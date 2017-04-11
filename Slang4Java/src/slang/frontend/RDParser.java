package slang.frontend;

import java.util.ArrayList;
import java.util.List;

import slang.ast.expression.BinaryMinus;
import slang.ast.expression.BinaryPlus;
import slang.ast.expression.BooleanConstant;
import slang.ast.expression.Div;
import slang.ast.expression.Exp;
import slang.ast.expression.LogicalExp;
import slang.ast.expression.LogicalNot;
import slang.ast.expression.Mul;
import slang.ast.expression.RelationExp;
import slang.ast.expression.StringLiteral;
import slang.ast.expression.UnaryMinus;
import slang.ast.expression.UnaryPlus;
import slang.ast.expression.Variable;
import slang.ast.expression.WhileStatement;
import slang.ast.meta.RELATION_OPERATOR;
import slang.ast.meta.TYPE_INFO;
import slang.ast.statement.AssignmentStatement;
import slang.ast.statement.IfStatement;
import slang.ast.statement.PrintLineStatement;
import slang.ast.statement.PrintStatement;
import slang.ast.statement.Stmt;
import slang.ast.statement.VariableDeclStatement;
import slang.builder.ProcedureBuilder;
import slang.contexts.SYMBOL_INFO;

public class RDParser extends Lexer{
	
	TOKEN Current_Token;
	TOKEN last_token;

	public RDParser(String Expr) {
		super(Expr);
		// TODO Auto-generated constructor stub
	}
	
	public Exp callExpr(){
		Current_Token = getToken();
		return expr(null);
	}
	
	public ArrayList<Stmt> parse(ProcedureBuilder ctx) {
		getNext();
		return statementList(ctx);
	}

	private ArrayList<Stmt> statementList(ProcedureBuilder ctx) {
		ArrayList<Stmt> arr = new ArrayList<Stmt>();
		while ((Current_Token != TOKEN.TOK_ELSE) && (Current_Token != TOKEN.TOK_ENDIF) && (Current_Token != TOKEN.TOK_WEND)
				&& (Current_Token != TOKEN.TOK_NULL)) {
			Stmt temp = statement(ctx);
			if(temp != null){
				arr.add(temp);
			}
		}
		return arr;
	}

	private Stmt statement(ProcedureBuilder ctx) {
		Stmt retVal = null;
		switch(Current_Token) {
		case TOK_VAR_STRING:
		case TOK_VAR_NUMBER:
		case TOK_VAR_BOOL:
			retVal = parseVariableDeclStatement(ctx);
			getNext();
			return retVal;
		case TOK_PRINT :
			retVal = parsePrintSatement(ctx);
			getNext();
			break;
		case TOK_PRINTLN : 
			retVal = parsePrintLNStatement(ctx);
			getNext();
			break;
		case TOK_UNQUOUTED_STRING:
			retVal = parseAssignmentStatement(ctx);
			getNext();
			return retVal;
		case TOK_IF:
			retVal = parseIfStatement(ctx);
			getNext();
			return retVal;
		case TOK_WHILE:
			retVal = parseWhileStatement(ctx);
			getNext();
			return retVal;
		default : 
			System.out.println("Invalid Statement");
			System.exit(0);
			break;
		
		}
		return retVal;
	}

	private Stmt parseWhileStatement(ProcedureBuilder pb) {
		getNext();
		Exp exp = bExpr(pb);
		if (pb.typeCheck(exp) != TYPE_INFO.TYPE_BOOL) {
			System.out.println("Expects a boolean expression");
			System.exit(0);
		}
		List<Stmt> body = statementList(pb);
		if (Current_Token != TOKEN.TOK_WEND) {
			System.out.println("WEND expected");
			System.exit(0);
		}
		return new WhileStatement(exp, body);
	}

	private Stmt parseIfStatement(ProcedureBuilder pb) {
		getNext();
		List<Stmt> true_part = null;
		List<Stmt> false_part = null;
		Exp exp = bExpr(pb);

		if (pb.typeCheck(exp) != TYPE_INFO.TYPE_BOOL) {
			System.out.println("Expects a boolean expression");
			System.exit(0);
		}
		if (Current_Token != TOKEN.TOK_THEN) {
			System.out.println("THEN part expected");
			System.exit(0);
		}
		getNext();
		true_part = statementList(pb);

		if (Current_Token == TOKEN.TOK_ENDIF) {
			return new IfStatement(exp, true_part, false_part);
		}

		if (Current_Token != TOKEN.TOK_ELSE) {
			System.out.println("ELSE expected");
			System.exit(0);
		}
		getNext();
		false_part = statementList(pb);
		if (Current_Token != TOKEN.TOK_ENDIF) {
			System.out.println("ENDIF expected");
			System.exit(0);
		}
		return new IfStatement(exp, true_part, false_part);
	}

	private Exp bExpr(ProcedureBuilder pb) {
		TOKEN l_token;
		Exp retValue = lExpr(pb);
		while (Current_Token == TOKEN.TOK_AND || Current_Token == TOKEN.TOK_OR) {
			l_token = Current_Token;
			Current_Token = getNext();
			Exp e2 = lExpr(pb);
			retValue = new LogicalExp(l_token, retValue, e2);
		}
		return retValue;
	}

	private Exp lExpr(ProcedureBuilder pb) {
		TOKEN l_token;
		Exp retValue = expr(pb);
		while (Current_Token == TOKEN.TOK_GT || Current_Token == TOKEN.TOK_LT || Current_Token == TOKEN.TOK_GTE || Current_Token == TOKEN.TOK_LTE
				|| Current_Token == TOKEN.TOK_NEQ || Current_Token == TOKEN.TOK_EQ) {
			l_token = Current_Token;
			Current_Token = getNext();
			Exp e2 = expr(pb);
			RELATION_OPERATOR relop = getRelop(l_token);
			retValue = new RelationExp(relop, retValue, e2);
		}
		return retValue;
	}

	private RELATION_OPERATOR getRelop(TOKEN tok) {
		if (tok == TOKEN.TOK_EQ)
			return RELATION_OPERATOR.TOK_EQ;
		else if (tok == TOKEN.TOK_NEQ)
			return RELATION_OPERATOR.TOK_NEQ;
		else if (tok == TOKEN.TOK_GT)
			return RELATION_OPERATOR.TOK_GT;
		else if (tok == TOKEN.TOK_GTE)
			return RELATION_OPERATOR.TOK_GTE;
		else if (tok == TOKEN.TOK_LT)
			return RELATION_OPERATOR.TOK_LT;
		else
			return RELATION_OPERATOR.TOK_LTE;
	}

	private Stmt parseAssignmentStatement(ProcedureBuilder ctx) {
		String variable = last_str;
		SYMBOL_INFO s = ctx.getTable().get(variable);
		if(s == null){
			System.out.println("variable not found");
			System.exit(0);
		}
		getNext();
		if(Current_Token != TOKEN.TOK_ASSIGN){
			System.out.println("= expected");
			System.exit(0);
		}
		getNext();
		Exp exp = bExpr(ctx);
		try {
			if (exp.typeCheck(ctx.getCtx()) != s.getType()) {
				System.out.println("Type mismatch in assignment");
				System.exit(0);
			}
		} catch (Throwable e) {
			System.out.println("Error");
			System.exit(0);
		}
		if(Current_Token != TOKEN.TOK_SEMI){
			System.out.println("; expected");
			System.exit(0);
		}
		
		return new AssignmentStatement(s, exp);
	}

	private Stmt parseVariableDeclStatement(ProcedureBuilder ctx) {
		TOKEN tok = Current_Token;
		getNext();
		if (Current_Token == TOKEN.TOK_UNQUOUTED_STRING) {
			SYMBOL_INFO symb = new SYMBOL_INFO();
			symb.setSymbolName(last_str);
			symb.setType((tok == TOKEN.TOK_VAR_BOOL) ? TYPE_INFO.TYPE_BOOL : (tok == TOKEN.TOK_VAR_NUMBER) ? TYPE_INFO.TYPE_NUMERIC : TYPE_INFO.TYPE_STRING);
			getNext();
			if (Current_Token == TOKEN.TOK_SEMI) {
				ctx.getTable().add(symb);
				return new VariableDeclStatement(symb);

			} else {
				System.out.println("; Expected");
				System.exit(0);
			}
		} else {
			System.out.println("; Expected");
			System.exit(0);
		}
		return null;
	}

	private Stmt parsePrintLNStatement(ProcedureBuilder ctx) {
		getNext();
		Exp a = expr(ctx);
		//Current_Token = getNext();
		a.typeCheck(ctx.getCtx());
		if(Current_Token != TOKEN.TOK_SEMI){
			System.out.println("; is expected");
		}
		return new PrintLineStatement(a);
	}

	private Stmt parsePrintSatement(ProcedureBuilder ctx) {
		getNext();
		Exp a = bExpr(ctx);
		a.typeCheck(ctx.getCtx());
		if(Current_Token != TOKEN.TOK_SEMI){
			System.out.println("; is expected");
		}
		return new PrintStatement(a);
	}

	private TOKEN getNext() {
		last_token = Current_Token;
		Current_Token = getToken();
		return Current_Token;
	}

	public Exp expr(ProcedureBuilder ctx) {
		TOKEN l_token;
		Exp retVal = term(ctx);
		while (Current_Token == TOKEN.TOK_PLUS || Current_Token == TOKEN.TOK_SUB) {
			l_token = Current_Token;
			Current_Token = getToken();
			Exp e1 = expr(ctx);
			if(l_token == TOKEN.TOK_PLUS){
				retVal = new BinaryPlus(retVal, e1);
			} else {
				retVal = new BinaryMinus(retVal, e1);
			}
		}
		return retVal;
	}

	private Exp term(ProcedureBuilder ctx) {
		TOKEN l_token;
		Exp retVal = factor(ctx);
		while(Current_Token == TOKEN.TOK_MUL || Current_Token == TOKEN.TOK_DIV){
			l_token = Current_Token;
			Current_Token = getToken();
			
			Exp e1 = term(ctx);
			if (l_token == TOKEN.TOK_MUL) {
				retVal = new Mul(retVal, e1);
			} else {
				retVal = new Div(retVal, e1);
			}
		}
		return retVal;
	}

	private Exp factor(ProcedureBuilder ctx) {
		TOKEN l_token;
		Exp retVal = null;

		if (Current_Token == TOKEN.TOK_NUMERIC) {
			retVal = new slang.ast.expression.NumericConstant(getNumber());
			Current_Token = getToken();
		} else if (Current_Token == TOKEN.TOK_STRING) {
			retVal = new StringLiteral(last_str);
			Current_Token = getToken();
		} else if (Current_Token == TOKEN.TOK_BOOL_FALSE || Current_Token == TOKEN.TOK_BOOL_TRUE) {
			retVal = new BooleanConstant(Current_Token == TOKEN.TOK_BOOL_TRUE ? true : false);
			Current_Token = getToken();
		} else if(Current_Token == TOKEN.TOK_OPAREN){
			Current_Token = getToken();
			retVal = bExpr(ctx);

			if(Current_Token != TOKEN.TOK_CPAREN){
				System.out.println("Missing closing paranthesis");
				System.exit(0);
			}
			Current_Token = getToken();
		} else if (Current_Token == TOKEN.TOK_PLUS || Current_Token == TOKEN.TOK_SUB) {
			l_token = Current_Token;
			Current_Token = getToken();
			retVal = factor(ctx);
			if (l_token == TOKEN.TOK_PLUS) {
				retVal = new UnaryPlus(retVal);
			} else {
				retVal = new UnaryMinus(retVal);
			}
		} else if (Current_Token == TOKEN.TOK_NOT) {
			l_token = Current_Token;
			Current_Token = getToken();
			retVal = factor(ctx);

			retVal = new LogicalNot(retVal);
		} else if (Current_Token == TOKEN.TOK_UNQUOUTED_STRING) {
			String str = last_str;
			SYMBOL_INFO inf = ctx.getTable().get(str);
			if (inf == null) {
				System.out.println("Undefined Symbol");
			}
			getNext();
			retVal = new Variable(inf);
			
		} else {
			System.out.println("Illegal token");
			System.exit(0);
		}

		return retVal;
	}
}
