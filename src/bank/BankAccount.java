package bank;

import java.util.ArrayList;
import java.text.DecimalFormat;

public class BankAccount extends Account {
    private ArrayList<Double> transactionHistory;

    // DecimalFormat para formatear con 2 decimales
    private static final DecimalFormat df = new DecimalFormat("#.##");


    public BankAccount(String accountNumber, double balance) {
        super(accountNumber, balance);
        this.transactionHistory = new ArrayList<>();
    }

    @Override
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactionHistory.add(amount);
            System.out.println("Depósito de $" + df.format(amount) + " exitoso.");
            System.out.println("Balance actual: $" + df.format(balance));
        } else {
            System.out.println("El monto a depositar debe ser mayor a 0.");
        }
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            transactionHistory.add(-amount);
            System.out.println("Retiro de $" + df.format(amount) + " exitoso.");
            System.out.println("Balance actual: $" + df.format(balance));
            return true;
        } else {
            System.out.println("Fondos insuficientes.");
            return false;
        }
    }

    // Obtener transacciones y balance
    public ArrayList<Double> getTransactionHistory() {
        return transactionHistory;
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public String getAccountNumber() {
        return accountNumber;
    }
}
