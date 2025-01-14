package bank;

import bank.auth.AuthStrategy; // Import correcto del paquete de estrategias de autenticación
import user.User;              // Import necesario para manejar usuarios
import java.util.ArrayList;    // Import de la clase ArrayList de Java estándar

public class Bank {
    private final ArrayList<User> users; // Lista de usuarios asociados al banco
    private AuthStrategy authStrategy; // Estrategia de autenticación actual

    public Bank() {
        this.users = new ArrayList<>(); // Inicialización de la lista de usuarios
    }

    // Permite cambiar la estrategia de autenticación
    public void setAuthStrategy(AuthStrategy authStrategy) {
        this.authStrategy = authStrategy;
    }

    // Agrega un usuario a la lista
    public void addUser(User user) {
        this.users.add(user);
    }

    // Autentica a un usuario basado en los datos de entrada y la estrategia configurada
    public User authenticateUser(String inputData) {
        if (authStrategy == null) {
            throw new IllegalStateException("No authentication strategy set.");
        }

        for (User user : users) {
            // Compara el inputData con el pin del usuario usando la estrategia
            if (authStrategy.authenticate(user.getPin())) {

                if (authStrategy.authenticate(inputData)) {
                    return user;
                }
            }
        }
        return null; // Devuelve null si ningún usuario fue autenticado
    }
}
