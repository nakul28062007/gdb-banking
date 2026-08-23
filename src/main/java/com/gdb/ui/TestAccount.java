package main.java.com.gdb.ui;

import main.java.com.gdb.model.Account;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

public class TestAccount {
    static ArrayList<Account> accounts = new ArrayList<>();
    static HashMap<Integer, Integer> accountsIndex = new HashMap<>();
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
    static int askAndCheckAccNum(Scanner sc){
        int accNum;
        do{
            accNum = getSafeInt(sc,"Enter You main.java.com.gdb.model.Account Number: ");
        }while(accNum < 1000);
        if(accountsIndex.containsKey(accNum)){
            return accountsIndex.get(accNum);
        }
        else {
            System.out.println("main.java.com.gdb.model.Account Number does not exits.");
            return -1;
        }
    }
    static boolean validateName(String name){
        if (name.isBlank()) {
            System.out.println("Name cannot be empty or only whitespace.");
            return false;
        }
        return true;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("==============================================================");
        System.out.println("\t\t\t\t\tGLOBAL DIGITAL BANK - ACCOUNT TEST");
        System.out.println("===============================================================");
        int i = 0; //iteraable variable for account Number
        String ans = "y";
        int accnum,index;
        while (ans.equals("y")) {
            System.out.println("1. Create main.java.com.gdb.model.Account.");
            System.out.println("2. Deposit Money.");
            System.out.println("3. Withdraw Money.");
            System.out.println("4. Check Balance.");
            System.out.println("5. Display All Accounts");
            int choice = getSafeInt(sc,"Enter your choice: ");
            if (choice == 1) {
                String name;
                do{
                    System.out.println("Enter name: ");
                    name = sc.nextLine();

                }while(!(validateName(name)));
                int age = getSafeInt(sc,"Enter age: ");
                if(!(age>=18)){
                    System.out.println("Underage.");
                    System.out.println("main.java.com.gdb.model.Account Creation Failed.");
                    continue;
                }
                System.out.println("Enter main.java.com.gdb.model.Account Type: ");
                String acctype = sc.next();
                if (!acctype.equals("Savings") && !acctype.equals("Current")) {
                    System.out.println("Invalid account type. Use 'Savings' or 'Current'.");
                    continue;
                }
                double deposit = getSafeDouble(sc,"Initial deposit amount: ");
                i++; //for account number generation
                Account newAccount = new Account(1000 + i, name, age, deposit, acctype);
                accounts.add(newAccount);
                accountsIndex.put(1000+i, i-1);//accountNumber , its index in the arraylist
                Account obj1 = accounts.get(i-1);
                System.out.printf("main.java.com.gdb.model.Account NO: %d | %s ( %d yrs ) | %s | %.2f | %s",obj1.getAccountNumber(),obj1.getName(),obj1.getAge(),obj1.getAccountType(),obj1.getBalance(),obj1.getStatus());
                System.out.println();
            }
            else if(choice==2){
                int index_1 = askAndCheckAccNum(sc);
                if(index_1==-1){
                    System.out.println("main.java.com.gdb.model.Account Not Found");
                    continue;
                }
                double amt = getSafeDouble(sc,"Enter the amount you want to deposit: ");
                if(accounts.get(index_1).deposit(amt)){
                    System.out.println("Deposit Successful.");
                    System.out.println("balance: "+accounts.get(index_1).getBalance());
                }
                else{
                    System.out.printf("Depositing %.2f: FAILED(Invalid amount)\n",amt);
                }
            }
            else if(choice==3){
                int index_1 = askAndCheckAccNum(sc);
                if (index_1 == -1) {
                    System.out.println("main.java.com.gdb.model.Account Not Found");
                    continue;
                }
                double amt = getSafeDouble(sc,"Enter Amount to Withdraw: ");
                if(accounts.get(index_1).withdraw(amt)){
                    System.out.printf("Withdrawing %.2f: SUCCESS%nNew balance: %.2f%n",amt,accounts.get(index_1).getBalance());
                }
                else {
                    System.out.printf("Withdrawing %.2f: FAILED (Insufficient balance)%nCurrent balance: %.2f%n",amt,accounts.get(index_1).getBalance());
                }

            }
            else if(choice ==4 ) {
                int index_1 = askAndCheckAccNum(sc);
                if (index_1 == -1) {
                    System.out.println("main.java.com.gdb.model.Account Not Found");
                    continue;
                }
                System.out.println("Balance: " + accounts.get(index_1).getBalance());
            }
            else if(choice ==5){
                for (Account obj : accounts) {
                    System.out.printf("main.java.com.gdb.model.Account# %d | %s (%d yrs) | %s | %.2f | %s", obj.getAccountNumber(), obj.getName(), obj.getAge(), obj.getAccountType(), obj.getBalance(), obj.getStatus());
                    System.out.println();
                }
            }
            else {
                System.out.println("Invalid choice.");
            }
            System.out.println("Do you want to continue? y/n: ");
            ans = sc.next();
            if (!((ans).equalsIgnoreCase("y"))){
                System.out.println("==============================================================");
                System.out.println("\t\t\t\t\tTEST COMPLETED!");
                System.out.println("==============================================================");
            }
        }
    }
}



