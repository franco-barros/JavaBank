package payment;

public class PayPalAdapter implements PaymentGateaway {
    private final PayPal payPal;

    public PayPalAdapter(PayPal payPal) {
        this.payPal = payPal;
    }

    @Override
    public void pay(double amount) {
        payPal.sendPayment(amount); // Aquí se llama al método de PayPal para procesar el pago
    }
}
