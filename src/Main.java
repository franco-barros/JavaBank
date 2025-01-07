import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Instancia de cuenta bancaria
        BankAccount account = new BankAccount("123456789", 1500.75, "1234");

        // Entrada del usuario para el PIN
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese su PIN:");
        String inputPin = scanner.nextLine();

        // Intentar autenticar al usuario
        if (account.authenticateUser(inputPin)) {
            System.out.println("Acceso Concedido");

            // Operaciones bancarias
            boolean exit = false;
            while (!exit) {
                System.out.println("\nSeleccione una opción:");
                System.out.println("1. Depositar");
                System.out.println("2. Retirar");
                System.out.println("3. Ver historial de transacciones");
                System.out.println("4. Ver balance actual");
                System.out.println("5. Salir");

                // Opción del menú
                String menuOption = scanner.nextLine();
                if (!isValidNumber(menuOption, false)) {
                    System.out.println("Opción inválida. Por favor, seleccione un número entre 1 y 5.");
                    continue;
                }

                int option = Integer.parseInt(menuOption);

                switch (option) {
                    case 1: // Depositar
                        System.out.println("Ingrese el monto a depositar:");
                        String depositInput = scanner.nextLine();
                        if (isValidNumber(depositInput, true)) {
                            double deposit = Double.parseDouble(depositInput);
                            account.deposit(deposit);
                        } else {
                            System.out.println("Entrada inválida. Ingrese un número válido (por ejemplo: 2.75).");
                        }
                        break;

                    case 2: // Retirar
                        System.out.println("Ingrese el monto a retirar:");
                        String withdrawInput = scanner.nextLine();
                        if (isValidNumber(withdrawInput, true)) {
                            double withdraw = Double.parseDouble(withdrawInput);
                            account.withdraw(withdraw);
                        } else {
                            System.out.println("Entrada inválida. Ingrese un número válido (por ejemplo: 12.75).");
                        }
                        break;

                    case 3: // Ver historial
                        System.out.println("Historial de transacciones:");
                        ArrayList<Double> transactions = account.getTransactionHistory();
                        if (transactions.isEmpty()) {
                            System.out.println("No hay transacciones registradas.");
                        } else {
                            for (int i = 0; i < transactions.size(); i++) {
                                String type = (transactions.get(i) > 0) ? "Depósito" : "Retiro";
                                System.out.println((i + 1) + ": " + type + " de $" + Math.abs(transactions.get(i)));
                            }
                        }
                        break;

                    case 4: // Ver balance
                        System.out.println("Su balance actual es: $" + account.getBalance());
                        break;

                    case 5: // Salir
                        System.out.println("Gracias por usar el cajero JavaBank. Hasta luego :)");
                        exit = true;
                        break;

                    default:
                        System.out.println("Opción no válida. Intente de nuevo.");
                }
            }
        } else {
            System.out.println("Acceso Denegado. Se ha superado el número de intentos.");
        }

        scanner.close();
    }


    public static boolean isValidNumber(String input, boolean allowDecimal) {
        try {
            if (allowDecimal) {
                Double.parseDouble(input); // Intenta convertirlo a double
            } else {
                Integer.parseInt(input); // Intenta convertirlo a entero
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
