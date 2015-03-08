package com.abc;

import java.math.BigDecimal;

public enum AccountType {

	CHECKING("Checking Account", "0.001", "0.0", "0.0") {
		@Override
		public BigDecimal applyInterest(final BigDecimal amount) {
			String interestRate = getTier1Rate();
			return new BigDecimal(interestRate).multiply(amount);
		}
	},
	SAVINGS("Savings Account", "0.001", "0.002", "0.0") {
		BigDecimal minBalance = new BigDecimal("1000");

		@Override
		public BigDecimal applyInterest(final BigDecimal amount) {
			if (minBalance.compareTo(amount) >= 0) {
				String interestRate = getTier1Rate();
				return new BigDecimal(interestRate).multiply(amount);
			}
			else {
				BigDecimal remainingAmt = amount.subtract(minBalance);
				String interestRate = getTier2Rate();
				return BigDecimal.ONE.add(new BigDecimal(interestRate).multiply(remainingAmt));
			}
		}
	},
	MAXI_SAVINGS("Maxi Savings Account", "0.02", "0.05", "0.1") {
		BigDecimal minBalance = new BigDecimal("1000");
		BigDecimal nextLvlBalance = new BigDecimal("2000");

		@Override
		public BigDecimal applyInterest(final BigDecimal amount) {
			if (minBalance.compareTo(amount) >= 0) {
				String interestRate = getTier1Rate();
				return new BigDecimal(interestRate).multiply(amount);
			}
			else if (nextLvlBalance.compareTo(amount) >= 0) {
				BigDecimal remainingAmt = amount.subtract(minBalance);
				String interestRate = getTier2Rate();
				return new BigDecimal("20").add(new BigDecimal(interestRate).multiply(remainingAmt));
			}
			else {
				BigDecimal remainingBalance = amount.subtract(nextLvlBalance);
				String interestRate = getTier3Rate();
				return new BigDecimal("70").add(new BigDecimal(interestRate).multiply(remainingBalance));
			}
		}
	};

	private final String accountName;
	private final String tier1Rate;
	private final String tier2Rate;
	private final String tier3Rate;	

	private AccountType(final String name,  String tier1, String tier2, String tier3) {
		this.accountName = name;
		this.tier1Rate = tier1;
		this.tier2Rate = tier2;
		this.tier3Rate = tier3;
	}

	public abstract BigDecimal applyInterest(BigDecimal amount);

	public String getAccountName() {
		return accountName;
	}
	
	public String getTier1Rate() {
		return tier1Rate;
	}

	public String getTier2Rate() {
		return tier2Rate;
	}
	
	public String getTier3Rate() {
		return tier3Rate;
	}
}
