package com.abc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;

public class CustomerTest {
	
	// Test customer statement generation
	@Test
	public void testApp() {
		Account checkingAccount = new Account(AccountType.CHECKING);
		Account savingsAccount = new Account(AccountType.SAVINGS);

		Customer henry = new Customer("Henry");
		henry.openAccount(checkingAccount);
		henry.openAccount(savingsAccount);

		checkingAccount.deposit("100.0");
		savingsAccount.deposit("4000.0");
		savingsAccount.withdraw("200.0");

		assertEquals("Bank statement for Henry\n" + "Checking Account\n" + " deposit $100.00\n"
				+ "Total $100.00\n" + "\n" + "Savings Account\n" + " deposit $4,000.00\n"
				+ " withdrawal $200.00\n" + "Total $3,800.00\n" + "\n"
				+ "Total in All Accounts $3,900.00", henry.getStatement());
	}

	@Test
	public void testOneAccount() {
		Customer oscar = new Customer("Oscar");
		oscar.openAccount(new Account(AccountType.SAVINGS));
		assertEquals(1, oscar.getNumberOfAccounts());
	}

	@Test
	public void testTwoAccounts() {
		Customer oscar = new Customer("Oscar");
		oscar.openAccount(new Account(AccountType.SAVINGS));
		oscar.openAccount(new Account(AccountType.CHECKING));

		assertEquals(2, oscar.getNumberOfAccounts());
	}

	@Test
	public void testZeroAcount() {
		Customer zorro = new Customer("Zorro");
		assertEquals(0, zorro.getNumberOfAccounts());
	}
	
	@Test
	public void customerNameTest() {
		Customer mark = new Customer("Mark");		
		assertEquals("Mark", mark.getCustomerName());
	}
	
	
	@Test
	public void customerAccountTest() {
		Customer mark = new Customer("Mark");
		Account checkingAccount = new Account(AccountType.CHECKING);
		Account savingsAccount = new Account(AccountType.SAVINGS);
		
		mark.openAccount(checkingAccount);
		mark.openAccount(savingsAccount);
		List<Account> accounts = mark.getAccounts();
		
		List<Account> expected = Arrays.asList(checkingAccount, savingsAccount);		
	    assertTrue(accounts.size() == expected.size() &&
			        new HashSet<Account>(accounts).equals(new HashSet<Account>(expected)));
	}
	
	@Test
	public void totalInterestPaidTest() {
		Customer steve = new Customer("Steve");		
		Account savingsAccount = new Account(AccountType.SAVINGS);	
		savingsAccount.deposit("2000");
		steve.openAccount(savingsAccount);
		BigDecimal totalInterest = steve.totalInterestEarned();
		assertTrue(totalInterest.compareTo(new BigDecimal("3.0")) == 0);		
	}
}
