package slang.contexts;

import java.util.HashMap;

import slang.ast.expression.Variable;

public class SymbolTable {
	private HashMap<String, SYMBOL_INFO> dt = new HashMap<String, SYMBOL_INFO>();
	
	public boolean add(SYMBOL_INFO s){
		dt.put(s.getSymbolName(), s);
		return true;
	}
	
	public SYMBOL_INFO get(String name){
		return dt.get(name);	
	}
	
	public void assign(Variable var, SYMBOL_INFO value){
		value.setSymbolName(var.getM_name());
		dt.put(var.getM_name(), value);
	}
	
	public void assign(String var, SYMBOL_INFO value){
		dt.put(var, value);
	}
}
