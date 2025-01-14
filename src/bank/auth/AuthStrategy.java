package bank.auth;
import user.User;

public interface AuthStrategy {
    boolean authenticate(String data);
    void setUser(User user);

}