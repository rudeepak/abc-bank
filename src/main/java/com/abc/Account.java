package com.abc;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Account {

	private final AccountType accountType;
	private List<Transaction> transactions;

	public Account(AccountType accountType) {
		this.accountType = accountType;
		this.transactions = new LinkedList<Transaction>();
	}

	public void deposit(String amount) {
		if (amount == null) {
			throw new IllegalArgumentException("Deposit amount must be greater than zero.");
		}
		else {			
			boolean isValid = validateAmount(amount);			
			if (isValid) {
				transactions.add(new Transaction(amount));
			}
			else {
				throw new IllegalArgumentException("Deposit amount must be numeric non-zero value.");
			}
		}
	}

	public void withdraw(String amount) {
		if (amount == null) {
			throw new IllegalArgumentException("Withdrawal amount must be greater than zero.");
		}
		else {
			boolean isValid = validateAmount(amount);
			if (isValid) {
				String strAmount = "-"+amount;
				transactions.add(new Transaction(strAmount));
			}
			else {
				throw new IllegalArgumentException("Withdrawal amount must be numeric non-zero value.");
			}
		}
	}
			
	private boolean validateAmount(final String amount) {				
		boolean hasDigit = isNumeric(amount);
		if (!hasDigit) {
			return false; 
		}		
		
		for (char ch : amount.toCharArray()) { 
			if (ch != '0' && ch != '.') {
				return true;
			}		
		}		
		
		return false;
	}

	public BigDecimal interestEarned() {
		BigDecimal amount = sumTransactions();
		BigDecimal balanceAmt = accountType.applyInterest(amount);

		return balanceAmt;
	}

	public BigDecimal sumTransactions() {
		BigDecimal amount = BigDecimal.ZERO;
		for (Transaction t : transactions) {
			BigDecimal txn = t.getTransactionAmount();
			amount = amount.add(txn);
		}
		return amount;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public List<Transaction> getTransactions() {
		return Collections.unmodifiableList(transactions);
	}
	
	private boolean isNumeric(String str) {
		boolean match = str.matches("\\d*\\.?\\d+");	
		
		return match;
	}
}