/**
* Lab 1: [CurrencyConversion BITCOIN => DOLLAR]
* @author Oleg Nikitashin
* @author [write the other partner name here]
* @
*/
import java.util.Scanner;

public class PartnerLab1 {
    private static final double BITCOIN_TO_DOLLAR_RATE = 60000.0; // Example conversion rate

    /**
    * Method to convert Bitcoin to Dollar.
    * @param amountInBitcoin Amount in Bitcoin
    * @return Amount in Dollar
    */
    public static double Bitcoin2Dollar(double amountInBitcoin) {
        return amountInBitcoin * BITCOIN_TO_DOLLAR_RATE;
    }

    /**
    * Method to convert Dollar to Bitcoin.
    * @param amountInDollar Amount in Dollar
    * @return Amount in Bitcoin
    */
    public static double Dollar2Bitcoin(double amountInDollar) {
        return amountInDollar / BITCOIN_TO_DOLLAR_RATE;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Currency Conversion Program!");
        System.out.println("Please select an option:");
        System.out.println("1. Convert Bitcoin to Dollar");
        System.out.println("2. Convert Dollar to Bitcoin");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                System.out.print("Enter the amount in Bitcoin: ");
                double bitcoinAmount = scanner.nextDouble();
                double dollarAmount = Bitcoin2Dollar(bitcoinAmount);
                System.out.println(bitcoinAmount + " Bitcoin is equal to " + dollarAmount + " Dollars.");
                break;

            case 2:
                System.out.print("Enter the amount in Dollars: ");
                double dollarValue = scanner.nextDouble();
                double bitcoinValue = Dollar2Bitcoin(dollarValue);
                System.out.println(dollarValue + " Dollars is equal to " + bitcoinValue + " Bitcoin.");
                break;

            default:
                System.out.println("Invalid option. Please select 1 or 2.");
                break;
        }

        scanner.close();
    }
}
