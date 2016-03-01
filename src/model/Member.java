package model;

public abstract class Member {
	private LoginInformation loginInfo;
	private String lastName;
	private String firstName;
	private Address address;
	private String phoneNumber;
	private MemberType type;
    private PaymentMethod payMethod;
	
	public Member(LoginInformation loginInfo, String lastName, String firstName, Address address, String phoneNumber, MemberType type){
		this.setLoginInfo(loginInfo);
		this.lastName = lastName;
		this.firstName = firstName;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.type = type;
	}
	
	public Address getAddress() {
		return address;
	}
	
	public void setAddress(Address address) {
		this.address = address;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public MemberType getMemberType() {
		return type;
	}

	public void setMemberType(MemberType type) {
		this.type = type;
	}

    public PaymentMethod getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(PaymentMethod payMethod) {
        this.payMethod = payMethod;
    }

	public LoginInformation getLoginInfo() {
		return loginInfo;
	}

	public void setLoginInfo(LoginInformation loginInfo) {
		this.loginInfo = loginInfo;
	}
		
}
