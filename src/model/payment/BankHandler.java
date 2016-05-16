package model.payment;

/**
 * Interface for a class that implements bank payments. 
 * @author David Lerner
 */
public interface BankHandler {

    /**
     * Make a payment via bank account.
     * @param info the bank account info
     * @param amount amount to pay
     * @return
     */
    public boolean makePayment(BankAccountInfo info, double amount); 
}
