package main.java.com.gdb.model;

public class Account {
    //======Constants========
    private static final double MIN_BALANCE_SAVINGS = 500.0;
    private static final double MIN_BALANCE_CURRENT = 1000.0;
    private static final int MIN_AGE = 18;
    private static final int MIN_PIN = 1000;
    private static final int MAX_PIN = 9999;
    private static final String CURRENCY = "₹";

    //=====Fields======
    private final int accountNumber;
    private String name;
    private int age;
    private double balance;
    private final String accountType;
    private  String status;
    private Integer PIN=null;


    //=====Constructor=====
    public Account(int accountNumber, String name, int age, double deposit, String accountType)
        throws IllegalArgumentException {
        if (age < MIN_AGE) throw new IllegalArgumentException("Age must be 18 or older.");
        if (!accountType.equalsIgnoreCase("savings") && !accountType.equalsIgnoreCase("current"))
            throw new IllegalArgumentException("Account type must be Savings or Current.");

        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.age = age;
        this.name = name;
        if (!Double.isFinite(deposit) || deposit < getMinimumBalance())
            throw new IllegalArgumentException("Initial deposit below minimum: "+CURRENCY+getMinimumBalance());

        this.balance = deposit;
        this.status = "Active";
        this.PIN = null;
    }

    //======Business Methods=======
    public void deposit(double amount)
        throws InvalidAmountException, InactiveAccountException{
        validateActive();
        if (!isValidAmount(amount)) {
            throw new InvalidAmountException("Invalid Amount. Amount must be greater than 0.");
        }
        balance+=amount;
    }
    public void withdraw(double amount, int pin)
        throws InvalidAmountException,InsufficientBalanceException, MinimumBalanceViolationException,InactiveAccountException, InvalidPinException{
        validateActive();
        if(this.PIN==null) throw new InvalidPinException("Invalid PIN Error: PIN not Set.");
        if(!(verifyPin(pin))) throw new InvalidPinException("EXCEPTION: Incorrect PIN.");
        if (!isValidAmount(amount)) throw new InvalidAmountException("Invalid Amount");
        if(amount > getBalance()) throw new InsufficientBalanceException("Insufficient balance."+"Available: "+getCurrency()+getBalance());
        if(balance-amount < getMinimumBalance()) throw new MinimumBalanceViolationException("Cannot withdraw. Minimum balance of "+getCurrency()+getMinimumBalance()+" required."+"Available after withdrawal: "+ getCurrency()+(getBalance()-amount));
        else balance -= amount;
    }

    //======Account Status Management=====
    public void closeAccount()
        throws IllegalStateException{
        if(this.status.equals("Inactive")){
            throw new IllegalStateException("Account is already closed.");
        }
        this.status="Inactive";
    }
    public void reopenAccount()
        throws IllegalStateException{
        if(this.status.equals("Active")) throw new IllegalStateException("Account is already active.");
        this.status="Active";
    }

    //======PIN Management========
    public void setPin(int pin)
        throws IllegalArgumentException, IllegalStateException{
        if (pin < MIN_PIN || pin > MAX_PIN) {
            throw new IllegalArgumentException("PIN must be a 4-digit number between " + MIN_PIN + " and " + MAX_PIN + ".");
        }
        if (hasPin()) {
            throw new IllegalStateException("PIN is already set.");
        }
        this.PIN=pin;
    }
    public void changePin(int pin)
            throws IllegalArgumentException, IllegalStateException{
        if (pin < MIN_PIN || pin > MAX_PIN) {
            throw new IllegalArgumentException("PIN must be a 4-digit number between " + MIN_PIN + " and " + MAX_PIN + ".");
        }
        if (hasPin()) {
            throw new IllegalStateException("PIN is already set.");
        }
        this.PIN=pin;
    }
    public boolean verifyPin(int pin){
        return this.PIN != null && this.PIN == pin;
    }
    public boolean hasPin(){
        return PIN != null;
    }

    //======Helper Methods========
    private double getMinimumBalance() {
        return this.accountType.equalsIgnoreCase("savings") ? MIN_BALANCE_SAVINGS : MIN_BALANCE_CURRENT;
    }
    private void validateActive()
        throws InactiveAccountException{
        if(!this.status.equalsIgnoreCase("active")) throw new InactiveAccountException("Account is Inactive.Please reopen the account or contact support.");
    }
    public double getBalance(){
        return balance;
    }
    public int getAccountNumber(){
        return accountNumber;
    }
    public String getName(){
        return name;
    }
    public int getAge(){
        return age;
    }
    public String getAccountType(){
        return accountType;
    }
    public String getStatus(){
        return status;
    }
    public String getCurrency(){
        return CURRENCY;
    }
    void setName(String name){
        this.name = name;
    }
    void setAge(int age){
        this.age = age;
    }
    private static boolean isValidAmount(double amount) {
        return Double.isFinite(amount) && amount > 0;
    }
}


