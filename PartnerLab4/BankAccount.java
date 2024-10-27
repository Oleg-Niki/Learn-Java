package PartnerLab4;

public class BankAccount {
  private double balance;

  public BankAccount(){
    balance = 0.0;
  }
  public BankAccount(double amount){
    balance = amount;
  }

  void setBalance(double amount){
    balance = amount;
  }
  double getBalance(){
    return balance;
  }
  void deposit(double amount){ //here we should check if the amount is positive
    balance += amount;
  }
  void withdraw(double amount){ //here for example we should check that we can't withdraw amount greater than we have
    balance -= amount;
  }
  void printStatement(){

  }
}
