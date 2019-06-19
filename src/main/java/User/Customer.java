package User;

import java.util.ArrayList;

import DOA.AccountOracle;
import DOA.ChildTableDAO;
import account.Account;
import bank.Util;

public class Customer extends User {

	private int ID;

	private static final ChildTableDAO<Account> dao = new AccountOracle();

	public Customer(int ID, String fname, String lname, String email, StringBuilder password) {
		super(fname, lname, email, password);
		this.ID = ID;
		this.setLocked(false);
	}

	public void OpenAccount() {
		System.out.println("Creating Account in Database");
		Account inserted = new Account(0, 1234);
		inserted.setOwner(this.ID);
		try {
			dao.insertInto(inserted);
			System.out.println("Accunt Created Sucessfully");
		} catch (Exception e) {
			System.out.println("Unable to access database to insert Account");
			return;
		}
	}

	public void CloseAccount() {
		Account account = this.chooseAccount();
		if (account == null) {
			return;
		}
		try {
			account = dao.getByID(Integer.toString(account.getAccountNum()));
		} catch (Exception e) {
			System.out.println("Unable to update account " + e.getMessage());
			return;
		}
		if (account.getBalance() > 0) {
			System.out.println("The balance is not zero. Please withdrawl all funds before closing Account");
			return;
		}
		try {
			dao.close(account);
			System.out.println("Account Succesfully Closed.");
		} catch (Exception e) {
			System.out.println("Unable to close account.");
			return;
		}
	}

	// Allows the user to insert into thier account
	public void Deposit() {
		Account account = this.chooseAccount();
		if (account == null) {
			return;
		}
		float deposit;
		do {
			try {
				System.out.println("Please enter the ammount that you would like to deposit.");
				deposit = Float.parseFloat(Util.getInput().next());
				break;
			} catch (Exception e) {
				System.out.println("Please enter a valid number");
			}
		} while (true);
		account.deposit(deposit);
		try {
			dao.insertInto(account);
			System.out.println("Deposit Updated");
		} catch (Exception e) {
			System.out.println("Unable to make transaction;");

		}
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public void Withdraw() {
		Account account = this.chooseAccount();
		if (account == null) {
			return;
		}
		float ammount;
		try {
			account = dao.getByID(Integer.toString(account.getAccountNum()));
		} catch (Exception e1) {
			System.out.println("Unable to get updated account.");
			return;
		}
		do {
			try {
				System.out.println("Please enter the ammount that you would like to withdraw.");
				ammount = Float.parseFloat(Util.getInput().next());
				account.withdraw(ammount);
				break;
			} catch (IllegalArgumentException e) {
				System.out.println("Please enter an ammont less than the balance of the account");
			} catch (Exception e) {
				System.out.println("Please enter a valid number");
			}
		} while (true);

		try {
			dao.insertInto(account);
			System.out.println("Withdrawl Sucessful");
		} catch (Exception e) {
			System.out.println("Unable to update account");
		}

	}

	public void checkBalance() {
		Account account = this.chooseAccount();
		if (account == null) {
			return;
		}
		try {
			account = dao.getByID(Integer.toString(account.getAccountNum()));
		} catch (Exception e) {
			System.out.println("Unable to update account.\nThe showing balance may or may not be accurate.");
		}
		if (account == null) {
			System.out.println("Who threw in a potato?");
			return;
		}
		System.out.println("Balance: " + account.getBalance());
	}

	@Override
	public String toString() {
		return "Customer [ID=" + ID + "] " + super.toString();
	}

	// Allows the user to pick an account
	private Account chooseAccount() {
		ArrayList<Integer> accountNums = new ArrayList<Integer>();
		ArrayList<Account> accounts;
		try {
			accounts = dao.queryByTag(Integer.toString(this.getID()));
		} catch (Exception e1) {
			System.out.println("Unable to Access Accounts");
			return null;
		}
		int i = 0;
		int choice;
		if (accounts.size() == 0) {
			System.out.println("You have no accounts and cannot deposit, withdrawal, or close an account at this time");
			return null;
		}
		for (Account account : accounts) {
			accountNums.add(account.getAccountNum());
		}
		do {
			System.out.println("Please Choose an Account");
			i = 0;
			for (Integer integer : accountNums)
				System.out.println(++i + ": " + integer);
			try {
				choice = Integer.parseInt(Util.getInput().next());
			} catch (Exception e) {
				System.out.println("Please Enter a number.");
				continue;
			}
			if (choice <= accounts.size()) {
				return accounts.get(choice - 1);
			}
			System.out.println("Please Enter a Valid Option.");
		} while (true);
	}

}
