package bank;

import bank.auth.AuthStrategy;
import user.User;

public class ATM {
    private AuthStrategy authStrategy;
    private User currentUser;


    public void setAuthStrategy(AuthStrategy authStrategy, User user) {
        this.authStrategy = authStrategy;
        this.currentUser = user;  // Asociamos el usuario con esta instancia del ATM
        authStrategy.setUser(user);  // Configuramos la estrategia con los datos del usuario.
    }


    public boolean authenticateUser(String input) {
        if (authStrategy == null) {
            System.out.println("No se ha configurado una estrategia de autenticación.");
            return false;
        }
        return authStrategy.authenticate(input);
    }


    public User getCurrentUser() {
        return currentUser;
    }
}
