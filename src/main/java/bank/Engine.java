package bank;

import User.Customer;
import User.SuperUser;
import User.User;

public class Engine {

	public void login() {
		User currentUser;
		StringBuilder email = new StringBuilder();
		do {
			System.out.println("Please enter your email.");
			email.append(Util.getInput().next());
			currentUser = User.login(email);
			if (currentUser == null) {
				continue;
			} else if (currentUser instanceof SuperUser) {
				SuperUserIntf((SuperUser) currentUser);
			} else if (currentUser instanceof Customer) {
				CustomerIntf((Customer) currentUser);
			} else {
				System.out.println("You should never ever see this.");
			}
			if (this.askQuit()) {
				break;
			}
		} while (true);

	}

	private void SuperUserIntf(SuperUser admin) {
		int input = 0;
		do {
			System.out.println("Please chose an option.\n1. Create a new User.\n2. Unlock a User.\n"
					+ "3. See the Transactions of an Account.\n 4. Logout.");
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
				return;
			default:
				System.out.println("Please enter a valid option!");
				continue;
			}
		} while (true);
	}

	private void CustomerIntf(Customer cust) {
		int input = 0;
		do {
			System.out.println("Please enter an option.\n1. Open an Account.\n2. Close an Account.\n3. Make a Deposit"
					+ "\n4. Make a Withdrawl.\n 5. Logout");
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
				return;
			default:
				System.out.println("Please enter a valid option!");
				continue;
			}
			break;
		} while(true);
	}

	private boolean askQuit() {
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
