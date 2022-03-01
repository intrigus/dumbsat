package com.github.intrigus.dumbsat;

import java.util.ArrayList;
import java.util.List;

public record DNF(List<Literal> literals) {

	public DNF(List<Literal> literals) {
		this.literals = literals;
	}

	public DNF() {
		this(new ArrayList<>());
	}

	public DNF(Literal l) {
		this(List.of(l));
	}

	public DNF deepCopy() {
		List<Literal> copyLiterals = new ArrayList<>(literals.size());
		for (Literal literal : literals) {
			copyLiterals.add(literal.deepCopy());
		}
		return new DNF(copyLiterals);
	}

	public Literal getALiteral() {
		return literals.get(0);
	}

	public boolean containsEmptyClause() {
		return literals.isEmpty();
	}

}
