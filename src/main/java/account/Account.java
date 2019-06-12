package account;

import java.util.ArrayList;
import java.util.Calendar;

public class Account {
	
	private float balance;
	private transient int accountNum;
	private int routingNum;
	private ArrayList<StringBuilder> Ledger = new ArrayList<StringBuilder>();
	
	public Account() {
		this.balance = 0.0f;
	}
	
	
	public Account(int accountNum, int routingNum) {
		super();
		this.balance = 0;
		this.accountNum = accountNum;
		this.routingNum = routingNum;
	}
	
	public void deposit(float amount) {
		this.balance += amount;
		this.makeLog("Deposit", Float.toString(amount), Float.toString(this.balance));
	}
	
	public void withdraw(float amount) throws IllegalArgumentException{
		if(amount > this.balance) {
			this.makeLog("Withdraw", "OverDraw", Float.toString(this.balance));
			throw new IllegalArgumentException();
		} else {
			this.balance -= amount;
			this.makeLog("Withdraw", Float.toString(amount), Float.toString(this.balance));
		}
	}
	
	
	public float getBalance() {
		return balance;
	}
	public void setBalance(float balance) {
		this.balance = balance;
	}
	public int getAccountNum() {
		return accountNum;
	}
	public void setAccountNum(int accountNum) {
		this.accountNum = accountNum;
		
	}
	public int getRoutingNum() {
		return routingNum;
	}
	public void setRoutingNum(int routingNum) {
		this.routingNum = routingNum;
	}
	
	public void makeLog(String type, String amount, String balance) {
		StringBuilder log = new StringBuilder(type + ": $" + amount + ", Time: ");
		Calendar today = Calendar.getInstance();
		log.append(today);
		log.append(", Balance:" + balance);
	}
	public void printLog() {
		for (StringBuilder log : this.Ledger) {
			System.out.println(log);
		}
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + accountNum;
		result = prime * result + Float.floatToIntBits(balance);
		result = prime * result + routingNum;
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
		Account other = (Account) obj;
		if (accountNum != other.accountNum)
			return false;
		if (Float.floatToIntBits(balance) != Float.floatToIntBits(other.balance))
			return false;
		if (routingNum != other.routingNum)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Account [balance=" + balance + ", accountNum=" + accountNum + ", routingNum=" + routingNum + "]";
	}

}
