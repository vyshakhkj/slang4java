package slang.ast.expression;

import slang.ast.meta.TYPE_INFO;
import slang.contexts.COMPILATION_CONTEXT;
import slang.contexts.RUNTIME_CONTEXT;
import slang.contexts.SYMBOL_INFO;

public class BooleanConstant extends Exp {
	
	private SYMBOL_INFO info;
	
	public BooleanConstant(boolean pvalue) {
		info = new SYMBOL_INFO();
		info.setSymbolName(null);
		info.setBol_val(pvalue);
		info.setType(TYPE_INFO.TYPE_BOOL);
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
