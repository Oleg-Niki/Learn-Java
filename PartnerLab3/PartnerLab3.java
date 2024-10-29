import java.util.Random;
import java.util.Scanner;

/**
 * This program simulates a number guessing game. The program generates a random number,
 * asks the user to guess it, and provides feedback on whether the guess was too high or too low.
 * The user has a limited number of attempts to guess the correct number.
 *
 * @author Ryan
 * @author Oleg
 */
public class PartnerLab3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        boolean playAgain;

        do {
            System.out.println("Hello and welcome gamer! This is a guessing game.");
            System.out.println("I'm thinking of a number between 1 and 10. Guess what it is.");
            int myNumber = random.nextInt(10) + 1;
            int maxAttempts = 5;  // 5 tries
            boolean guessedCorrectly = false;

            for (int attempt = 1; attempt <= maxAttempts; attempt++) {
                System.out.printf("Attempt %d: Enter your guess: ", attempt);
                int gamerNumber = scanner.nextInt();

                if (myNumber < gamerNumber) {
                    System.out.println("Too high! Try again.");
                } else if (myNumber > gamerNumber) {
                    System.out.println("Too low! Try again.");
                } else {
                    System.out.println("Congratulations! You guessed it right!");
                    guessedCorrectly = true;
                    break;
                }
            }

            if (!guessedCorrectly) {
                System.out.println("Sorry, you lost! The correct number was: " + myNumber);
            }

            System.out.print("Do you want to play again? (yes/no): ");
            scanner.nextLine();  // Consume the newline left from nextInt()
            playAgain = scanner.nextLine().equalsIgnoreCase("yes");

        } while (playAgain);

        System.out.println("Thanks for playing, goodbye!");
        scanner.close();
    }
}
