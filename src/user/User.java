package user;

import bank.BankAccount;

public class User {
    private String name;
    private String pin;
    private BankAccount account;

    public User(String name, String pin, BankAccount account) {
        this.name = name;
        this.pin = pin;
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public String getPin() {
        return pin;
    }

    public BankAccount getAccount() {
        return account;
    }
}
