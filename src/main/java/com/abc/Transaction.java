package com.abc;

import java.math.BigDecimal;

public class Transaction {
	private final String transactionAmount;
	private final long transactionTime;

	public Transaction(String amount) {
		transactionAmount = amount;
		transactionTime = System.currentTimeMillis();
	}

	public long getTransactionTime() {
		return transactionTime;
	}

	public BigDecimal getTransactionAmount() {
		return new BigDecimal(transactionAmount);
	}
}
