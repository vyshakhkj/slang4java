package slang.ast.statement;

import java.util.List;

import slang.ast.expression.Exp;
import slang.ast.meta.TYPE_INFO;
import slang.contexts.RUNTIME_CONTEXT;
import slang.contexts.SYMBOL_INFO;

public class IfStatement extends Stmt{
	
	private Exp cond;
	
	private List<Stmt> stmts;
	
	private List<Stmt> else_part;

	public IfStatement(Exp c, List<Stmt> s, List<Stmt> e) {
		cond = c;
		stmts = s;
		else_part = e;
	}

	@Override
	public SYMBOL_INFO execute(RUNTIME_CONTEXT con) {
		SYMBOL_INFO m_cond = cond.evaluate(con);
		if(m_cond == null || m_cond.getType() != TYPE_INFO.TYPE_BOOL){
			return null;
		}
		if(m_cond.isBol_val() == true){
			for (Stmt rst : stmts) {
				rst.execute(con);
			}
		} else if(else_part != null){
			for (Stmt rst : else_part) {
				rst.execute(con);
			}
		}
		return null;
	}
	
}
