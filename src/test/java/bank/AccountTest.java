package bank;



import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import account.Account;

public class AccountTest {

	Account taccount;
	@BeforeClass
	public void setUpAccount( ) {
		taccount = new Account(1234 , 1234);
	}
	
	@Before
	public void resetBalance() {
		taccount.setBalance(0.00f);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void addBalanceTest() {
		taccount.deposit(50.00f);
		Assert.assertEquals(taccount.getBalance(), 50.00f);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void withdrawlTest() {
		taccount.setBalance(50.00f);
		taccount.withdraw(25);
		Assert.assertEquals(taccount.getBalance(), 25.00f);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void overdrawTest() {
		taccount.setBalance(24.00f);
		taccount.withdraw(25);
	}
	

}
