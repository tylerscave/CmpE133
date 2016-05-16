package model.payment;

/**
 * Interface for a class that implements credit card payments. 
 * @author David Lerner
 */
public interface CreditCardHandler {
    
    /**
     * Make a payment credit card.
     * @param info the credit card info
     * @param amount amount to pay
     * @return
     */
    public boolean makePayment(CreditCardInfo info, double amount);
}
