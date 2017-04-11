package slang.ast.expression;

import slang.ast.meta.TYPE_INFO;
import slang.contexts.COMPILATION_CONTEXT;
import slang.contexts.RUNTIME_CONTEXT;
import slang.contexts.SYMBOL_INFO;

public class NumericConstant extends Exp {
	
	private SYMBOL_INFO info;
	
	public NumericConstant(double pvaleu) {
		info = new SYMBOL_INFO();
		info.setDbl_val(pvaleu);
		info.setSymbolName(null);
		info.setType(TYPE_INFO.TYPE_NUMERIC);
	}

	@Override
	public SYMBOL_INFO evaluate(RUNTIME_CONTEXT cont) {
		// TODO Auto-generated method stub
		return info;
	}

	@Override
	public TYPE_INFO typeCheck(COMPILATION_CONTEXT CONT) {
		// TODO Auto-generated method stub
		return info.getType();
	}

	@Override
	public TYPE_INFO get_type() {
		// TODO Auto-generated method stub
		return info.getType();
	}
	

}
