package bank.auth;

public class PasswordAuthStrategy implements AuthStrategy {
    private final String password;

    // Constructor para la contraseña
    public PasswordAuthStrategy(String password) {
        this.password = password;
    }

    @Override
    public boolean authenticate(String data) {
        return data != null && data.equals(password);
    }
}
