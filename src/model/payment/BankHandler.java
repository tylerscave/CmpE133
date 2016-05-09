package model.payment;

/**
 *
 * @author David
 */
public interface BankHandler {
    public boolean makePayment(BankAccountInfo info, double amount);
    
}
