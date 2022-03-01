package com.github.intrigus.dumbsat;

import java.util.ArrayList;
import java.util.List;

public record CNF(List<DNF> disjunctions) {

	public CNF(List<DNF> disjunctions) {
		this.disjunctions = disjunctions;
	}

	public CNF() {
		this(new ArrayList<>());
	}

	public CNF deepCopy() {
		List<DNF> copyDisjunctions = new ArrayList<>(disjunctions.size());
		for (DNF disjunction : disjunctions) {
			copyDisjunctions.add(disjunction.deepCopy());
		}
		return new CNF(copyDisjunctions);
	}

	public Literal getAUnitClause() {
		return disjunctions.stream().filter(dnf -> dnf.literals().size() == 1).map(dnf -> dnf.literals().get(0))
				.findAny().orElse(null);
	}

	public boolean isEmpty() {
		return this.disjunctions.isEmpty();
	}

	public boolean containsEmptyClause() {
		return this.disjunctions.stream().anyMatch(it -> it.containsEmptyClause());
	}

	public CNF and(Literal l) {
		CNF copy = this.deepCopy();
		copy.disjunctions.add(new DNF(l));
		return copy;
	}
}
