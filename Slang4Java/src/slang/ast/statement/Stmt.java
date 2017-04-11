package slang.ast.statement;

import slang.contexts.RUNTIME_CONTEXT;
import slang.contexts.SYMBOL_INFO;

public abstract class Stmt {
	public abstract SYMBOL_INFO execute(RUNTIME_CONTEXT con);
}
