package slang.ast.statement;

import slang.ast.expression.Exp;
import slang.ast.meta.TYPE_INFO;
import slang.contexts.RUNTIME_CONTEXT;
import slang.contexts.SYMBOL_INFO;

public class PrintStatement extends Stmt{
	
	private Exp _ex;	
	
	public PrintStatement(Exp ex) {
		_ex = ex;
	}

	@Override
	public SYMBOL_INFO execute(RUNTIME_CONTEXT con) {
		SYMBOL_INFO a = null;
		try {
			a = _ex.evaluate(con);
		} catch (Throwable e) {
			System.out.println("Error occurred on execute");
		}
		if(a.getType() == TYPE_INFO.TYPE_NUMERIC){
			System.out.print(a.getDbl_val());
		} else if(a.getType() == TYPE_INFO.TYPE_STRING){
			System.out.print(a.getStr_val());
		} else if (a.getType() == TYPE_INFO.TYPE_BOOL) {
			System.out.print(a.isBol_val());
		}
		return null;
	}

}
