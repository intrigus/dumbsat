package com.github.intrigus.dumbsat;

public record Literal(int identifier) {

	public Literal negate() {
		return new Literal(-identifier);
	}

	public boolean isPositive() {
		return identifier > 0;
	}

	public boolean isNegative() {
		return identifier < 0;
	}

	public Literal deepCopy() {
		return new Literal(this.identifier);
	}

}
