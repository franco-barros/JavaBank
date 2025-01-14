package bank;

import bank.auth.AuthStrategy;
import user.User;

public class BiometricAuthStrategy implements AuthStrategy {
    private final String biometricData;
    private User user; // Usuario asociado

    // Constructor que inicializa los datos biométricos
    public BiometricAuthStrategy(String biometricData) {
        this.biometricData = biometricData;
    }

    // Implementación del método authenticate() definido en AuthStrategy
    @Override
    public boolean authenticate(String data) {
        if (biometricData == null || data == null) {
            return false;
        }
        return this.biometricData.equals(data);
    }

    // Implementación del método setUser(User user)
    @Override
    public void setUser(User user) {
        this.user = user;
    }
}
