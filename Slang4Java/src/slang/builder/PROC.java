package slang.builder;

import slang.contexts.RUNTIME_CONTEXT;
import slang.contexts.SYMBOL_INFO;

public abstract class PROC {
	public abstract SYMBOL_INFO execute(RUNTIME_CONTEXT cont);
}
