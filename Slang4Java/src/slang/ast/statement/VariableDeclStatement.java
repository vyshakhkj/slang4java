package slang.ast.statement;

import slang.ast.expression.Variable;
import slang.contexts.RUNTIME_CONTEXT;
import slang.contexts.SYMBOL_INFO;

public class VariableDeclStatement extends Stmt{
	SYMBOL_INFO m_inf = null;
	Variable var = null;
	
	public VariableDeclStatement(SYMBOL_INFO inf) {
		m_inf = inf;
	}

	@Override
	public SYMBOL_INFO execute(RUNTIME_CONTEXT cont) {
		cont.getM_dt().add(m_inf);
		var = new Variable(m_inf);
		return null;
	}

}
