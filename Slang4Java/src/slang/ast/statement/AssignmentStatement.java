package slang.ast.statement;

import slang.ast.expression.Exp;
import slang.ast.expression.Variable;
import slang.contexts.RUNTIME_CONTEXT;
import slang.contexts.SYMBOL_INFO;

public class AssignmentStatement extends Stmt {
	private Variable variable;
	private Exp exp1;
	
	public AssignmentStatement(Variable var, Exp e) {
		variable = var;
		exp1 = e;
	}
	
	public AssignmentStatement(SYMBOL_INFO var, Exp e) {
		variable = new Variable(var);
		exp1 = e;
	}

	@Override
	public SYMBOL_INFO execute(RUNTIME_CONTEXT cont) {
		SYMBOL_INFO val = exp1.evaluate(cont);
		cont.getM_dt().assign(variable, val);
		return null;
	}
}
