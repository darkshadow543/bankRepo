package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import account.Account;
import bank.Util;

public class AccountOracle implements ChildTableDAO<Account>{

	private static final Logger log = (Logger) LogManager.getLogger(AccountOracle.class);
	
	public AccountOracle() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public ArrayList<Account> getAll() throws Exception {
		Connection con = Util.getServer();

		if (con == null) {
			log.error("The Connection was null");
			throw new Exception("Unable to connect to Database");
		}

		ArrayList<Account> accounts = new ArrayList<Account>();
		Account temp;
		
		try {
			String sql = "SELECT * FROM ACCOUNT";
			PreparedStatement query = con.prepareStatement(sql);

			ResultSet ret = query.executeQuery();
			while(ret.next()) {
				temp = new Account(ret.getInt("ACCOUNTNUM"), ret.getInt("ROUTINGNUM"));
				temp.setBalance(ret.getFloat("BALANCE"));
				temp.setOwner(ret.getInt("CUSTOMERID"));
				accounts.add(temp);
			}
			return accounts;
		} catch (SQLException e) {
			log.error("Unable to execute sql query", e);
			throw new Exception("Unable to connect to database");
		}
	}

	@Override
	public void insertInto(Account object) throws Exception {
		String sql;
		PreparedStatement query;
		Account temp = this.getByID(Integer.toString(object.getAccountNum()));
		Connection con = Util.getServer();
		
		
		if (con == null) {
			log.error("The Connection was null");
			throw new Exception("Unable to connect to Database");
		}
		
		
		if (temp != null) {
			try {
			sql = "UPDATE ACCOUNT SET "
					+ "BALANCE = ? "
					+ "WHERE ACCOUNTNUM = ?";
			query = con.prepareStatement(sql);
			query.setFloat(1, object.getBalance());
			query.setInt(2, object.getAccountNum());
			query.executeUpdate();
			return;
			} catch (SQLException e) {
				log.error("Unable to execute sql query", e);
				throw new Exception("Unable to connect to database");
			}
		} else {
			try {
			sql = "INSERT INTO ACCOUNT (BALANCE, ROUTINGNUM, CUSTOMERID) VALUES ( ?, ?, ?)";
			query = con.prepareStatement(sql);
			query.setFloat(1, object.getBalance());
			query.setInt(2, object.getRoutingNum());
			query.setInt(3, object.getOwner());
			query.executeUpdate();
			} catch (SQLException e) {
				log.error("Unable to execute sql query", e);
				throw new Exception("Unable to connect to database");
			}
		}
	}

	@Override
	public Account getByID(String key) throws Exception {
		Connection con = Util.getServer();
		if (con == null) {
			log.error("The Connection was null");
			throw new Exception("Unable to connect to Database");
		}
		
		Account temp;
		
		try {
			String sql = "SELECT * FROM ACCOUNT WHERE ACCOUNTNUM = ?";
			PreparedStatement query = con.prepareStatement(sql);
			query.setInt(1, Integer.parseInt(key));
			ResultSet ret = query.executeQuery();
			if (ret.next()){
				temp = new Account(ret.getInt("ACCOUNTNUM"), ret.getInt("ROUTINGNUM"));
				temp.setBalance(ret.getFloat("BALANCE"));
				temp.setOwner(ret.getInt("CUSTOMERID"));
				return temp;
			}
			return null;
		} catch (SQLException e) {
			log.error("Unable to execute sql query", e);
			throw new Exception("Unable to connect to database");
		}
	}

	@Override
	public ArrayList<Account> queryByTag(String fk) throws Exception {
		String sql;
		PreparedStatement query;
		Connection con = Util.getServer();
		
		if (con == null) {
			log.error("The Connection was null");
			throw new Exception("Unable to connect to Database");
		}
		
		ArrayList<Account> accounts = new ArrayList<Account>();
		Account temp;
		try {
			sql = "SELECT * FROM ACCOUNT WHERE CUSTOMERID = ?";
			query = con.prepareStatement(sql);
			query.setInt(1, Integer.parseInt(fk));
			ResultSet ret = query.executeQuery();
			while(ret.next()) {
				temp = new Account(ret.getInt("ACCOUNTNUM"), ret.getInt("ROUTINGNUM"));
				temp.setBalance(ret.getFloat("BALANCE"));
				temp.setOwner(ret.getInt("CUSTOMERID"));
				accounts.add(temp);
			}
			return accounts;
		} catch (SQLException e) {
			log.error("Unable to execute sql query", e);
			throw new Exception("Unable to connect to database");
		}
	}

	@Override
	public void close(Account object) throws Exception{
		String sql;
		PreparedStatement query;
		Account temp = this.getByID(Integer.toString(object.getAccountNum()));
		Connection con = Util.getServer();
		
		if (con == null) {
			log.error("The Connection was null");
			throw new Exception("Unable to connect to Database");
		}
		
		if (temp == null) {
			log.error("The Account does not exist.");
			System.out.println("The Account Does not exist.");
		}
		
		try {
			sql = "DELETE FROM ACCOUNT WHERE ACCOUNTNUM = ?";
			query = con.prepareStatement(sql);
			query.setInt(1, object.getAccountNum());
			query.executeUpdate();
		} catch (SQLException e) {
			log.error("Unable to execute sql query", e);
			throw new Exception("Unable to connect to database");
		}
		
	}

}
