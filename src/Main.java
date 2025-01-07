import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Crear una instancia de BankAccount
        BankAccount account = new BankAccount("123456789", 1500.75, "1234");

        // Simular la entrada del usuario para el PIN
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese su PIN:");
        String inputPin = scanner.nextLine();

        // Intentar autenticar al usuario
        if (account.authenticateUser(inputPin)) {
            System.out.println("Acceso Concedido");

            // Array de montos de transacciones
            int[] transactionAmounts = {200, -100, 50};

            // Operaciones con variables
            double balance = account.getBalance();
            balance += transactionAmounts[0]; // Depósito

            // Uso de operadores
            balance++; // Incremento
            String status = (balance < 0) ? "Deuda" : "Crédito";
            System.out.println("Estado de cuenta: " + status);

            // Imprimir el balance actualizado
            System.out.println("Balance actualizado: " + balance);
        } else {
            System.out.println("Acceso Denegado. Se ha superado el número de intentos.");
        }

        scanner.close();
    }
}
