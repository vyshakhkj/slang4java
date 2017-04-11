package slang.ast.expression;

import slang.ast.meta.TYPE_INFO;
import slang.contexts.COMPILATION_CONTEXT;
import slang.contexts.RUNTIME_CONTEXT;
import slang.contexts.SYMBOL_INFO;

public abstract class Exp {
	public abstract SYMBOL_INFO evaluate(RUNTIME_CONTEXT cont);

	public abstract TYPE_INFO typeCheck(COMPILATION_CONTEXT CONT);
	public abstract TYPE_INFO get_type();
}
