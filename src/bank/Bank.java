package bank;

import user.User;

import java.util.ArrayList;

public class Bank {
    private ArrayList<User> users;

    public Bank() {
        this.users = new ArrayList<>();
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public User authenticateUser(String inputPin) {
        for (User user : users) {
            if (user.getPin().equals(inputPin)) {
                return user;
            }
        }
        return null;
    }
}
