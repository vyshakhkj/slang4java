package slang.ast.expression;

import slang.ast.meta.TYPE_INFO;
import slang.contexts.COMPILATION_CONTEXT;
import slang.contexts.RUNTIME_CONTEXT;
import slang.contexts.SYMBOL_INFO;

public class UnaryMinus extends Exp {
	
	private Exp exp1;
	TYPE_INFO _type;
	
	public UnaryMinus(Exp e1) {
		exp1 = e1;
	}

	@Override
	public SYMBOL_INFO evaluate(RUNTIME_CONTEXT cont) {
		SYMBOL_INFO eval_left = exp1.evaluate(cont);
		if(eval_left.getType() == TYPE_INFO.TYPE_NUMERIC){
			SYMBOL_INFO ret_val = new SYMBOL_INFO();
			ret_val.setDbl_val(- eval_left.getDbl_val());
			ret_val.setType(TYPE_INFO.TYPE_NUMERIC);
			ret_val.setSymbolName("");
			return ret_val;
		} else{
			System.out.println("Type Mismatch");
			return null;
		}
	}

	@Override
	public TYPE_INFO typeCheck(COMPILATION_CONTEXT cont) {
		TYPE_INFO eval_left = exp1.typeCheck(cont);
		if(eval_left == TYPE_INFO.TYPE_NUMERIC){
			_type = eval_left;
			return _type;
		} else{
			System.out.println("Type Mismatch failure");
			return null;
		}
	}

	@Override
	public TYPE_INFO get_type() {
		// TODO Auto-generated method stub
		return _type;
	}

	
}
