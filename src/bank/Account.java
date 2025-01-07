package bank;

public abstract class Account {
    protected String accountNumber;
    protected double balance;

    // Constructor
    public Account(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }

    // Métodos para depósitos y retiros
    public abstract void deposit(double amount);
    public abstract boolean withdraw(double amount);

    // Método para obtener el balance
    public double getBalance() {
        return this.balance;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }
}
