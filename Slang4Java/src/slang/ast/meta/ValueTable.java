package slang.ast.meta;

import slang.frontend.TOKEN;

public class ValueTable {

	public TOKEN tok;
	
	public String value;
	
	public ValueTable(TOKEN tok, String value) {
		this.tok = tok;
		this.value = value;
	}
}
