package com.abc;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class Bank {	
	private static final Set<Customer> customers = new HashSet<Customer>();

	private Bank() {}


	public static final boolean balanceTransfer(final Customer customer, final Account fromAccount, final Account toAccount, final String amount) {
		if (amount == null) {
			throw new IllegalArgumentException("Fund must be greater than zero.");			
		}
		
		boolean done = false;		
		//boolean isValid = validateAmount(amount);	
		customers.contains(customer);
		List<Account> accounts = customer.getAccounts();
		accounts.contains(fromAccount);
		accounts.contains(toAccount);
			
		boolean valid = verifyBalance(fromAccount, amount);		
		if (!valid) {
			throw new IllegalArgumentException("Insufficient fund in " + fromAccount.getAccountType().getAccountName());			
		}		
		
		fromAccount.withdraw(amount);	
		toAccount.deposit(amount);							
						
		return done;
	}


	private static boolean verifyBalance(final Account account, final String fund) {
		BigDecimal accountBalance  = account.sumTransactions();
		BigDecimal withdraw  = new BigDecimal(fund);
		
		if (accountBalance.compareTo(withdraw) < 0) {
			return false;
		}
		
		return true;
		
	}

	public static final String customerSummary() {
		StringBuilder summary = new StringBuilder("Customer Summary - ");

		for (Customer cust : customers) {
			summary.append(cust.getCustomerName());
			summary.append(" (");
			summary.append(format(cust.getNumberOfAccounts()));
			summary.append(")");
		}

		return summary.toString();
	}

	// Make sure correct plural of word is created based on the number passed
	// in: If number passed in is 1 just return the word otherwise add an 's' at the
	// end
	private static final String format(int number) {
		if (number > 1) {
			return number + " accounts";
		}
		return number + " account";
	}

	public static final BigDecimal totalInterestPaid() {
		BigDecimal totalInterest = BigDecimal.ZERO;
		for (Customer cust : customers) {
			BigDecimal interestEarned = cust.totalInterestEarned();			
			totalInterest = totalInterest.add(interestEarned);
		}

		return totalInterest;
	}
	
	public static final void addCustomer(final Customer customer) {
		customers.add(customer);
	}
	
	
	public static final void removeAllCustomers() {
		customers.clear();
		return;
	}
}
