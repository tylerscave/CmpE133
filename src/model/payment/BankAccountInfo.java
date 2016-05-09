package model.payment;

/**
 *
 * @author David
 */
public class BankAccountInfo {
    String nameOnAccount;
    String bank; 
    String accountNumber; 
    String routingNumber;

    public BankAccountInfo(String nameOnAccount, String bank, String accountNumber, String routingNumber) {
        this.nameOnAccount = nameOnAccount;
        this.bank = bank;
        this.accountNumber = accountNumber;
        this.routingNumber = routingNumber;
    }
    
    
}
