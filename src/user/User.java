package user;

import bank.BankAccount;

public class User {
    private final String name;
    private final String pin;
    private final BankAccount account;

    public User(String name, String pin, BankAccount account) {
        this.name = name;
        this.pin = pin;
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public String getPin() {
        return this.pin;
    }


    public BankAccount getAccount() {
        return account;
    }
}
