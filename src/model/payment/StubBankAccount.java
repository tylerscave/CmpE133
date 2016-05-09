package model.payment;

/**
 *
 * @author David
 */
public class StubBankAccount implements BankHandler{

    @Override
    public boolean makePayment(BankAccountInfo info, double amount) {
        //stub
        return true;
    }
    
}
