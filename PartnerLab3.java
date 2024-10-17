import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        System.out.printf("Hello and welcome gamer! This is a guessing game.\nI'm thinking of number between 1 and 10.\nGuess what it is.");
        //int myNumber = randomNum();
        int maxAttempts = 6;
        boolean guessedCorretly = false;
        String playAgain;

        do {
            System.out.println("You have 5 attempts. \nEnter you number here: ");
            //int gamerNumber = 0;
            int myNumber = random.nextInt(10) + 1;
            for (int attempt = 1; attempt < maxAttempts; attempt++) {
                int gamerNumber = scanner.nextInt();
                System.out.println("Your number is : " + gamerNumber);

                if (myNumber < gamerNumber) {
                    System.out.println("My number is less");
                } else if (myNumber > gamerNumber) {
                    System.out.println("My number is bigger");
                } else {
                    System.out.println("WIN!");
                    guessedCorretly = true;
                    break;
                }

                if (attempt == maxAttempts || !guessedCorretly) {
                    System.out.println("Sorry, you have used all your attempts. The correct number was: " + myNumber);
                }
            }
            System.out.print("Do you want to play again? (yes/no): ");
            scanner.nextLine();  // Consume the newline left from nextInt()
            playAgain = scanner.nextLine();
        }
        while (playAgain.equalsIgnoreCase("yes"));
        System.out.println("Thank for playing, goodbye!");
        scanner.close();
    }
}


//        for (int attempt=1; attempt <maxAttempts; attempt++) {
//            int gamerNumber = gamerNum();
//            System.out.print("Attempt " + attempt + ". You number: " + gamerNumber);
//
//            if (gamerNumber < myNumber) {
//                System.out.println(" Number in my mind is bigger than yours");
//            } else if (gamerNumber > myNumber) {
//                System.out.println(" Number in my mind is smaller than yours");
//            }
//            else {
//                System.out.println(" HOW DID YOU KNOW?!");
//            }
//        }
//    System.out.println("1 - if you want to play again\n2 - exit");
//    int choise = scanner.nextInt();
//    switch (choise){
//        case 1:
//            System.out.println("Let's play again");
//
//    }
//
//
//    }
//    public static int randomNum (){
//        Random random = new Random();
//        int randomInt = random.nextInt(100) + 1;
//        return randomInt;
//    }
//
//    public static int gamerNum (){
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Enter your number here: ");
//        int enterNum = scanner.nextInt();
//        return enterNum;
//    }
//}
