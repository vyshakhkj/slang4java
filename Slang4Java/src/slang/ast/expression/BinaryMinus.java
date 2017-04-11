package slang.ast.expression;

import slang.ast.meta.TYPE_INFO;
import slang.contexts.COMPILATION_CONTEXT;
import slang.contexts.RUNTIME_CONTEXT;
import slang.contexts.SYMBOL_INFO;

public class BinaryMinus extends Exp {
	
	private Exp exp1,exp2;
	TYPE_INFO _type;
	
	public BinaryMinus(Exp e1, Exp e2) {
		exp1 = e1;
		exp2 = e2;
	}
	
	@Override
	public SYMBOL_INFO evaluate(RUNTIME_CONTEXT cont) {
		SYMBOL_INFO eval_left = exp1.evaluate(cont);
		SYMBOL_INFO eval_right = exp2.evaluate(cont);
		if(eval_left.getType() == TYPE_INFO.TYPE_NUMERIC && eval_right.getType() == TYPE_INFO.TYPE_NUMERIC){
			SYMBOL_INFO ret_val = new SYMBOL_INFO();
			ret_val.setDbl_val(eval_left.getDbl_val() - eval_right.getDbl_val());
			ret_val.setType(TYPE_INFO.TYPE_NUMERIC);
			ret_val.setSymbolName("");
			return ret_val;
		} else {
			System.out.println("Type Mismatch");
			return null;
		}
	}
	@Override
	public TYPE_INFO typeCheck(COMPILATION_CONTEXT cont) {
		TYPE_INFO eval_left = exp1.typeCheck(cont);
		TYPE_INFO eval_right = exp2.typeCheck(cont);
		if(eval_left == eval_right && eval_left == TYPE_INFO.TYPE_NUMERIC){
			_type = eval_left;
			return _type;
		} else {
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
