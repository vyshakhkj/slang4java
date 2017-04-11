package slang.ast.expression;

import java.util.List;

import slang.ast.meta.TYPE_INFO;
import slang.ast.statement.Stmt;
import slang.contexts.RUNTIME_CONTEXT;
import slang.contexts.SYMBOL_INFO;

public class WhileStatement extends Stmt {

	private Exp cond;

	private List<Stmt> stmts;

	public WhileStatement(Exp c, List<Stmt> s) {
		cond = c;
		stmts = s;
	}

	@Override
	public SYMBOL_INFO execute(RUNTIME_CONTEXT con) {
		do {
			SYMBOL_INFO m_cond = cond.evaluate(con);

			if (m_cond == null || m_cond.getType() != TYPE_INFO.TYPE_BOOL) {
				return null;
			}
			if (m_cond.isBol_val() != true) {
				return null;
			}
			SYMBOL_INFO tsp = null;
			for (Stmt rst : stmts) {
				tsp = rst.execute(con);
				if (tsp != null) {
					return tsp;
				}
			}
		} while (true);
	}

}
