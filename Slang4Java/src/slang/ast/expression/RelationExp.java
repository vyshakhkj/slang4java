package slang.ast.expression;

import slang.ast.meta.RELATION_OPERATOR;
import slang.ast.meta.TYPE_INFO;
import slang.contexts.COMPILATION_CONTEXT;
import slang.contexts.RUNTIME_CONTEXT;
import slang.contexts.SYMBOL_INFO;

public class RelationExp extends Exp {

	RELATION_OPERATOR m_op;

	private Exp ex1, ex2;

	TYPE_INFO _type;

	TYPE_INFO _optype;

	public RelationExp(RELATION_OPERATOR m_op, Exp ex1, Exp ex2) {
		this.m_op = m_op;
		this.ex1 = ex1;
		this.ex2 = ex2;
	}

	@Override
	public SYMBOL_INFO evaluate(RUNTIME_CONTEXT cont) {
		SYMBOL_INFO eval_left = ex1.evaluate(cont);
		SYMBOL_INFO eval_right = ex2.evaluate(cont);

		SYMBOL_INFO ret_val = new SYMBOL_INFO();
		if (eval_left.getType() == TYPE_INFO.TYPE_NUMERIC && eval_right.getType() == TYPE_INFO.TYPE_NUMERIC) {
			ret_val.setType(TYPE_INFO.TYPE_BOOL);
			ret_val.setSymbolName("");

			if (m_op == RELATION_OPERATOR.TOK_EQ)
				ret_val.setBol_val(eval_left.getDbl_val() == eval_right.getDbl_val());
			else if (m_op == RELATION_OPERATOR.TOK_NEQ)
				ret_val.setBol_val(eval_left.getDbl_val() != eval_right.getDbl_val());
			else if (m_op == RELATION_OPERATOR.TOK_GT)
				ret_val.setBol_val(eval_left.getDbl_val() > eval_right.getDbl_val());
			else if (m_op == RELATION_OPERATOR.TOK_GTE)
				ret_val.setBol_val(eval_left.getDbl_val() >= eval_right.getDbl_val());
			else if (m_op == RELATION_OPERATOR.TOK_LT)
				ret_val.setBol_val(eval_left.getDbl_val() < eval_right.getDbl_val());
			else if (m_op == RELATION_OPERATOR.TOK_LTE)
				ret_val.setBol_val(eval_left.getDbl_val() <= eval_right.getDbl_val());

			return ret_val;
		} else if (eval_left.getType() == TYPE_INFO.TYPE_STRING && eval_right.getType() == TYPE_INFO.TYPE_STRING) {
			ret_val.setType(TYPE_INFO.TYPE_BOOL);
			ret_val.setSymbolName("");
			if (m_op == RELATION_OPERATOR.TOK_EQ) {
				ret_val.setBol_val(eval_left.getStr_val().equals(eval_right.getStr_val()));
			} else if (m_op == RELATION_OPERATOR.TOK_NEQ) {
				ret_val.setBol_val(!eval_left.getStr_val().equals(eval_right.getStr_val()));
			} else {
				ret_val.setBol_val(false);
			}
			return ret_val;
		} else if (eval_left.getType() == TYPE_INFO.TYPE_BOOL && eval_right.getType() == TYPE_INFO.TYPE_BOOL) {
			ret_val.setType(TYPE_INFO.TYPE_BOOL);
			ret_val.setSymbolName("");
			if (m_op == RELATION_OPERATOR.TOK_EQ)
				ret_val.setBol_val(eval_left.isBol_val() == eval_right.isBol_val());
			else if (m_op == RELATION_OPERATOR.TOK_NEQ)
				ret_val.setBol_val(eval_left.isBol_val() != eval_right.isBol_val());

			return ret_val;
		}
		return null;
	}

	@Override
	public TYPE_INFO typeCheck(COMPILATION_CONTEXT CONT) {
		TYPE_INFO eval_left = ex1.typeCheck(CONT);
		TYPE_INFO eval_right = ex2.typeCheck(CONT);

		if (eval_left != eval_right) {
			System.out.println("Wrong type in expression");
			System.exit(0);
		}
		if (eval_left == TYPE_INFO.TYPE_STRING && (!(m_op == RELATION_OPERATOR.TOK_EQ || m_op == RELATION_OPERATOR.TOK_NEQ))) {
			System.out.println("Only == and != supported for string type");
			System.exit(0);
		}
		if (eval_left == TYPE_INFO.TYPE_BOOL && (!(m_op == RELATION_OPERATOR.TOK_EQ || m_op == RELATION_OPERATOR.TOK_NEQ))) {
			System.out.println("Only == and != supported for boolean type");
			System.exit(0);
		}
		_optype = eval_left;
		_type = TYPE_INFO.TYPE_BOOL;
		return _type;
	}

	@Override
	public TYPE_INFO get_type() {

		return _type;
	}

}
