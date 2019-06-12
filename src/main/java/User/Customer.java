package User;

public class Customer extends User {

	public static boolean addUser(User admin, StringBuilder fname, StringBuilder lname, StringBuilder email, StringBuilder password) {
		if (admin instanceof SuperUser) {
			User cust = new Customer(fname, lname, email, password);
			User.addUser(cust);
			return true;
		}
		System.out.println("You do not have permission to use this command");
		return false;
	}

	private Customer(StringBuilder fname, StringBuilder lname, StringBuilder email, StringBuilder password) {
		super(fname, lname, email, password);
	}
	
	public void OpenAccount() {
		
	}
	
	public void CloseAccount() {
		
	}
	
	public void Deposit() {
		
	}
	
	public void Withdraw() {
		
	}


}
