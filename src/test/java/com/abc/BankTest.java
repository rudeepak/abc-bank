package com.abc;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BankTest {
	private static final double DOUBLE_DELTA = 1e-15;
	
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Test
	public void customerSummary() {
		Bank.removeAllCustomers();
		
		Customer john = new Customer("John");
		john.openAccount(new Account(AccountType.CHECKING));
		Bank.addCustomer(john);
		assertEquals("Customer Summary - John (1 account)", Bank.customerSummary());
	}

	@Test
	public void checkingAccountTest() {
		Account checkingAccount = new Account(AccountType.CHECKING);
		Customer bill = new Customer("Bill", checkingAccount);	
		Bank.removeAllCustomers();
		Bank.addCustomer(bill);
		checkingAccount.deposit("100.0");		
		assertEquals(0.1, Bank.totalInterestPaid().doubleValue(), DOUBLE_DELTA);
	}

	@Test
	public void savingsAccountTest() {
		Bank.removeAllCustomers();
		
		Account savingsAccount = new Account(AccountType.SAVINGS);
		Customer tom = new Customer("Tom");
		tom.openAccount(savingsAccount);

		Bank.addCustomer(tom);
		savingsAccount.deposit("1500.0");
		//savingsAccount.deposit("0.0");
		assertEquals(2.0, Bank.totalInterestPaid().doubleValue(), DOUBLE_DELTA);
	}

	@Test
	public void transferFundsTest() {
		Bank.removeAllCustomers();
		
		Account savingsAccount = new Account(AccountType.SAVINGS);
		Account checkingAccount = new Account(AccountType.CHECKING);
		Customer jane = new Customer("Jane");
		jane.openAccount(savingsAccount);
		jane.openAccount(checkingAccount);
		checkingAccount.deposit("500.0");		
		Bank.balanceTransfer(jane, checkingAccount, savingsAccount, "400");		
		
		assertEquals(400.0, savingsAccount.sumTransactions().doubleValue(), DOUBLE_DELTA);
		assertEquals(100.0, checkingAccount.sumTransactions().doubleValue(), DOUBLE_DELTA);
	}
	
	@Test
	public void maxiSavingsAccountTest() {
		Bank.removeAllCustomers();
		
		Account checkingAccount = new Account(AccountType.MAXI_SAVINGS);
		Customer chris = new Customer("Chris", checkingAccount);
		Bank.addCustomer(chris);
		checkingAccount.deposit("3000.0");
		assertEquals(170.0, Bank.totalInterestPaid().doubleValue(), DOUBLE_DELTA);
	}
	
	@Test
	public void transferZeroFund() {
		expectedEx.expect(IllegalArgumentException.class);
		expectedEx.expectMessage("Withdrawal amount must be numeric non-zero value.");
		
		Bank.removeAllCustomers();
		
		Account savingsAccount = new Account(AccountType.SAVINGS);
		Account checkingAccount = new Account(AccountType.CHECKING);
		Customer erin = new Customer("Erin");
		erin.openAccount(savingsAccount);
		erin.openAccount(checkingAccount);
		checkingAccount.deposit("1800.0");		
		Bank.balanceTransfer(erin, checkingAccount, savingsAccount, "0.0");		
		
		assertTrue(new BigDecimal("0.0").compareTo(savingsAccount.sumTransactions()) == 0);
		assertTrue(new BigDecimal("1800.0").compareTo(checkingAccount.sumTransactions()) == 0);
		
	}
	
	@Test
	public void insufficientFundTransferTest() {		
		expectedEx.expect(IllegalArgumentException.class);
		expectedEx.expectMessage("Insufficient fund in Checking Account");	
		
		Bank.removeAllCustomers();
		
		Account savingsAccount = new Account(AccountType.SAVINGS);
		Account checkingAccount = new Account(AccountType.CHECKING);
		Customer jack = new Customer("Jack");
		jack.openAccount(savingsAccount);
		jack.openAccount(checkingAccount);
		checkingAccount.deposit("1800.0");		
		Bank.balanceTransfer(jack, checkingAccount, savingsAccount, "2500.0");		
		
		assertTrue(new BigDecimal("1800.0").compareTo(savingsAccount.sumTransactions()) == 0);
		assertTrue(new BigDecimal("2500.0").compareTo(checkingAccount.sumTransactions()) == 0);
	}
	
	@Test
	public void nullFundTransferTest() {		
		expectedEx.expect(IllegalArgumentException.class);
		expectedEx.expectMessage("Fund must be greater than zero.");	
		
		Bank.removeAllCustomers();
		
		Account savingsAccount = new Account(AccountType.SAVINGS);
		Account checkingAccount = new Account(AccountType.CHECKING);
		Customer kathy = new Customer("Kathy");
		kathy.openAccount(savingsAccount);
		kathy.openAccount(checkingAccount);
		checkingAccount.deposit("300.0");		
		Bank.balanceTransfer(kathy, checkingAccount, savingsAccount, null);		
		
		assertTrue(new BigDecimal("300.0").compareTo(savingsAccount.sumTransactions()) == 0);
		
	}
}
