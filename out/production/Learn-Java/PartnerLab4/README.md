**Pseudocode for BankAccount Class
**

**Class BankAccount**

**Instance Variables:**
accountNumber: Holds the unique number of the bank account.
balance: Tracks the current balance in the account.
transactionHistory: Stores a history of transactions (use StringBuilder to store transaction records).

**Constructors:**

1. Default Constructor BankAccount():

Initialize balance to 0.
Initialize transactionHistory with an empty StringBuilder.

2. Overloaded Constructor BankAccount(startingBalance):

Initialize balance to startingBalance.
Initialize transactionHistory with an empty StringBuilder.
Add "Account created with initial balance: startingBalance" to transactionHistory.

**Methods:
**

**deposit(amount):**

If amount is negative:
Output "Deposit amount must be positive."
Else:
Add amount to balance.
Record the date and time of the transaction using Calendar.
Append "Deposited amount on date" to transactionHistory.
withdraw(amount):

If amount is negative:
Output "Withdrawal amount must be positive."
Else If amount > balance:
Output "Insufficient funds."
Else:
Subtract amount from balance.
Record the date and time of the transaction using Calendar.
Append "Withdrew amount on date" to transactionHistory.
getBalance():

Return balance.
getTransactionStatement():

Return the transaction history as a string (transactionHistory.toString()).
Pseudocode for Application Tester Class
Class ApplicationTester

**Main Method:**
Create an instance of BankAccount (e.g., account1).
Perform a series of operations on account1, such as:
Deposit money
Withdraw money
Check Balance using getBalance()
Print Transaction Statement using getTransactionStatement()
Output results for each operation to test if the BankAccount class is working as expected.
