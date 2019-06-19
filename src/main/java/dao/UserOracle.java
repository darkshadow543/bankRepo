package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import bank.Util;
import user.Customer;
import user.SuperUser;
import user.User;

public class UserOracle implements DAO<User> {

	private static final Logger log = (Logger) LogManager.getLogger(UserOracle.class);

	public UserOracle() {

	}

	@Override
	public ArrayList<User> getAll() throws Exception {
		Connection con = Util.getServer();

		if (con == null) {
			log.error("The Connection was null");
			throw new Exception("Unable to connect to Database");

		}

		ArrayList<User> users;
		User temp;

		try {

			String sql = "SELECT C.CUSTOMERID, U.FNAME, U.LNAME, U.EMAIL, U.PASSWORD, U.ISLOCKED "
					+ "FROM CUSTOMERS C INNER JOIN USERS U ON C.EMAIL = U.EMAIL";
			PreparedStatement query = con.prepareStatement(sql);

			ResultSet ret = query.executeQuery();
			users = new ArrayList<User>();
			while (ret.next()) {
				temp = new Customer(ret.getInt("CUSTOMERID"), ret.getString("FNAME"), ret.getString("LNAME"),
						ret.getString("EMAIL"), new StringBuilder(ret.getString("PASSWORD")));
				temp.setLocked(ret.getBoolean("ISLOCKED"));
				users.add(temp);
			}

			sql = "SELECT E.EMPNUM, U.FNAME, U.LNAME, U.EMAIL, U.PASSWORD, U.ISLOCKED "
					+ "FROM EMPLOYEE E INNER JOIN USERS U " + "ON E.EMAIL = U.EMAIL";
			query = con.prepareStatement(sql);
			ret = query.executeQuery();
			while (ret.next()) {
				temp = new SuperUser(ret.getInt("EMPNUM"), ret.getString("FNAME"), ret.getString("LNAME"),
						ret.getString("EMAIL"), new StringBuilder(ret.getString("PASSWORD")));
				temp.setLocked(ret.getBoolean("ISLOCKED"));
				users.add(temp);
			}
		} catch (SQLException e) {
			log.error("Unable to execute sql query", e);
			throw new Exception("Unable to connect to database");
		}
		return users;
	}

	/*
	 * This will insert into the Users Table in the Database If the user already
	 * exists it will update the user
	 */
	@Override
	public void insertInto(User object) throws Exception {
		Connection con = Util.getServer();

		if (con == null) {
			log.error("The Connection was null");
			throw new Exception("Unable to connect to Database");

		}
		try {
			User usercomp = this.getByID(object.getEmail());
			String sql;
			PreparedStatement query;
			if (usercomp != null) {
				sql = "UPDATE USERS SET " + "FNAME = ?, " + "LNAME = ?, " + "ISLOCKED = ?, " + "PASSWORD = ?"
						+ "WHERE EMAIL = ?";
				query = con.prepareStatement(sql);
				query.setString(1, object.getFname());
				query.setString(2, object.getLname());
				query.setBoolean(3, object.isLocked());
				query.setString(4, object.getPassword().toString());
				query.setString(5, object.getEmail());
				query.execute();
			} else {
				sql = "INSERT INTO USERS (EMAIL, FNAME, LNAME, PASSWORD) VALUES (?, ?, ?, ?)";
				query = con.prepareStatement(sql);
				query.setString(1, object.getEmail());
				query.setString(2, object.getFname());
				query.setString(3, object.getLname());
				query.setString(4, object.getPassword().toString());
				query.execute();
				if(object instanceof Customer) {
					sql = "INSERT INTO CUSTOMERS (EMAIL) VALUES (?)";
					query = con.prepareStatement(sql);
					query.setString(1, object.getEmail());
					query.execute();
				} else if (object instanceof SuperUser) {
					sql = "INSERT INTO EMPLOYEE (EMAIL) VALUES (?)";
					query = con.prepareStatement(sql);
					query.setString(1, object.getEmail());
					query.execute();
				}
			}

		} catch (SQLException e) {
			log.error("Unable to execute sql query", e);
			throw new Exception("Unable to connect to database");
		}

	}

	@Override
	public User getByID(String key) throws Exception {

		Connection con = Util.getServer();

		if (con == null) {
			log.error("The Connection was null");
			throw new Exception("Unable to connect to Database");

		}

		try {

			String sql = "SELECT FNAME, LNAME, EMAIL, PASSWORD, ISLOCKED " + "FROM USERS " + "WHERE EMAIL = ?";
			PreparedStatement query = con.prepareStatement(sql);
			query.setString(1, key);
			ResultSet ret = query.executeQuery();
			if (!ret.next()) {
				return null;
			}
			sql = "SELECT CUSTOMERID, EMAIL " + "FROM CUSTOMERS " + "WHERE EMAIL = ?";
			query = con.prepareStatement(sql);
			query.setString(1, key);
			ResultSet cust = query.executeQuery();
			if (cust.next()) {
				User Cust = new Customer(cust.getInt("CUSTOMERID"), ret.getString("FNAME"), ret.getString("LNAME"),
						ret.getString("EMAIL"), new StringBuilder(ret.getString("PASSWORD")));
				Cust.setLocked(ret.getBoolean("ISLOCKED"));
				return Cust;
			}
			sql = "SELECT EMPNUM, EMAIL " + "FROM EMPLOYEE " + "WHERE EMAIL = ?";
			query = con.prepareStatement(sql);
			query.setString(1, key);
			ResultSet emp = query.executeQuery();
			if (emp.next()) {
				User empl = new SuperUser(emp.getInt("EMPNUM"), ret.getString("FNAME"), ret.getString("LNAME"),
						ret.getString("EMAIL"), new StringBuilder(ret.getString("PASSWORD")));
				empl.setLocked(ret.getBoolean("ISLOCKED"));
				return empl;
			}
		} catch (SQLException e) {
			log.error("Unable to execute sql query", e);
			throw new Exception("Unable to connect to database");
		}
		return null;
	}
}
