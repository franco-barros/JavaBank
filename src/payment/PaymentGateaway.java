package payment;

public interface PaymentGateaway { // <- Asegúrate de que el nombre aquí coincida
    void pay(double amount);
}