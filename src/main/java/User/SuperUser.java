package User;

import bank.Util;

public class SuperUser extends User {

	public void createUser() {
		StringBuilder email = new StringBuilder();
		StringBuilder fname = new StringBuilder();
		StringBuilder lname = new StringBuilder();
		StringBuilder password = new StringBuilder();
		String input;
		int choice = 0;
		System.out.println("Please enter the users email:");
		email.append(Util.getInput().next());
		if (User.getUserByEmail(this, email) != null) {
			System.out.println("The user already exists, please enter a new email");
			return;
		}
		System.out.println("Please enter a the customer's first name.");
		fname.append(Util.getInput().next());
		fname = Util.cleanInput(fname);
		System.out.println("Please enter a the customer's last name.");
		lname.append(Util.getInput().next());
		lname = Util.cleanInput(lname);
		System.out.println("Please enter the users password");
		password.append(Util.getInput().next());

		do {
			System.out.println("Is this a\n1.Customer\n2.Administrator?");
			input = Util.getInput().next();
			try {
				choice = Integer.parseInt(input);
			} catch (Exception e) {
				System.out.println("Please Enter a Number");
				continue;
			}
			switch (choice) {
			case 1:
				Customer.addUser(this, fname, lname, email, password);
				break;
			case 2:
				addUser(this, fname, lname, email, password);
				break;
			default:
				System.out.println("Please enter a valid option.");
				continue;
			}
			break;
		} while (true);

		
	}

	// function called to unlock the user
	public void unlockUser() {
		StringBuilder email = new StringBuilder();
		User lUser;
		do {
			System.out.println("Please enter the users email:");
			email.append(Util.getInput().next());
			lUser = User.getUserByEmail(this, email);
			if (lUser == null) {
				System.out.println("The user does not exist, please try again.");
			} else {
				break;
			}
		} while (true);
		
		if (lUser.isLocked()) {
			User.unlockUser(this, lUser);
			System.out.println(lUser.getLname() + "'s account has been unlocked.");
		} else {
			System.out.println("The user is already unlocked.");
		}
	}
	
	public void seeTransactions() {
		
	}

	private static boolean addUser(User admin, StringBuilder fname, StringBuilder lname, StringBuilder email,
			StringBuilder password) {
		if (admin instanceof SuperUser) {
			User supUser = new SuperUser(fname, lname, email, password);
			User.addUser(supUser);
			return true;
		}
		System.out.println("You do not have permission to perform this action");
		return false;
	}

	private SuperUser(StringBuilder fname, StringBuilder lname, StringBuilder email, StringBuilder password) {
		super(fname, lname, email, password);
	}
	
	public static User login(StringBuilder email, StringBuilder password) {
		return null;
	}

}
