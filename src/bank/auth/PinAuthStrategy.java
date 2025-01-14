package bank.auth;

import user.User;

public class PinAuthStrategy implements AuthStrategy {
    private String pin;
    private User user; // Usuario asociado

    // Constructor que inicializa el PIN
    public PinAuthStrategy(String pin) {
        this.pin = pin;
    }

    // Implementación del método authenticate
    @Override
    public boolean authenticate(String data) {
        return pin != null && pin.equals(data);
    }

    // Implementación del método setUser
    @Override
    public void setUser(User user) {
        this.user = user;
        if (user != null) {
            this.pin = user.getPin(); // Asocia el PIN del usuario
        }
    }
}
