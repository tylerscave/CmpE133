package model.payment;

/**
 *
 * @author David
 */
public interface CreditCardHandler {
    
    public boolean makePayment(CreditCardInfo info, double amount);
}
