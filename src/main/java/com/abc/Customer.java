package com.abc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Customer {
	private static final String WITHDRAWAL = " withdrawal ";
	private static final String DEPOSIT = " deposit ";

	private final String name;
	private final List<Account> accounts;

	public Customer(String name) {
		this.name = name;
		this.accounts = new ArrayList<Account>();
	}

	public Customer(String name, Account account) {
		this(name);
		accounts.add(account);
	}
	
	public String getCustomerName() {
		return name;
	}

	public List<Account> getAccounts() {
		return Collections.unmodifiableList(accounts);
	}

	public boolean openAccount(final Account account) {
		accounts.add(account);
		return true;
	}

	public int getNumberOfAccounts() {
		return accounts.size();
	}

	public BigDecimal totalInterestEarned() {
		BigDecimal sum = BigDecimal.ZERO;
		for (Account acct : accounts) {
			BigDecimal interest = acct.interestEarned();
			sum = sum.add(interest);
		}
		return sum;
	}

	public String getStatement() {
		StringBuilder statement = new StringBuilder("Bank statement for ");
		statement.append(name);
		statement.append("\n");

		BigDecimal allAccountTotal = BigDecimal.ZERO;
		for (Account acct : accounts) {			
			statement.append(statementForAccount(acct));
			allAccountTotal = allAccountTotal.add(acct.sumTransactions());
			statement.append("\n\n");
		}
		statement.append("Total in All Accounts ");
		statement.append(toDollars(allAccountTotal));

		return statement.toString();
	}

	private String statementForAccount(final Account account) {
		// Translate to pretty account type
		AccountType acctType = account.getAccountType();
		StringBuilder str = new StringBuilder(acctType.getAccountName());
		str.append("\n");
		// Now total up all the transactions
		BigDecimal totalTxn = BigDecimal.ZERO;
		for (Transaction txn : account.getTransactions()) {
			BigDecimal txnAmount = txn.getTransactionAmount();
			if (txnAmount.compareTo(BigDecimal.ZERO) < 0) {
				str.append(WITHDRAWAL);
			}
			else {
				str.append(DEPOSIT);
			}

			str.append(toDollars(txnAmount));
			str.append("\n");
			totalTxn = totalTxn.add(txnAmount);
		}
		str.append("Total " + toDollars(totalTxn));
		return str.toString();
	}

	private String toDollars(BigDecimal d) {
		return String.format("$%,.2f", d.abs());
	}
}
