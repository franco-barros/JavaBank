package bank;

public class EmailNotifier implements Observer {
    @Override
    public void update(double balance) {
        System.out.println("Email: Tu saldo es: " + balance);
    }
}
