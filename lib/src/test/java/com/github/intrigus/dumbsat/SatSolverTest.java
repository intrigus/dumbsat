package com.github.intrigus.dumbsat;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class SatSolverTest {

	@Test
	void testEmptyCNF() {
		SatSolver solver = new SatSolver();
		CNF formula = new CNF();
		assertTrue(solver.DPLL(formula), "An empty CNF is always satisfiable");
	}

	@Test
	void testEmptyDNF() {
		SatSolver solver = new SatSolver();
		CNF formula = new CNF();
		formula.disjunctions().add(new DNF());
		assertFalse(solver.DPLL(formula), "An empty DNF is not satisfiable");
	}

	@Test
	void testUnsatCNF() {
		Literal a = new Literal(1);
		Literal b = new Literal(2);
		SatSolver solver = new SatSolver();
		CNF formula = new CNF();

		DNF dnfPart1 = new DNF();
		dnfPart1.literals().add(a);
		dnfPart1.literals().add(b);

		DNF dnfPart2 = new DNF();
		dnfPart1.literals().add(a.negate());
		dnfPart1.literals().add(b);

		DNF dnfPart3 = new DNF();
		dnfPart1.literals().add(a);
		dnfPart1.literals().add(b.negate());

		DNF dnfPart4 = new DNF();
		dnfPart1.literals().add(a.negate());
		dnfPart1.literals().add(b.negate());

		formula.disjunctions().add(dnfPart1);
		formula.disjunctions().add(dnfPart2);
		formula.disjunctions().add(dnfPart3);
		formula.disjunctions().add(dnfPart4);
		assertFalse(solver.DPLL(formula), "(a ∨ b) ∧ (¬a ∨ b) ∧ (a ∨ ¬b) ∧ (¬a ∨ ¬b) is not satisfiable");
	}

	@Test
	void testSatCNF() {
		Literal a = new Literal(1);
		Literal b = new Literal(2);
		SatSolver solver = new SatSolver();
		CNF formula = new CNF();

		DNF dnfPart1 = new DNF();
		dnfPart1.literals().add(a);
		dnfPart1.literals().add(b);

		DNF dnfPart2 = new DNF();
		dnfPart1.literals().add(a.negate());
		dnfPart1.literals().add(b);

		DNF dnfPart3 = new DNF();
		dnfPart1.literals().add(a);
		dnfPart1.literals().add(b.negate());

		formula.disjunctions().add(dnfPart1);
		formula.disjunctions().add(dnfPart2);
		formula.disjunctions().add(dnfPart3);
		assertFalse(solver.DPLL(formula), "(a ∨ b) ∧ (¬a ∨ b) ∧ (a ∨ ¬b) is satisfiable");
	}

}
