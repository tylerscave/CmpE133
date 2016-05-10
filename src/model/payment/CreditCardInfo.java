package model.payment;
/**
 *COPYRIGHT (C) 2016 CmpE133_7. All Rights Reserved.
 * The model for the CreditCard.  
 * Solves CmpE133 SpartanPool
 * @author Tyler Jones
*/

public class CreditCardInfo {
    public enum CardType {
        Visa,
        MasterCard,
        Discover,
        AmericanExpress;
    }
	private String nameOnCard;
	private CardType cardType;
	private String cardNumber;
	private String cardSecurityCode;
	private int expMonth;
	private int expYear;
	
    public CreditCardInfo(String nameOnCard, CardType cardType, String cardNumber, 
    		String cardSecurityCode, int expMonth, int expYear) {
        this.nameOnCard = nameOnCard;
        this.cardType = cardType;
        this.cardNumber = cardNumber;
        this.cardSecurityCode = cardSecurityCode;
        this.expMonth = expMonth;
        this.expYear = expYear;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardSecurityCode() {
        return cardSecurityCode;
    }

    public CardType getCardType() {
        return cardType;
    }

    public int getExpMonth() {
        return expMonth;
    }

    public int getExpYear() {
        return expYear;
    }

    public String getNameOnCard() {
        return nameOnCard;
    }
    
}
