package slang.ast.expression;

import slang.ast.meta.TYPE_INFO;
import slang.contexts.COMPILATION_CONTEXT;
import slang.contexts.RUNTIME_CONTEXT;
import slang.contexts.SYMBOL_INFO;

public class LogicalNot extends Exp {

	private Exp ex1;

	TYPE_INFO _type;

	public LogicalNot(Exp e1) {
		ex1 = e1;
	}

	@Override
	public SYMBOL_INFO evaluate(RUNTIME_CONTEXT cont) {
		SYMBOL_INFO eval_left = ex1.evaluate(cont);

		if (eval_left.getType() == TYPE_INFO.TYPE_BOOL) {
			SYMBOL_INFO ret_val = new SYMBOL_INFO();
			ret_val.setType(TYPE_INFO.TYPE_BOOL);
			ret_val.setStr_val("");
			ret_val.setBol_val(!eval_left.isBol_val());
			return ret_val;
		} else {
			return null;
		}
	}

	@Override
	public TYPE_INFO typeCheck(COMPILATION_CONTEXT CONT) {
		TYPE_INFO eval_left = ex1.typeCheck(CONT);
		if (eval_left == TYPE_INFO.TYPE_BOOL) {
			_type = TYPE_INFO.TYPE_BOOL;
			return _type;
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
