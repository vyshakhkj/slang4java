package slang.ast.expression;

import slang.ast.meta.TYPE_INFO;
import slang.contexts.COMPILATION_CONTEXT;
import slang.contexts.RUNTIME_CONTEXT;
import slang.contexts.SYMBOL_INFO;
import slang.frontend.TOKEN;

public class LogicalExp extends Exp {

	TOKEN m_op;

	private Exp ex1, ex2;

	TYPE_INFO _type;

	public LogicalExp(TOKEN m_op, Exp ex1, Exp ex2) {
		this.m_op = m_op;
		this.ex1 = ex1;
		this.ex2 = ex2;
	}

	@Override
	public SYMBOL_INFO evaluate(RUNTIME_CONTEXT cont) {
		SYMBOL_INFO eval_left = ex1.evaluate(cont);
		SYMBOL_INFO eval_right = ex2.evaluate(cont);

		if (eval_left.getType() == TYPE_INFO.TYPE_BOOL && eval_right.getType() == TYPE_INFO.TYPE_BOOL) {
			SYMBOL_INFO ret_val = new SYMBOL_INFO();
			ret_val.setType(TYPE_INFO.TYPE_BOOL);
			ret_val.setSymbolName("");

			if (m_op == TOKEN.TOK_AND)
				ret_val.setBol_val(eval_left.isBol_val() && eval_right.isBol_val());
			else if (m_op == TOKEN.TOK_OR)
				ret_val.setBol_val(eval_left.isBol_val() || eval_right.isBol_val());
			else
				return null;
			return ret_val;
		}
		return null;
	}

	@Override
	public TYPE_INFO typeCheck(COMPILATION_CONTEXT CONT) {
		TYPE_INFO eval_left = ex1.typeCheck(CONT);
		TYPE_INFO eval_right = ex2.typeCheck(CONT);

		if (eval_left == eval_right && eval_left == TYPE_INFO.TYPE_BOOL) {
			_type = TYPE_INFO.TYPE_BOOL;
		} else {
			System.out.println("Wrong type in expression");
			System.exit(0);
		}
		return null;
	}

	@Override
	public TYPE_INFO get_type() {

		return _type;
	}

}
