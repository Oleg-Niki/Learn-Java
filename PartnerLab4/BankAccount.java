package PartnerLab4;

import java.util.Calendar;
import java.text.SimpleDateFormat;
/**
 * This program simulates a Bank Account program.
 * @author Mike
 * @author Oleg
 */

public class BankAccount {
  private String accountNumber;
  private StringBuilder transactionHistory;
  private double balance;

  /**
   * Default constructor initializes balance to zero.
   */
  public BankAccount(){
    this.balance = 0.0;
    this.transactionHistory = new StringBuilder();
    this.accountNumber = generateAccountNumber();
  }

  /**
   * Constructor initializes balance with specified amount.
   * @param amount Initial amount for the account balance.
   */
  public BankAccount(double amount){
    if (amount < 0) {
      System.out.println("Balance cannot be negative. Transaction canceled");
    }
    this.balance = amount;
    this.transactionHistory = new StringBuilder();
    this.accountNumber = generateAccountNumber();
    recordTransaction("Your first deposit =", amount);
  }
    /**
   * Sets a new balance for the account.
   * @param amount New balance amount.
   */
  public void setBalance(double amount) {
    if (amount < 0) {
     System.out.println("Balance cannot be negative. Transaction canceled");
  }
  this.balance = amount;
  recordTransaction("Set Balance: ", amount);
}
  /**
   * Returns the current balance.
   * @return Current account balance.
   */
  public double getBalance() {
    return balance;
  }

  /**
   * Deposits an amount into the account.
   * @param amount Amount to deposit.
   */
  public void deposit(double amount) {
    if (amount <= 0) {
      System.out.println("Amount must be positive. Transaction canceled.");
    }
    balance += amount;
    recordTransaction("Deposit =", amount);
  }
  /**
   * Withdraws an amount from the account.
   * @param amount Amount to withdraw.
   */
  public void withdraw(double amount) {
    if (amount <= 0) {
      System.out.println("Amount must be positive. Transaction canceled.");
    }
    if (amount > balance) {
      System.out.println("You don't have enough money. Sorry. Check your balance and try again");
    }
    balance -= amount;
    recordTransaction("Withdrawal =", amount);
  }
  /**
   * Returns a statement of all transactions as a String.
   * @return A string containing all transaction history.
   */
  public String getStatement() {
    return "Account Statement for Account Number: " + accountNumber + "\n" + transactionHistory.toString();
  }
  /**
   * Records each transaction with the current date and time.
   * @param type Type of transaction (Deposit, Withdrawal, etc.)
   * @param amount Amount for the transaction.
   */
  private void recordTransaction(String type, double amount) {
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String date = sdf.format(calendar.getTime());
    transactionHistory.append(date)
            .append(" - ")
            .append(type)
            .append(": $")
            .append(amount)
            .append("\n");
  }
  /**
   * Generates a random account number.
   * @return A randomly generated account number.
   */
  private String generateAccountNumber() {
    return "ACC" + (int) (Math.random() * 1000000);
  }
}
