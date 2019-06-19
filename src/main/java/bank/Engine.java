package bank;

import user.Customer;
import user.SuperUser;
import user.User;

public class Engine {
	//login function, allows the user to quit if they want to.
	public static void login() {
		User currentUser;
		String email = new String();
		do {
			System.out.println("Please enter your email.");
			email = Util.getInput().next();
			currentUser = User.login(email);
			if (currentUser == null) {
				continue;
			} else if (currentUser instanceof SuperUser) {
				SuperUserIntf((SuperUser) currentUser);
			} else if (currentUser instanceof Customer) {
				CustomerIntf((Customer) currentUser);
			} else {
				System.out.println("I have no clue what you did, but this area of the dungeon should never have been found.");
			}
			if (Engine.askQuit()) {
				break;
			}
		} while (true);

	}

	//Admin Interface
	private static void SuperUserIntf(SuperUser admin) {
		int input = 0;
		do {
			System.out.println("Welcome " + admin.getFname() + " " + admin.getLname());
			System.out.println("Please chose an option.\n1. Create a new user.\n2. Unlock a user.\n"
					+ "3. See the Transactions of an Account.\n4. See All Accounts \n5. Logout.");
			try {
				input = Integer.parseInt(Util.getInput().next());
			} catch (Exception e) {
				System.out.println("Please enter a valid integer!");
				continue;
			}
			switch (input) {
			case 1:
				admin.createUser();
				break;
			case 2:
				admin.unlockUser();
				break;
			case 3:
				admin.seeTransactions();
				break;
			case 4:
				admin.SeeAccounts();
				break;
			case 5:
				System.out.println("Logging out...");
				return;
			default:
				System.out.println("Please enter a valid option!");
				continue;
			}
		} while (true);
	}

	//Customer interface
	private static void CustomerIntf(Customer cust) {
		int input = 0;
		do {
			System.out.println("Welcome " + cust.getFname() + " " + cust.getLname());
			System.out.println("Please enter an option.\n1. Open an Account.\n2. Close an Account.\n3. Make a Deposit"
					+ "\n4. Make a Withdrawl.\n5. Check a Balance\n6. Logout");
			try {
				input = Integer.parseInt(Util.getInput().next());
			} catch (Exception e) {
				System.out.println("Please enter a valid integer!");
				continue;
			}
			switch (input) {
			case 1:
				cust.OpenAccount();
				break;
			case 2:
				cust.CloseAccount();
				break;
			case 3:
				cust.Deposit();
				break;
			case 4:
				cust.Withdraw();
				break;
			case 5:
				cust.checkBalance();
				break;
			case 6:
				System.out.println("Logging out...");
				return;
			default:
				System.out.println("Please enter a valid option!");
				continue;
			}
		} while(true);
	}

	private static boolean askQuit() {
		String input;
		do {
			System.out.println("Would you like to continue? [Y/N]");
			input = Util.getInput().next();
			input = input.toUpperCase();
			if (input.equals("Y") || input.equals("YES")) {
				return false;
			} else if (input.equals("N") || input.equals("NO")) {
				return true;
			}
		} while (true);
	}

}
