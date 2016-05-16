package model.payment;

/**
 * Stub class for simulating bank account payments; payments always successful.
 * @author David Lerner
 */
public class StubBankAccount implements BankHandler{

    @Override
    public boolean makePayment(BankAccountInfo info, double amount) {
        //stub
        return true;
    }
    
}
