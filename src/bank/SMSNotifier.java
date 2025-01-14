package bank;

public class SMSNotifier implements Observer {
    @Override
    public void update(double balance) {
        System.out.println("SMS: Tu saldo es: " + balance);
    }
}
