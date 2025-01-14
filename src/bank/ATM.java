package bank;

import bank.auth.AuthStrategy;

public class ATM {
    private AuthStrategy authStrategy;

    // Establecer la estrategia de autenticación
    public void setAuthStrategy(AuthStrategy authStrategy) {
        this.authStrategy = authStrategy;
    }

    // Autenticar al usuario con los datos proporcionados
    public boolean authenticateUser(String input) {
        if (authStrategy == null) {
            System.out.println("No se ha configurado una estrategia de autenticación.");
            return false;
        }
        return authStrategy.authenticate(input);
    }
}
