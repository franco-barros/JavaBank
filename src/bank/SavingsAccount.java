package bank;

public class SavingsAccount extends BankAccount {
    private final double interestRate; // Tasa de interés

    // Constructor para SavingsAccount
    public SavingsAccount(String accountNumber, double balance, double interestRate) {
        super(accountNumber, balance);
        this.interestRate = interestRate;
    }

    // Aplicar interés sobre el saldo de la cuenta de ahorros
    public void applyInterest() {
        double interest = getBalance() * interestRate;
        deposit(interest);  // Método deposit
        System.out.println("Interés aplicado: $" + interest);
    }

    // Obtener la tasa de interés
    public double getInterestRate() {
        return interestRate;
    }
}
