package bank;

import bank.auth.AuthStrategy;
import user.User;
import java.util.ArrayList;

public class Bank {
    private final ArrayList<User> users; // Lista de usuarios asociados al banco

    public Bank() {
        this.users = new ArrayList<>(); // Inicialización de la lista de usuarios
    }

    // Agrega un usuario a la lista
    public void addUser(User user) {
        this.users.add(user);
    }

    // Autentica a un usuario basado en la estrategia de autenticación y el inputData
    public User authenticateUser(AuthStrategy authStrategy, String inputData) {
        if (authStrategy == null) {
            throw new IllegalStateException("No authentication strategy set.");
        }

        for (User user : users) {
            authStrategy.setUser(user); // Asocia el usuario con la estrategia.
            if (authStrategy.authenticate(inputData)) {
                return user; // Retorna el usuario autenticado si hay coincidencia.
            }
        }

        return null; // Devuelve null si no hay usuario autenticado.
    }


}
