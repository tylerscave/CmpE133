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

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getBank() {
        return bank;
    }

    public String getNameOnAccount() {
        return nameOnAccount;
    }

    public String getRoutingNumber() {
        return routingNumber;
    }
    
    
}
