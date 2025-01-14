package bank;

import java.util.ArrayList;
import java.text.DecimalFormat;

public class BankAccount extends Account {
    private final ArrayList<Double> transactionHistory;  // Lista para registrar las transacciones

    // DecimalFormat para formatear con 2 decimales
    private static final DecimalFormat df = new DecimalFormat("#.##");

    public BankAccount(String accountNumber, double balance) {
        super(accountNumber, balance);  // Llamada al constructor de la clase Account
        this.transactionHistory = new ArrayList<>();
    }

    @Override
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;  // Modificar el balance
            transactionHistory.add(amount);  // Añadir el depósito a la historia
            System.out.println("Depósito de $" + df.format(amount) + " exitoso.");
            System.out.println("Balance actual: $" + df.format(balance));
            notifyObservers();  // Notificar a los observadores del cambio en el balance
        } else {
            System.out.println("El monto a depositar debe ser mayor a 0.");
        }
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;  // Modificar el balance
            transactionHistory.add(-amount);  // Añadir el retiro a la historia (negativo)
            System.out.println("Retiro de $" + df.format(amount) + " exitoso.");
            System.out.println("Balance actual: $" + df.format(balance));
            notifyObservers();  // Notificar a los observadores del cambio en el balance
            return true;
        } else {
            System.out.println("Fondos insuficientes o monto negativo.");
            return false;
        }
    }

    // Obtener el historial de transacciones
    public ArrayList<Double> getTransactionHistory() {
        return transactionHistory;
    }

    @Override
    public String getAccountNumber() {
        return accountNumber;  // Regresamos el accountNumber heredado de Account
    }
}
