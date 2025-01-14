package main;

import java.util.Scanner;
import java.text.DecimalFormat;
import bank.*;
import user.User;
import config.ConfigManager;
import bank.auth.PinAuthStrategy;
import bank.auth.PasswordAuthStrategy;
import bank.BiometricAuthStrategy;

public class Main {

    private static final DecimalFormat df = new DecimalFormat("#.##");

    public static void main(String[] args) {
        // Cargar configuraciones al inicio del programa
        ConfigManager configManager = ConfigManager.getInstance();
        String welcomeMessage = configManager.getProperty("welcome.message");
        String bankName = configManager.getProperty("bank.name");

        // Mostrar mensaje de bienvenida
        if (welcomeMessage != null) {
            System.out.println(welcomeMessage.replace("{bankName}", bankName != null ? bankName : "JavaBank"));
        } else {
            System.out.println("Bienvenido a JavaBank.");
        }

        // Crear banco y usuarios
        BankAccount account1 = new BankAccount("123456789", 1500.75);
        account1.attach(new EmailNotifier());
        account1.attach(new SMSNotifier());

        SavingsAccount account2 = new SavingsAccount("987654321", 2500.50, 0.05);
        account2.attach(new EmailNotifier());
        account2.attach(new SMSNotifier());

        BankAccount account3 = new BankAccount("1011121314", 0.50);
        account3.attach(new EmailNotifier());
        account3.attach(new SMSNotifier());

        BankAccount account4 = new BankAccount("1516171819", 500.50);
        account4.attach(new EmailNotifier());
        account4.attach(new SMSNotifier());

        User user1 = new User("Juan Perez", "1234", "password123", account1);
        User user2 = new User("Nicolas Roque", "8888", "password456", account2);
        User user3 = new User("Roman Garcia", "8080", "password789", account3);
        User user4 = new User("Ana Garcia", "5678", "password101", account4);

        Bank bank = new Bank();
        bank.addUser(user1);
        bank.addUser(user2);
        bank.addUser(user3);
        bank.addUser(user4);

        // Selección de usuario
        Scanner scanner = new Scanner(System.in);
        System.out.println("Seleccione su usuario:");
        System.out.println("1. Juan Perez ");
        System.out.println("2. Nicolas Roque ");
        System.out.println("3. Roman Garcia ");
        System.out.println("4. Ana Garcia ");

        String userChoice = scanner.nextLine();
        User selectedUser ;
        switch (userChoice) {
            case "1":
                selectedUser = user1;
                break;
            case "2":
                selectedUser = user2;
                break;
            case "3":
                selectedUser = user3;
                break;
            case "4":
                selectedUser = user4;
                break;
            default:
                System.out.println("Opción no válida.");
                scanner.close();
                return;
        }

        // Selección de método de autenticación para el usuario seleccionado
        ATM atm = new ATM();
        System.out.println("Seleccione su método de autenticación:");
        System.out.println("1. PIN");
        System.out.println("2. Contraseña");
        System.out.println("3. Biometría");

        String authChoice = scanner.nextLine();
        switch (authChoice) {
            case "1":
                atm.setAuthStrategy(new PinAuthStrategy(selectedUser.getPin()), selectedUser);
                break;
            case "2":
                atm.setAuthStrategy(new PasswordAuthStrategy(selectedUser.getPassword()), selectedUser);
                break;
            case "3":
                atm.setAuthStrategy(new BiometricAuthStrategy("biometricData123"), selectedUser);
                break;
            default:
                System.out.println("Opción no válida.");
                scanner.close();
                return;
        }

        // Autenticación del usuario seleccionado
        System.out.println("Ingrese su dato de autenticación:");
        String inputData = scanner.nextLine();
        boolean authenticated = atm.authenticateUser(inputData);

        if (authenticated) {
            System.out.println("Acceso concedido para " + selectedUser.getName());
            BankAccount userAccount = selectedUser.getAccount();

            // Menú interactivo
            boolean exit = false;
            while (!exit) {
                System.out.println("\nSeleccione una opción:");
                System.out.println("1. Depositar");
                System.out.println("2. Retirar");
                System.out.println("3. Ver historial de transacciones");
                System.out.println("4. Ver balance actual");

                if (userAccount instanceof SavingsAccount) {
                    System.out.println("5. Aplicar interés");
                    System.out.println("6. Salir");
                } else {
                    System.out.println("5. Salir");
                }

                String menuOption = scanner.nextLine();
                switch (menuOption) {
                    case "1":
                        double deposit = getValidAmount(scanner, "depositar");
                        userAccount.deposit(deposit);
                        break;

                    case "2":
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
                            ((SavingsAccount) userAccount).applyInterest(); // Aplicar interés
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
            System.out.println("Acceso Denegado. No se pudo autenticar.");
        }

        // Cerrar el scanner
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
                    valid = true;
                } else {
                    System.out.println("El monto debe ser mayor que 0.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor ingrese un número válido.");
            }
        }
        return amount;
    }
}
