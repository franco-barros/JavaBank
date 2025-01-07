import java.util.ArrayList;
import java.util.Scanner;

public class BankAccount {
    private String accountNumber; // Número de cuenta
    private double balance;       // Saldo actual
    private String pin;           // PIN de seguridad
    private ArrayList<Double> transactionHistory; // Historial de transacciones

    // Constructor
    public BankAccount(String accountNumber, double balance, String pin) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.pin = pin;
        this.transactionHistory = new ArrayList<>(); // Inicializar historial vacío
    }

    // Método para autenticar al usuario
    public boolean authenticateUser(String inputPin) {
        int attempts = 0;
        Scanner scanner = new Scanner(System.in);

        while (attempts < 3) {
            if (this.pin.equals(inputPin)) {
                return true; // Autenticación exitosa
            } else {
                attempts++;
                System.out.println("PIN incorrecto. Intento " + attempts + " de 3.");
                if (attempts < 3) {
                    System.out.println("Por favor, intente de nuevo:");
                    inputPin = scanner.nextLine();
                }
            }
        }
        return false; // Autenticación fallida tras 3 intentos
    }

    // Métodos para operaciones bancarias
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;                    // Incrementar el saldo
            transactionHistory.add(amount);       // Registrar transacción como depósito
            System.out.println("Depósito de $" + amount + " exitoso.");
        } else {
            System.out.println("El monto a depositar debe ser mayor a 0.");
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;                    // Decrementar el saldo
            transactionHistory.add(-amount);      // Registrar transacción como retiro
            System.out.println("Retiro de $" + amount + " exitoso.");
            return true;
        } else if (amount > balance) {
            System.out.println("Fondos insuficientes.");
        } else {
            System.out.println("El monto a retirar debe ser mayor a 0.");
        }
        return false;
    }

    // Método para obtener el historial de transacciones
    public ArrayList<Double> getTransactionHistory() {
        return transactionHistory;
    }

    // Getters
    public double getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
}
