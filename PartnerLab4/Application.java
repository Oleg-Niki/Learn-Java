package PartnerLab4;
import java.util.Scanner;
/**
 * Bank Account Application Tester
 * @author Hellen Pacheco
 */
public class Application {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Bank!");
        System.out.println("Please select an option:");
        System.out.println("1. Deposit");
        System.out.println("2. Withdrawal");
        System.out.println("3. Statement");
        int option = scanner.nextInt();
        double amount = 0.0;
        BankAccount account = new BankAccount(100);
        System.out.println("New Account Balance: " + account.getBalance());
        System.out.println("Withdrawing 200");
        account.withdraw(200);
        System.out.println("New Balance: " + account.getBalance());
        System.out.println("Withdrawing -100");
        account.withdraw(-100);
        System.out.println("New Balance: " + account.getBalance());
        System.out.println("Depositing -100");
        account.deposit(-100);
        System.out.println("New Balance: " + account.getBalance());
        System.out.println("Depositing 1000");
        account.deposit(1000);
        System.out.println("Withdrawing 200");
        account.withdraw(200);
        System.out.println("*************************************");
        System.out.println(account.getStatement());

//here we can create a menu of options
        switch (option) {
            case 1:
                System.out.println("For deposit enter amount in dollars: ");

                break;

            case 2:
                System.out.println("For withdraw enter amount in dollars: ");
                break;

            case 3:
                System.out.println("Your account statement");
                break;
        }

    }
}