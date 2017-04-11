package slang.contexts;


public class RUNTIME_CONTEXT {
	
	private SymbolTable m_dt;
	
	public RUNTIME_CONTEXT() {
		m_dt = new SymbolTable();
	}

	public SymbolTable getM_dt() {
		return m_dt;
	}

	public void setM_dt(SymbolTable m_dt) {
		this.m_dt = m_dt;
	}
	
}
