package slang.builder;

import java.util.List;

import slang.ast.meta.TYPE_INFO;
import slang.ast.statement.Stmt;
import slang.contexts.RUNTIME_CONTEXT;
import slang.contexts.SYMBOL_INFO;
import slang.contexts.SymbolTable;

public class Procedure extends PROC {

	public String m_name;

	public List<Object> m_formals = null;

	public List<Stmt> m_statements = null;

	public SymbolTable m_locals = null;

	public SYMBOL_INFO return_value = null;

	public TYPE_INFO _type = TYPE_INFO.TYPE_ILLEGAL;

	public Procedure(String name, List<Stmt> stats, SymbolTable locals, TYPE_INFO type) {
		m_name = name;
		m_formals = null;
		m_statements = stats;
		m_locals = locals;
		_type = type;
	}

	@Override
	public SYMBOL_INFO execute(RUNTIME_CONTEXT cont) {
		for (Stmt stmt : m_statements) {
			stmt.execute(cont);
		}
		return null;
	}

}
