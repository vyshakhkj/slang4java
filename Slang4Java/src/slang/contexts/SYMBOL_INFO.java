package slang.contexts;

import slang.ast.meta.TYPE_INFO;

public class SYMBOL_INFO {
	
	private String symbolName;
	
	private TYPE_INFO type;
	
	private String str_val;
	
	private double dbl_val;
	
	private boolean bol_val;
	
	public SYMBOL_INFO() {
		// TODO Auto-generated constructor stub
	}

	public String getSymbolName() {
		return symbolName;
	}

	public void setSymbolName(String symbolName) {
		this.symbolName = symbolName;
	}

	public TYPE_INFO getType() {
		return type;
	}

	public void setType(TYPE_INFO type) {
		this.type = type;
	}

	public String getStr_val() {
		return str_val;
	}

	public void setStr_val(String str_val) {
		this.str_val = str_val;
	}

	public double getDbl_val() {
		return dbl_val;
	}

	public void setDbl_val(double dbl_val) {
		this.dbl_val = dbl_val;
	}

	public boolean isBol_val() {
		return bol_val;
	}

	public void setBol_val(boolean bol_val) {
		this.bol_val = bol_val;
	}
	
	

}
