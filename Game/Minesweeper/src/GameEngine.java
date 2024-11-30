package Game;

import javafx.scene.image.Image;

public class GameEngine {
    private int rows;
    private int cols;
    private int mines;
    private boolean[][] mineField;
    private boolean[][] revealed;
    private boolean[][] flagged;
    private boolean gameOver;

    public GameEngine(int rows, int cols, int mines) {
        this.rows = rows;
        this.cols = cols;
        this.mines = mines;
        initializeGame();
    }

    private void initializeGame() {
        mineField = new boolean[rows][cols];
        revealed = new boolean[rows][cols];
        flagged = new boolean[rows][cols];
        gameOver = false;

        // Randomly place mines
        for (int i = 0; i < mines; i++) {
            int row, col;
            do {
                row = (int) (Math.random() * rows);
                col = (int) (Math.random() * cols);
            } while (mineField[row][col]);
            mineField[row][col] = true;
        }
    }

    public boolean isMine(int row, int col) {
        return mineField[row][col];
    }

    public boolean revealCell(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols || revealed[row][col] || flagged[row][col]) {
            return false; // Ignore invalid or already revealed/flagged cells
        }

        revealed[row][col] = true;

        if (isMine(row, col)) {
            gameOver = true;
            return false; // Indicate game over
        }

        // If no neighboring mines, reveal adjacent cells recursively
        if (getNeighboringMines(row, col) == 0) {
            for (int dr = -1; dr <= 1; dr++) {
                for (int dc = -1; dc <= 1; dc++) {
                    if (dr != 0 || dc != 0) {
                        revealCell(row + dr, col + dc);
                    }
                }
            }
        }

        return true; // Indicate safe cell revealed
    }

    public void toggleFlag(int row, int col) {
        if (!revealed[row][col]) {
            flagged[row][col] = !flagged[row][col];
        }
    }

    public boolean isRevealed(int row, int col) {
        return revealed[row][col];
    }

    public boolean isFlagged(int row, int col) {
        return flagged[row][col];
    }

    public int getNeighboringMines(int row, int col) {
        int count = 0;
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                int newRow = row + dr;
                int newCol = col + dc;
                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols && mineField[newRow][newCol]) {
                    count++;
                }
            }
        }
        return count;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public void restartGame() {
        initializeGame();
    }

    public Image getMineImage() {
        try {
            return new Image(getClass().getResource("/images/mine.png").toExternalForm());
        } catch (NullPointerException e) {
            System.err.println("Error: Mine image not found!");
            throw e;
        }
    }

    public Image getFlagImage() {
        try {
            return new Image(getClass().getResource("/images/flag.png").toExternalForm());
        } catch (NullPointerException e) {
            System.err.println("Error: Flag image not found!");
            throw e;
        }
    }
}
