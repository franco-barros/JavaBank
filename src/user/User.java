package user;

import bank.BankAccount;

public class User {
    private final String name;
    private final String pin;
    private final String password;  // Añadido para almacenar la contraseña
    private final BankAccount account;

    public User(String name, String pin, String password, BankAccount account) {
        this.name = name;
        this.pin = pin;
        this.password = password;
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public String getPin() {
        return this.pin;
    }

    public String getPassword() {  // Método getPassword añadido
        return this.password;
    }

    public BankAccount getAccount() {
        return account;
    }
}
