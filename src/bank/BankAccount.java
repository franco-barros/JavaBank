package bank;

import java.util.ArrayList;
import java.text.DecimalFormat;

public class BankAccount extends Account implements Subject {
    private final ArrayList<Double> transactionHistory;
    private final ArrayList<Observer> observers;  // Lista para los observadores

    private static final DecimalFormat df = new DecimalFormat("#.##");

    public BankAccount(String accountNumber, double balance) {
        super(accountNumber, balance);
        this.transactionHistory = new ArrayList<>();
        this.observers = new ArrayList<>();
    }

    @Override
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactionHistory.add(amount);
            System.out.println("Depósito de $" + df.format(amount) + " exitoso.");
            System.out.println("Balance actual: $" + df.format(balance));
            notifyObservers();
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
            notifyObservers();
            return true;
        } else {
            System.out.println("Fondos insuficientes o monto negativo.");
            return false;
        }
    }

    @Override
    public void attach(Observer observer) {
        observers.add(observer);  // Añadir observador
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);  // Eliminar observador
    }

    @Override
    public void notifyObservers() {
        // Notificar a todos los observadores registrados
        for (Observer observer : observers) {
            observer.update(balance);  // Pasamos el balance
        }
    }

    public ArrayList<Double> getTransactionHistory() {
        return transactionHistory;
    }

    @Override
    public String getAccountNumber() {
        return accountNumber;
    }
}
