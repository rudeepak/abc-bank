package com.abc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class AccountTest {

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Test
	public void accountType() {
		Account account = new Account(AccountType.MAXI_SAVINGS);
		AccountType accountType = account.getAccountType();
		assertEquals(accountType, AccountType.MAXI_SAVINGS);
	}

	@Test
	public void checkingDepositTest() {
		Account account = new Account(AccountType.CHECKING);
		account.deposit("2000");
		BigDecimal interest = account.interestEarned();

		assertTrue(interest.compareTo(new BigDecimal("2.0")) == 0);
	}

	@Test
	public void savingsWithDrawalTest() {
		expectedEx.expect(IllegalArgumentException.class);
		expectedEx.expectMessage("Withdrawal amount must be greater than zero.");

		Account savingsAccount = new Account(AccountType.SAVINGS);
		savingsAccount.deposit("1500");
		BigDecimal interest = savingsAccount.interestEarned();
		assertTrue(interest.compareTo(new BigDecimal("2.0")) == 0);

		savingsAccount.withdraw(null);
	}
	
	@Test
	public void invalidWithDrawalTest() {
		expectedEx.expect(IllegalArgumentException.class);
		expectedEx.expectMessage("Withdrawal amount must be numeric non-zero value.");

		Account account = new Account(AccountType.CHECKING);	
		account.withdraw("abcd");
	}

	@Test
	public void zeroWithDrawalTest() {
		expectedEx.expect(IllegalArgumentException.class);
		expectedEx.expectMessage("Withdrawal amount must be numeric non-zero value.");

		Account account = new Account(AccountType.CHECKING);	
		account.withdraw("0.0");
	}
	
	@Test
	public void transactionsTest() {
		Account savingsAccount = new Account(AccountType.SAVINGS);
		savingsAccount.deposit("100");
		savingsAccount.withdraw("50");
		savingsAccount.deposit("300");
		savingsAccount.withdraw("150");
		List<Transaction> txns = savingsAccount.getTransactions();

		assertEquals(4, txns.size());

	}

	@Test
	public void sumOfTransactionsTest() {
		Account maxiSavingsAccount = new Account(AccountType.MAXI_SAVINGS);
		maxiSavingsAccount.deposit("450");
		maxiSavingsAccount.withdraw("50");
		maxiSavingsAccount.withdraw("150");
		BigDecimal txnSum = maxiSavingsAccount.sumTransactions();

		assertTrue(txnSum.compareTo(new BigDecimal("250.0")) == 0);
	}
	
	@Test
	public void nullDespositTest() {
		expectedEx.expect(IllegalArgumentException.class);
		expectedEx.expectMessage("Deposit amount must be greater than zero.");
		
		Account savingsAccount = new Account(AccountType.SAVINGS);
		savingsAccount.deposit(null);	
		savingsAccount.sumTransactions();
	}
	
	@Test
	public void zeroDespositTest() {
		expectedEx.expect(IllegalArgumentException.class);
		expectedEx.expectMessage("Deposit amount must be numeric non-zero value.");
		
		Account savingsAccount = new Account(AccountType.SAVINGS);
		savingsAccount.deposit("0.0");	
		savingsAccount.sumTransactions();
	}
	
	@Test
	public void midTierInterestCalculationTest() {
		Account maxiSavingsAccount = new Account(AccountType.MAXI_SAVINGS);
		maxiSavingsAccount.deposit("400");
		maxiSavingsAccount.deposit("1200");		
		BigDecimal totalInterestEarned = maxiSavingsAccount.interestEarned();

		assertTrue(new BigDecimal("50.0").compareTo(totalInterestEarned) == 0);
	}
}
