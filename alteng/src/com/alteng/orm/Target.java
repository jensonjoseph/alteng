package com.alteng.orm;

public class Target {
	private int amount;
	private int bonus;

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getBonus() {
		return bonus;
	}

	public void setBonus(int bonus) {
		this.bonus = bonus;
	}

	public Target(int amt, int bonus) {
		this.amount = amt;
		this.bonus = bonus;
	}
}
