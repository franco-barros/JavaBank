package main;

import java.util.Scanner;
import java.text.DecimalFormat;
import bank.*;
import payment.*;
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

        // Ejemplo: Obtener valores del archivo config.properties
        String welcomeMessage = configManager.getProperty("welcome.message");
        String bankName = configManager.getProperty("bank.name");

        // Mostrar un mensaje de bienvenida con los valores obtenidos
        if (welcomeMessage != null) {
            System.out.println(welcomeMessage.replace("{bankName}", bankName != null ? bankName : "JavaBank"));
        } else {
            System.out.println("Bienvenido a JavaBank.");
        }

        // Crear el cajero (ATM) y configurar la estrategia de autenticación
        ATM atm = new ATM();

        // Configurar el menú para la selección de método de autenticación
        Scanner scanner = new Scanner(System.in);
        System.out.println("Seleccione su método de autenticación para el ATM:");
        System.out.println("1. PIN");
        System.out.println("2. Contraseña");
        System.out.println("3. Biometría");

        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                atm.setAuthStrategy(new PinAuthStrategy("1234")); // Usar PIN por defecto
                break;
            case "2":
                atm.setAuthStrategy(new PasswordAuthStrategy("password123")); // Contraseña por defecto
                break;
            case "3":
                atm.setAuthStrategy(new BiometricAuthStrategy("biometricData123")); // Biometría por defecto
                break;
            default:
                System.out.println("Opción no válida.");
                scanner.close();
                return;
        }

        // Realizar la autenticación en el cajero automático (ATM)
        System.out.println("Ingrese su dato de autenticación:");
        String inputData = scanner.nextLine();

        boolean authenticated = atm.authenticateUser(inputData); // Se realiza autenticación en ATM

        if (authenticated) {
            System.out.println("Acceso concedido.");
        } else {
            System.out.println("Acceso Denegado.");
            scanner.close();
            return; // Terminar ejecución si no está autenticado
        }

        // Crear cuentas
        BankAccount account1 = new BankAccount("123456789", 1500.75);
        BankAccount account2 = new SavingsAccount("987654321", 2500.50, 0.05); // Tasa de interés del 5%
        BankAccount account3 = new BankAccount("1011121314", 0.50);
        BankAccount account4 = new BankAccount("1516171819", 500.50);

        // Agregar observadores (por ejemplo, para notificaciones de SMS y Email)
        account1.addObserver(new SMSNotifier());
        account2.addObserver(new EmailNotifier());
        account3.addObserver(new SMSNotifier());
        account4.addObserver(new EmailNotifier());

        // Crear usuarios
        User user1 = new User("Juan Perez", "1234", account1);
        User user2 = new User("Nicolas Roque", "8888", account2);
        User user3 = new User("Roman Garcia", "8080", account3);
        User user4 = new User("Ana Garcia", "5678", account4);

        // Crear el banco y agregar usuarios
        Bank bank = new Bank();
        bank.addUser(user1);
        bank.addUser(user2);
        bank.addUser(user3);
        bank.addUser(user4);

        // Seleccionar la estrategia de autenticación para el banco
        System.out.println("Seleccione el método de autenticación para el Banco:");
        System.out.println("1. PIN");
        System.out.println("2. Contraseña");
        System.out.println("3. Biometría");

        String bankAuthChoice = scanner.nextLine();

        switch (bankAuthChoice) {
            case "1":
                bank.setAuthStrategy(new PinAuthStrategy("1234")); // Usar PIN
                break;
            case "2":
                bank.setAuthStrategy(new PasswordAuthStrategy("password123")); // Usar Contraseña
                break;
            case "3":
                bank.setAuthStrategy(new BiometricAuthStrategy("biometricData123")); // Usar Biometría
                break;
            default:
                System.out.println("Método no válido.");
                break;
        }

        // Realizar autenticación del usuario en el banco
        System.out.println("Ingrese su dato de autenticación (PIN o Contraseña o Biometría):");
        String bankInput = scanner.nextLine();
        User authenticatedUser = bank.authenticateUser(bankInput);

        if (authenticatedUser != null) {
            System.out.println("Acceso concedido para " + authenticatedUser.getName());
            BankAccount userAccount = authenticatedUser.getAccount();

            // Realizar pago
            System.out.println("Ingrese el monto para el pago:");
            double amountToPay = getValidAmount(scanner, "pagar");

            // Crear cuenta de PayPal y adaptar el pago
            PayPal payPal = new PayPal();
            PaymentGateaway paymentGateaway = new PayPalAdapter(payPal);
            paymentGateaway.pay(amountToPay); // Esto usa el adaptador para enviar el pago por PayPal

            boolean exit = false;
            while (!exit) {
                System.out.println("\nSeleccione una opción:");
                System.out.println("1. Depositar");
                System.out.println("2. Retirar");
                System.out.println("3. Ver historial de transacciones");
                System.out.println("4. Ver balance actual");

                // Mostrar opción para aplicar intereses si es cuenta de ahorro
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
