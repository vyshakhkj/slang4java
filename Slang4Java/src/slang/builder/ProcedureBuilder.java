package slang.builder;

import java.util.ArrayList;
import java.util.List;

import slang.ast.expression.Exp;
import slang.ast.meta.TYPE_INFO;
import slang.ast.statement.Stmt;
import slang.contexts.COMPILATION_CONTEXT;
import slang.contexts.SYMBOL_INFO;
import slang.contexts.SymbolTable;

public class ProcedureBuilder extends AbstractBuilder {

	private String proc_name = "";

	COMPILATION_CONTEXT ctx = null;

	List<Object> m_formals = null;

	List<Stmt> m_stmts = new ArrayList<Stmt>();

	TYPE_INFO inf = TYPE_INFO.TYPE_ILLEGAL;

	public ProcedureBuilder(String name, COMPILATION_CONTEXT _ctx) {
		ctx = _ctx;
		proc_name = name;
	}

	public boolean addLocal(SYMBOL_INFO info) {
		ctx.getM_dt().add(info);
		return true;
	}

	public TYPE_INFO typeCheck(Exp e) {
		return e.typeCheck(ctx);
	}

	public void addStatement(Stmt st) {
		m_stmts.add(st);
	}
	
	public SYMBOL_INFO getSymbol(String strname){
		return ctx.getM_dt().get(strname);
	}
	
	public boolean checkProto(String name){
		return true;
	}

	public String getProc_name() {
		return proc_name;
	}

	public void setProc_name(String proc_name) {
		this.proc_name = proc_name;
	}

	public COMPILATION_CONTEXT getCtx() {
		return ctx;
	}

	public void setCtx(COMPILATION_CONTEXT ctx) {
		this.ctx = ctx;
	}

	public TYPE_INFO getInf() {
		return inf;
	}

	public void setInf(TYPE_INFO inf) {
		this.inf = inf;
	}
	
	public SymbolTable getTable() {
		return ctx.getM_dt();
	}

	public Procedure getProcedure() {
		Procedure ret = new Procedure(proc_name, m_stmts, ctx.getM_dt(), inf);
		return ret;
	}
}
