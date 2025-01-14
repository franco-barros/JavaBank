package bank.auth;

public class PinAuthStrategy implements AuthStrategy {
    private final String pin;

    public PinAuthStrategy(String pin) {
        this.pin = pin;
    }

    @Override
    public boolean authenticate(String data) {
        return data != null && data.equals(pin);
    }
}
