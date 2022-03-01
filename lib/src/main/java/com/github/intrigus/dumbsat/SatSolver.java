package com.github.intrigus.dumbsat;

public class SatSolver {

	public boolean isSat(CNF formula) {
		return DPLL(formula);
	}

	public boolean DPLL(CNF formula) {
		Literal l;
		CNF currentFormula = formula;
		while ((l = currentFormula.getAUnitClause()) != null) {
			currentFormula = unitPropagate(l, currentFormula);
		}
//    while there is a literal l that occurs pure in formula do
//    	formula ← pure-literal-assign(l, formula);
		if (currentFormula.isEmpty()) {
			return true;
		}
		if (currentFormula.containsEmptyClause()) {
			return false;
		}
		l = chooseLiteral(currentFormula);
		return DPLL(currentFormula.and(l)) || DPLL(currentFormula.and(not(l)));
	}

	private Literal not(Literal l) {
		return l.negate();
	}

	private Literal chooseLiteral(CNF formula) {
		return formula.disjunctions().stream().findAny().orElseThrow().getALiteral();
	}

	private CNF unitPropagate(Literal l, CNF formula) {
		CNF copy = formula.deepCopy();
		copy.disjunctions().stream().map(it -> {
			Literal negatedLiteral = not(l);
			if (it.literals().contains(l)) {
				return new DNF();
			} else if (it.literals().contains(negatedLiteral)) {
				return it.deepCopy().literals().remove(negatedLiteral);
			} else {
				return it;
			}
		});
		return copy;
	}
}