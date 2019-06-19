package user;

import java.util.ArrayList;

import account.Account;
import bank.Util;
import dao.AccountOracle;
import dao.DAO;

public class SuperUser extends User {

	int empID;
	private static final DAO<Account> ADAO = new AccountOracle();
	
	public void createUser() {
		String email = new String();
		String fname = new String();
		String lname = new String();
		StringBuilder password = new StringBuilder();
		String input;
		int choice = 0;
		System.out.println("Please enter the users email:");
		email = Util.getInput().next();
		try {
			if (User.getDao().getByID(email) != null) {
				System.out.println("The user already exists, please enter a new email");
				return;
			}
		} catch (Exception e1) {
			System.out.println("Unable to access Database.");
			return;
		}
		System.out.println("Please enter a the customer's first name.");
		fname = Util.getInput().next();
		fname = Util.cleanInput(new StringBuilder(fname)).toString();
		System.out.println("Please enter a the customer's last name.");
		lname = Util.getInput().next();
		lname = Util.cleanInput(new StringBuilder(lname)).toString();
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
				User cust = new Customer(0, fname, lname, email, password);
				try {
					User.getDao().insertInto(cust);
					System.out.println("Customer Created Sucessfully");
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;
			case 2:
				User emp = new Customer(0, fname, lname, email, password);
				try {
					User.getDao().insertInto(emp);
					System.out.println("Admin Created Sucessfully");
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
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
		String email;
		User lUser;
		do {
			System.out.println("Please enter the users email:");
			email = Util.getInput().next();
			try {
				lUser = User.getDao().getByID(email);
			} catch (Exception e) {
				System.out.println("Unable to Access DataBase");
				return;
			}
			if (lUser == null) {
				System.out.println("The user does not exist, please try again.");
			} else {
				break;
			}
		} while (true);
		
		if (lUser.isLocked()) {
			lUser.setLocked(false);
			try {
				User.getDao().insertInto(lUser);
				System.out.println(lUser.getFname() + " " + lUser.getLname() + "'s account has been unlocked.");
			} catch (Exception e) {
				System.out.println("Unable to unlock user.");
			}
			
		} else {
			System.out.println("The user is already unlocked.");
		}
	}
	
	public void seeTransactions() {
		System.out.println("NYI");
	}
	
	public void SeeAccounts() {
		ArrayList<Account> allAccounts;
		try {
			allAccounts = ADAO.getAll();
		} catch (Exception e) {
			System.out.println("Unable To get Accounts");
			return;
		}
		for (Account account : allAccounts) {
			System.out.println(account);
		}
	}

	public SuperUser(int empID, String fname, String lname, String email, StringBuilder password) {
		super(fname, lname, email, password);
		this.empID = empID;
		this.setLocked(false);
	}
	
	@Override
	public String toString() {
		return "SuperUser [empID=" + empID + "]" + super.toString();
	}

	public static User login(String email, StringBuilder password) {
		return null;
	}

}
