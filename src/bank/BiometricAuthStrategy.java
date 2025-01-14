package bank;

import bank.auth.AuthStrategy;

public class BiometricAuthStrategy implements AuthStrategy {
    private final String biometricData;

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
}

