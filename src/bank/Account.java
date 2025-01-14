package bank;

import java.util.ArrayList;
import java.util.List;

public abstract class Account {
    private final List<Observer> observers = new ArrayList<>();
    protected double balance;  // balance es ahora 'protected'
    protected String accountNumber;  // accountNumber también 'protected'

    // Constructor para inicializar accountNumber y balance
    public Account(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    // Agregar observadores
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    // Retirar dinero
    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            notifyObservers();  // Notificar a los observadores
            return true;  // Retiro exitoso
        }
        return false;  // Fondos insuficientes o monto negativo
    }

    // Depositar dinero
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            notifyObservers();  // Notificar a los observadores
        } else {
            System.out.println("El monto a depositar debe ser mayor a 0.");
        }
    }

    // Notificar a los observadores cuando cambia el balance
    protected void notifyObservers() {  // Cambié el modificador de 'private' a 'protected'
        for (Observer observer : observers) {
            observer.update(balance);
        }
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
}
