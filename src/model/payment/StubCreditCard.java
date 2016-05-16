package model.payment;

/**
 * Stub class for simulating credit card payments; payments always successful.
 * @author David Lerner
 */
public class StubCreditCard implements CreditCardHandler{

    @Override
    public boolean makePayment(CreditCardInfo info, double amount) {
        //stub
        return true;
    }
    
}
