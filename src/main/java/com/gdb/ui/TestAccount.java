package main.java.com.gdb.ui;
import main.java.com.gdb.model.*;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.LinkedHashMap;
import java.util.Map;

public class TestAccount {
    static Map<Integer, Account> accounts = new LinkedHashMap<>(); //account Number , Account object
    static int nextAccountNumber = 1001;
    static int getSafeInt(Scanner sc , String prompt){
        int val = 0;
        while(true){
            System.out.println(prompt);
            try{
                val = sc.nextInt();
                sc.nextLine(); //consumes newline
                return val;
            }catch (InputMismatchException e){
                System.out.println("Invalid input. Please enter a whole number.");
                sc.nextLine(); //discards the invalid token
            }
        }
    }
    static double getSafeDouble(Scanner sc, String prompt){
        double val = 0.0;
        while(true){
            System.out.println(prompt);
            try{
                val = sc.nextDouble();
                sc.nextLine(); //consumes newline
                return val;
            }catch (InputMismatchException e){
                System.out.println("Invalid input. Please enter a number (e.g., 100.50).");
                sc.nextLine(); //discards the invalid token
            }
        }
    }
    static Account askAndCheckAccNum(Scanner sc){
        int accNum = getSafeInt(sc, "Enter Your Account Number: ");
        Account acc = accounts.get(accNum);
        if(acc==null){
            System.out.println("Account not found.");
        }
        return acc;
    }
    static boolean validateName(String name){
        if (name.isBlank()) {
            System.out.println("Name cannot be empty or only whitespace.");
            return false;
        }
        return true;
    }
    static String checkPin(Account acc){
        String resultPin ="Yes";
        if(!(acc.hasPin())){
            resultPin = "No";
        }
        return resultPin;
    }
    static int askPin(Scanner sc){
        return getSafeInt(sc, "Enter PIN: ");
    }
    static boolean askContinue(Scanner sc){
        System.out.println("Do you want to continue? y/n: ");
        String ans = sc.next();
        return ans.equalsIgnoreCase("y");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("==============================================================");
        System.out.println("\t\t\t\t\tACCOUNT TEST WITH EXCEPTIONS");
        System.out.println("===============================================================");
        boolean keepGoing = true;
        while (keepGoing) {
            System.out.println("\t\t\t\t\tMENU");
            System.out.println("1. Create Account.");
            System.out.println("2. Deposit Money.");
            System.out.println("3. Withdraw Money.");
            System.out.println("4. Check Balance.");
            System.out.println("5. Close Account.");
            System.out.println("6. Reopen Account.");
            System.out.println("7. View Account.");
            System.out.println("8. Set PIN.");
            System.out.println("9. Change PIN");
            System.out.println("10. Display All Accounts");
            System.out.println("11. Interest Calculation");
            System.out.println("12. Check Overdraft.");
            System.out.println("13. Repay Overdraft.");
            int choice = getSafeInt(sc,"Enter your choice: ");

            if (choice == 1) {
                String name;
                do{
                    System.out.println("Enter name: ");
                    name = sc.nextLine();

                }while(!(validateName(name)));
                int age = getSafeInt(sc,"Enter age: ");

                System.out.println("Enter Account Type: ");
                String acctype = sc.next();

                double initialBalance = getSafeDouble(sc,"Initial deposit amount: ");
                try {
                    Account newAccount;
                    if (acctype.equalsIgnoreCase("savings")) {
                        newAccount = new SavingsAccount(nextAccountNumber, name, age, initialBalance);
                    } else if (acctype.equalsIgnoreCase("current")) {
                        newAccount = new CurrentAccount(nextAccountNumber, name, age, initialBalance);
                    } else {
                        System.out.println("Invalid account type. Must be 'savings' or 'current'.");
                        // FIX: was `continue` — now goes through askContinue like every other path.
                        keepGoing = askContinue(sc);
                        continue;
                    }
                    accounts.put(newAccount.getAccountNumber(), newAccount);
                    nextAccountNumber++;
                    System.out.printf("Account NO: %d | %s ( %d yrs ) | %s | %s %.2f | %s | PIN: %s",
                            newAccount.getAccountNumber(), newAccount.getName(), newAccount.getAge(),
                            newAccount.getAccountType(), newAccount.getCurrency(), newAccount.getBalance(),
                            newAccount.getStatus(), checkPin(newAccount));
                    System.out.println();
                } catch (IllegalArgumentException e) {
                    System.out.println("Account creation Failed: " + e.getMessage());
                }
            }
            else if(choice == 2) {
                Account acc = askAndCheckAccNum(sc);
                if (acc == null) { keepGoing = askContinue(sc); continue; }
                double amt = getSafeDouble(sc, "Enter the amount you want to deposit: ");
                try{
                    acc.deposit(amt);
                    System.out.println("Deposit Successful.");
                    System.out.println("Balance: " + acc.getBalance());
                }catch (InvalidAmountException | InactiveAccountException e){
                    System.out.println("Deposition Failed: "+e.getMessage());
                }
            }
            else if(choice == 3){
                Account acc = askAndCheckAccNum(sc);
                if (acc == null) { keepGoing = askContinue(sc); continue; }
                if(!acc.hasPin()) {
                    System.out.println("PIN not set. Set a PIN (option 8) before withdrawing.");
                    keepGoing = askContinue(sc);
                    continue;
                }
                double amt = getSafeDouble(sc,"Enter Amount to Withdraw: ");
                int pin = askPin(sc);
                if(acc.getAccountType().equalsIgnoreCase("current")){
                    CurrentAccount ca = (CurrentAccount) acc;
                    try{
                        System.out.println("Balance before: "+ca.getBalance());
                        ca.withdraw(amt,pin);
                        System.out.println("Withdrawing "+amt+" SUCCESS");
                        System.out.println("Balance After: "+ca.getBalance());
                        System.out.println("Overdraft Used: "+ca.getOverdraftUsed());
                        System.out.println("Available Overdraft: "+ca.getAvailableOverdraft());
                        System.out.println("Is using overdraft: "+ ca.isUsingOverdraft());
                    }catch (InvalidAmountException | InsufficientBalanceException | MinimumBalanceViolationException | InactiveAccountException | InvalidPinException e){
                        System.out.println("ERROR: "+e.getMessage());
                    }
                }
                else {
                    try {
                        acc.withdraw(amt, pin);
                        System.out.println("Withdrawal Successful");
                        System.out.println("Balance: " + acc.getBalance());
                    } catch (InvalidAmountException | InsufficientBalanceException | MinimumBalanceViolationException |
                             InactiveAccountException | InvalidPinException e) {
                        System.out.println("ERRORL: " + e.getMessage());
                    }
                }
            }
            else if(choice == 4 ) {
                Account acc = askAndCheckAccNum(sc);
                if (acc == null) { keepGoing = askContinue(sc); continue; }
                if(acc.getAccountType().equalsIgnoreCase("current")){
                    CurrentAccount ca = (CurrentAccount) acc;
                    System.out.println("Balance: " + ca.getBalance());

                }
                else {
                    System.out.println("Balance: " + acc.getBalance());
                }
            }
            else if(choice == 5){
                Account acc = askAndCheckAccNum(sc);
                if (acc == null) { keepGoing = askContinue(sc); continue; }

                try{
                    acc.closeAccount();
                    System.out.println("Account closed successfully.");
                }catch (IllegalStateException e){
                    System.out.println("Account closure Failed: "+ e.getMessage());
                }
            }
            else if(choice == 6){
                Account acc = askAndCheckAccNum(sc);
                if (acc == null) { keepGoing = askContinue(sc); continue; }

                try{
                    acc.reopenAccount();
                    System.out.println("Account reopened successfully");
                }catch (IllegalStateException e){
                    System.out.println("Account Cam't be reopened: "+ e.getMessage());
                }
            }
            else if(choice == 7){
                Account acc = askAndCheckAccNum(sc);
                if (acc == null) { keepGoing = askContinue(sc); continue; }

                System.out.printf("Account# %d | %s (%d yrs) | %s | %.2f | %s | PIN: %s%n", acc.getAccountNumber(), acc.getName(), acc.getAge(), acc.getAccountType(), acc.getBalance(), acc.getStatus(), checkPin(acc));
            }
            else if(choice == 8){
                Account acc = askAndCheckAccNum(sc);
                if (acc == null) { keepGoing = askContinue(sc); continue; }
                int pin = askPin(sc);
                try{
                    acc.setPin(pin);
                    System.out.println("PIN set successfully.");
                }catch (IllegalArgumentException | IllegalStateException e){
                    System.out.println("PIN can't be set."+ "ERROR: "+ e.getMessage());
                }
            }
            else if(choice == 9){
                Account acc = askAndCheckAccNum(sc);
                if (acc == null) { keepGoing = askContinue(sc); continue; }
                int oldPin = getSafeInt(sc, "Enter current PIN: ");
                int newPin = getSafeInt(sc, "Enter new PIN: ");
                try{
                    acc.changePin(oldPin,newPin);
                    System.out.println("PIN changed successfully");
                }catch (IllegalArgumentException | IllegalStateException | InvalidPinException e){
                    System.out.println("PIN cant't be changed. "+"ERROR: "+ e.getMessage());
                }
            }
            else if(choice ==10){
                if(accounts.isEmpty()){
                    System.out.println("No accounts to display.");
                }else {
                    for (Account acc : accounts.values()) {
                        System.out.printf("Account# %d | %s (%d yrs) | %s | %.2f | %s | PIN: %s", acc.getAccountNumber(), acc.getName(), acc.getAge(), acc.getAccountType(), acc.getBalance(), acc.getStatus(), checkPin(acc));
                        System.out.println();
                    }
                }
            }
            else if(choice == 11){
                Account acc = askAndCheckAccNum(sc);
                if (acc == null) { keepGoing = askContinue(sc); continue; }
                if(!(acc instanceof SavingsAccount sa)){
                    System.out.println("Interest calculation is only available for Savings accounts.");
                    keepGoing = askContinue(sc);
                    continue;
                }
                int years = getSafeInt(sc,"Enter Years: ");
                try {
                    double interest = sa.calculateInterest(years);
                    System.out.printf("Interest over %d years: %s%.2f%n", years, sa.getCurrency(), interest);
                }catch (IllegalArgumentException e){
                    System.out.println("ERROR: "+e.getMessage());
                }
            }
            else if(choice == 12){
                Account acc= askAndCheckAccNum(sc);
                if (acc == null) { keepGoing = askContinue(sc); continue; }
                if(!(acc instanceof CurrentAccount ca)){
                    System.out.println("Overdraft check is only available for Current Account.");
                    keepGoing = askContinue(sc);
                    continue;
                }
                System.out.println("Overdraft Limit: "+ ca.getOverdraftLimit()+"\n"+"Available overdraft: "+ca.getAvailableOverdraft()+"\n"+"Overdraft Used: "+ca.getOverdraftUsed()+"\n"+"Is Using Overdraft: "+ca.isUsingOverdraft());
            }
            else if(choice == 13){
                Account acc= askAndCheckAccNum(sc);
                if (acc == null) { keepGoing = askContinue(sc); continue; }
                if(!(acc instanceof CurrentAccount ca)){
                    System.out.println("Overdraft check is only available for Current Account.");
                    keepGoing = askContinue(sc);
                    continue;
                }
                try{
                    double amount = getSafeDouble(sc,"Enter repay overdraft amount: ");
                    System.out.println("Repaying overdraft of: "+amount);
                    System.out.println("balance before payment: "+ca.getBalance());
                    System.out.println("Overdraft used before: "+ca.getOverdraftUsed());
                    ca.repayOverdraft(amount);
                    System.out.println("Overdraft repay of: "+amount+" SUCCESS");
                    System.out.println("Balance after repayment: "+ca.getBalance());
                    System.out.println("Overdraft used after: "+ca.getOverdraftUsed());
                    System.out.println("Is using overdraft: "+ca.isUsingOverdraft());
                    // FIX (Bug #1): repayOverdraft() now throws InactiveAccountException
                    // if the account is closed — catch it alongside IllegalArgumentException.
                }catch (IllegalArgumentException | InactiveAccountException e){
                    System.out.println("ERROR: "+e.getMessage());
                }
            }
            else {
                System.out.println("Invalid choice.");
            }
            keepGoing = askContinue(sc);
            if (!keepGoing){
                System.out.println("==============================================================");
                System.out.println("\t\t\t\t\tTEST COMPLETED!");
                System.out.println("==============================================================");
            }
        }
    }
}