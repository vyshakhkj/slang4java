package slang.ast.expression;

import slang.ast.meta.TYPE_INFO;
import slang.contexts.COMPILATION_CONTEXT;
import slang.contexts.RUNTIME_CONTEXT;
import slang.contexts.SYMBOL_INFO;

public class Variable extends Exp{
	
	private String m_name;
	TYPE_INFO _type;
	
	public Variable(SYMBOL_INFO inf) {
		m_name = inf.getSymbolName();
	}
	public Variable(COMPILATION_CONTEXT st, String name, double _value) {
		SYMBOL_INFO s = new SYMBOL_INFO();
		s.setSymbolName(name);
		s.setType(TYPE_INFO.TYPE_NUMERIC);
		s.setDbl_val(_value);
		st.getM_dt().add(s);	
		m_name = name;
	}
	
	public Variable(COMPILATION_CONTEXT st, String name, boolean _value) {
		SYMBOL_INFO s = new SYMBOL_INFO();
		s.setSymbolName(name);
		s.setType(TYPE_INFO.TYPE_NUMERIC);
		s.setBol_val(_value);
		st.getM_dt().add(s);	
		m_name = name;
	}
	
	public Variable(COMPILATION_CONTEXT st, String name, String _value) {
		SYMBOL_INFO s = new SYMBOL_INFO();
		s.setSymbolName(name);
		s.setType(TYPE_INFO.TYPE_NUMERIC);
		s.setStr_val(_value);
		st.getM_dt().add(s);	
		m_name = name;
	}
	
	


	public String getM_name() {
		return m_name;
	}
	
	public void setM_name(String m_name) {
		this.m_name = m_name;
	}
	
	
	@Override
	public SYMBOL_INFO evaluate(RUNTIME_CONTEXT cont) {
		if(cont.getM_dt() == null) {
			return null;
		} else {
			SYMBOL_INFO a = cont.getM_dt().get(m_name);
			return a;
		}
	}
	@Override
	public TYPE_INFO typeCheck(COMPILATION_CONTEXT cont) {
		if(cont.getM_dt() == null){
			return TYPE_INFO.TYPE_ILLEGAL;
		} else {
			SYMBOL_INFO a = cont.getM_dt().get(m_name);
			if(a != null){
				_type = a.getType();
				return _type;
			}
			return TYPE_INFO.TYPE_ILLEGAL;
		}
	}

	@Override
	public TYPE_INFO get_type() {
		return _type;
	}
	

}
