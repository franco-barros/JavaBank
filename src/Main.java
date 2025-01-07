import java.util.Scanner;
import bank.Bank;
import bank.BankAccount;
import bank.SavingsAccount;
import user.User;
import java.text.DecimalFormat;

public class Main {

    private static final DecimalFormat df = new DecimalFormat("#.##");

    public static void main(String[] args) {
        // Crear cuentas
        BankAccount account1 = new BankAccount("123456789", 1500.75);
        SavingsAccount account2 = new SavingsAccount("987654321", 2500.50, 0.05); // Tasa de interés del 5%
        BankAccount account3 = new BankAccount("1011121314", 0.50);
        BankAccount account4 = new BankAccount("1516171819", 500.50);

        // Crear usuarios
        User user1 = new User("Juan Perez", "1234", account1);
        User user2 = new User("Nicolas Roque", "8888", account2); // Cuenta con SavingsAccount
        User user3 = new User("Roman Garcia", "8080", account3);
        User user4 = new User("Ana Garcia", "5678", account4);

        // Crear banco y añadir usuarios
        Bank bank = new Bank();
        bank.addUser(user1);
        bank.addUser(user2);
        bank.addUser(user3);
        bank.addUser(user4);


        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese su PIN:");
        String inputPin = scanner.nextLine();

        User authenticatedUser = bank.authenticateUser(inputPin);

        if (authenticatedUser != null) {
            System.out.println("Acceso concedido para " + authenticatedUser.getName());
            BankAccount userAccount = authenticatedUser.getAccount();

            boolean exit = false;
            while (!exit) {
                System.out.println("\nSeleccione una opción:");
                System.out.println("1. Depositar");
                System.out.println("2. Retirar");
                System.out.println("3. Ver historial de transacciones");
                System.out.println("4. Ver balance actual");

                // Mostrar la opción 5 de acuerdo al tipo de cuenta
                if (userAccount instanceof SavingsAccount) {
                    System.out.println("5. Aplicar interés");
                    System.out.println("6. Salir");
                } else {
                    System.out.println("5. Salir");
                }

                String menuOption = scanner.nextLine();
                switch (menuOption) {
                    case "1":
                        // Validación para el monto a depositar
                        double deposit = getValidAmount(scanner, "depositar");
                        userAccount.deposit(deposit);
                        break;

                    case "2":
                        // Validación para el monto a retirar
                        double withdraw = getValidAmount(scanner, "retirar");
                        userAccount.withdraw(withdraw);
                        break;

                    case "3":
                        System.out.println("Historial de transacciones:");
                        for (double transaction : userAccount.getTransactionHistory()) {
                            String type = transaction > 0 ? "Depósito" : "Retiro";
                            System.out.println(type + " de $" + Math.abs(transaction));
                        }
                        break;

                    case "4":
                        System.out.println("Balance actual: $" + df.format(userAccount.getBalance()));
                        break;

                    case "5":
                        if (userAccount instanceof SavingsAccount) {
                            // Solo las SavingsAccount pueden aplicar interés
                            SavingsAccount savingsAccount = (SavingsAccount) userAccount;
                            savingsAccount.applyInterest(); // Aplicar interés
                        } else {
                            System.out.println("Gracias por usar el cajero JavaBank.");
                            exit = true;
                        }
                        break;

                    case "6":
                        if (userAccount instanceof SavingsAccount) {
                            System.out.println("Gracias por usar el cajero JavaBank.");
                            exit = true;
                        }
                        break;

                    default:
                        System.out.println("Opción no válida. Intente de nuevo.");
                }
            }

        } else {
            System.out.println("Acceso Denegado. PIN incorrecto.");
        }
        scanner.close();
    }

    private static double getValidAmount(Scanner scanner, String action) {
        double amount = -1;
        boolean valid = false;
        while (!valid) {
            System.out.println("Ingrese el monto a " + action);
            String input = scanner.nextLine();
            try {
                amount = Double.parseDouble(input);
                if (amount > 0) {
                    valid = true; // Monto válido
                } else {
                    System.out.println("El monto debe ser un número valido, ejemplo 15.60.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor ingrese un número válido.");
            }
        }
        return amount;
    }
}
