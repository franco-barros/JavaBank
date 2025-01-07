import java.util.Scanner;

public class BankAccount {
    private String accountNumber;
    private double balance;
    private String pin;

    // Constructor
    public BankAccount(String accountNumber, double balance, String pin) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.pin = pin;
    }

    // Método para autenticar al usuario
    public boolean authenticateUser(String inputPin) {
        int attempts = 0;
        Scanner scanner = new Scanner(System.in); // Añadir Scanner para nuevas entradas de PIN

        while (attempts < 3) {
            if (this.pin.equals(inputPin)) {
                return true;
            } else {
                attempts++;
                System.out.println("PIN incorrecto. Intento " + attempts + " de 3.");
                if (attempts < 3) {
                    System.out.println("Por favor, intente de nuevo:");
                    inputPin = scanner.nextLine(); // Pedir nuevo PIN
                }
            }
        }
        return false;
    }


    // Getters
    public double getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    // Otros métodos relacionados con operaciones bancarias (opcional)
}
