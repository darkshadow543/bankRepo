package user;


import bank.Util;
import dao.DAO;
import dao.UserOracle;

public abstract class User {

	private String fname; 
	private String lname; 
	private String email; 
	private transient StringBuilder password = new StringBuilder();
	private boolean isLocked = false;
	private static final DAO<User> dao = new UserOracle(); 

	public static DAO<User> getDao() {
		return dao;
	}

	protected User(String fname, String lname, String email, StringBuilder password) {
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.password = password;
	}

	// Static user login;
	public static User login(String email) {
		User user;
		try {
			user = dao.getByID(email);
		} catch (Exception e) {
			System.out.println("Unable to retrieve user");
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
			if (user.getPassword().toString().equals(input.toString())) {
				return user;
			} else {
				i++;
				System.out.println("The password you have entered is wrong.");
				System.out.println("You have " + (3 - i) + " attempts left.");
				input.delete(0, input.length());
			}
		} while (i < 3);
		user.setLocked(true);
		System.out.println("Your account has been locked.");
		System.out.println("Please contact an administrator to unlock your account.");
		try {
		dao.insertInto(user); 
		} catch (Exception e) {
			System.out.println("Unable to access database.");
		}
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((fname == null) ? 0 : fname.hashCode());
		result = prime * result + (isLocked ? 1231 : 1237);
		result = prime * result + ((lname == null) ? 0 : lname.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (fname == null) {
			if (other.fname != null)
				return false;
		} else if (!fname.equals(other.fname))
			return false;
		if (isLocked != other.isLocked)
			return false;
		if (lname == null) {
			if (other.lname != null)
				return false;
		} else if (!lname.equals(other.lname))
			return false;
		return true;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public StringBuilder getPassword() {
		return password;
	}

	public void setPassword(StringBuilder password) {
		this.password = password;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isLocked() {
		return isLocked;
	}

	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}

	@Override
	public String toString() {
		return "user [fname=" + fname + ", lname=" + lname + ", email=" + email + ", isLocked=" + isLocked + "]";
	}

}
