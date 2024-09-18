/**
* Lab 1: [CurrencyConversion BITCOIN => DOLLAR]
* @author [write one partner name here]
* @author [write the other partner name here]
*/
public class CurrencyConversion {
    private static final double BITCOIN_TO_DOLLAR_RATE = 60000.0; // Example conversion rate

    // Method to convert Bitcoin to Dollar
    public static double Bitcoin2Dollar(double amountInBitcoin) {
        return amountInBitcoin * BITCOIN_TO_DOLLAR_RATE;
    }

    // Method to convert Dollar to Bitcoin
    public static double Dollar2Bitcoin(double amountInDollar) {
        return amountInDollar / BITCOIN_TO_DOLLAR_RATE;
    }

    public static void main(String[] args) {
        double bitcoinAmount = 0.5; // Example amount in Bitcoin
        double dollarAmount = Bitcoin2Dollar(bitcoinAmount);
        System.out.println(bitcoinAmount + " Bitcoin is equal to " + dollarAmount + " Dollars.");

        dollarAmount = 15000; // Example amount in Dollars
        double bitcoinValue = Dollar2Bitcoin(dollarAmount);
        System.out.println(dollarAmount + " Dollars is equal to " + bitcoinValue + " Bitcoin.");
    }
}
