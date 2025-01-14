package bank.auth;

import user.User;

public class PasswordAuthStrategy implements AuthStrategy {
    private String password;
    private User user; // Usuario asociado

    // Constructor que inicializa la contraseña
    public PasswordAuthStrategy(String password) {
        this.password = password;
    }

    // Implementación del método authenticate
    @Override
    public boolean authenticate(String data) {
        return password != null && password.equals(data);
    }

    // Implementación del método setUser
    @Override
    public void setUser(User user) {
        this.user = user;
        if (user != null) {
            this.password = user.getPassword(); // Asocia la contraseña del usuario
        }
    }
}
