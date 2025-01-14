package bank.auth;

public interface AuthStrategy {
    boolean authenticate(String data);
}