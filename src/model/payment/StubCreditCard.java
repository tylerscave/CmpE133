package model.payment;

/**
 *
 * @author David
 */
public class StubCreditCard implements CreditCardHandler{

    @Override
    public boolean makePayment(CreditCardInfo info, double amount) {
        //stub
        return true;
    }
    
}
