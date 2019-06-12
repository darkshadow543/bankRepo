package User;

import java.util.HashMap;
import java.util.Map;

import bank.Util;

public abstract class User {

	private StringBuilder fname = new StringBuilder();
	private StringBuilder lname = new StringBuilder();
	private StringBuilder email = new StringBuilder();
	private transient StringBuilder password = new StringBuilder();
	private boolean isLocked = false;
	private static Map<String, User> UserMap = new HashMap<String, User>();

	protected User(StringBuilder fname, StringBuilder lname, StringBuilder email, StringBuilder password) {
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.password = password;
	}

	// Static user login;
	public static User login(StringBuilder email) {
		User user;
		try {
			user = (User) UserMap.get(email.toString());
		} catch (Exception e) {
			System.out.println(
					"This error should not happen, if you are reading this, the returned object was not a user");
			return null;
		}
		if (user == null) {
			System.out.println("There is no user with this email.  Please enter a valid email");
			return null;
		} else if (user.isLocked) {
			System.out.println("The user for this email is locked.");
			System.out.println("Please contact an adminstator to unlock your account");
			return null;
		}
		int i = 0;
		StringBuilder input = new StringBuilder();
		do {
			System.out.println("Please enter your password");
			input.append(Util.getInput().next());
			if (input.equals(user.getPassword())) {
				return user;
			} else {
				i++;
				System.out.println("The password you have entered is wrong.");
				System.out.println("You have " + (3 - i) + "attempts left.");
				input.delete(0, input.length());
			}
		} while (i < 3);
		user.setLocked(true);
		System.out.println("Your account has been locked.");
		System.out.println("Please contact an administrator to unlock your account");
		return null;
	}

	public StringBuilder getFname() {
		return fname;
	}

	public void setFname(StringBuilder fname) {
		this.fname = fname;
	}

	public StringBuilder getPassword() {
		return password;
	}

	public void setPassword(StringBuilder password) {
		this.password = password;
	}

	public StringBuilder getLname() {
		return lname;
	}

	public void setLname(StringBuilder lname) {
		this.lname = lname;
	}

	public StringBuilder getEmail() {
		return email;
	}

	public void setEmail(StringBuilder email) {
		this.email = email;
	}

	public boolean isLocked() {
		return isLocked;
	}

	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}

	public static void unlockUser(User admin, User user) {
		if (user == null) {
			System.out.println("Please enter a user");
			return;
		}
		if (admin instanceof SuperUser) {
			user.setLocked(false);
			return;
		} else {
			System.out.println("You do not have permission to use this funtion");
			return;
		}
	}
	
	public static User getUserByEmail(User admin, StringBuilder email) {
		User test = UserMap.get(email.toString());
		if (test != null) {
			return test;
		}
		return test;
	}
	
	protected static void addUser(User user) {
		UserMap.put(user.email.toString(), user);
	}

}
