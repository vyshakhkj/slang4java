package slang.frontend;

import slang.ast.expression.Exp;

public class ExpressionBuilder extends AbstractBuilder {
	
	public String _expr_string;
	
	public ExpressionBuilder(String expr) {
		_expr_string = expr;
	}

	public Exp getExpr() {
		RDParser p = new RDParser(_expr_string);
		return p.callExpr();
	}
}
