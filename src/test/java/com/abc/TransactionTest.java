package com.abc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;

public class TransactionTest {
	@Test
	public void transaction() {
		Transaction t = new Transaction("5");
		assertTrue(t instanceof Transaction);
	}
	
	@Test
	public void recordTransaction() {
		Transaction txn = new Transaction("900");		
		BigDecimal fundAvaialble = txn.getTransactionAmount();
		assertEquals(new BigDecimal("900.0").compareTo(fundAvaialble),0);		
	}
}