package slang.frontend;

import slang.ast.meta.ValueTable;

public class Lexer {
	private String IExpr;
	private int index;
	private int length;
	private double number;
	private ValueTable[] keyword = null;
	private TOKEN current_token;
	private TOKEN last_token;
	public String last_str;

	public Lexer(String Expr){
		IExpr = Expr;
		length = IExpr.length();
		index = 0;
		keyword = new ValueTable[13];
		keyword[0] = new ValueTable(TOKEN.TOK_BOOL_FALSE, "FALSE");
		keyword[1] = new ValueTable(TOKEN.TOK_BOOL_TRUE, "TRUE");
		keyword[2] = new ValueTable(TOKEN.TOK_VAR_STRING, "STRING");
		keyword[3] = new ValueTable(TOKEN.TOK_VAR_BOOL, "BOOLEAN");
		keyword[4] = new ValueTable(TOKEN.TOK_VAR_NUMBER, "NUMERIC");
		keyword[5] = new ValueTable(TOKEN.TOK_PRINT, "PRINT");
		keyword[6] = new ValueTable(TOKEN.TOK_PRINTLN, "PRINTLINE");

		keyword[7] = new ValueTable(TOKEN.TOK_IF, "IF");
		keyword[8] = new ValueTable(TOKEN.TOK_WHILE, "WHILE");
		keyword[9] = new ValueTable(TOKEN.TOK_WEND, "WEND");
		keyword[10] = new ValueTable(TOKEN.TOK_ELSE, "ELSE");
		keyword[11] = new ValueTable(TOKEN.TOK_ENDIF, "ENDIF");
		keyword[12] = new ValueTable(TOKEN.TOK_THEN, "THEN");

	}

	public TOKEN getToken() {
		TOKEN tok = TOKEN.ILLEGAL_TOKEN;
		boolean restart = false;
		do {
			while (index < length
					&& (IExpr.charAt(index) == ' ' || IExpr.charAt(index) == '\t' || System.lineSeparator().contains(String.valueOf(IExpr.charAt(index))))) {
				index++;
			}
			if (index == length) {
				return TOKEN.TOK_NULL;
			}
			switch (IExpr.charAt(index)) {
			case '+':
				tok = TOKEN.TOK_PLUS;
				index++;
				break;
			case '-':
				tok = TOKEN.TOK_SUB;
				index++;
				break;
			case '/':
				if (IExpr.charAt(index + 1) == '/') {
					skipToNextLine();
					restart = true;
				} else {
					tok = TOKEN.TOK_DIV;
					index++;
				}
				break;
			case '*':
				tok = TOKEN.TOK_MUL;
				index++;
				break;
			case '(':
				tok = TOKEN.TOK_OPAREN;
				index++;
				break;
			case ')':
				tok = TOKEN.TOK_CPAREN;
				index++;
				break;
			case ';':
				tok = TOKEN.TOK_SEMI;
				index++;
				break;
			case '"': {
				String tempString = "";
				index++;
				while (index < length && IExpr.charAt(index) != '"') {
					tempString += IExpr.charAt(index);
					index++;
				}

				if (index == length) {
					tok = TOKEN.ILLEGAL_TOKEN;
					return tok;
				} else {
					index++;
					last_str = tempString;
					tok = TOKEN.TOK_STRING;
					return tok;
				}
			}
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9': {
				String str = "";
				while (index < length
						&& (IExpr.charAt(index) == '0' || IExpr.charAt(index) == '1' || IExpr.charAt(index) == '2' || IExpr.charAt(index) == '3'
								|| IExpr.charAt(index) == '4' || IExpr.charAt(index) == '5' || IExpr.charAt(index) == '6' || IExpr.charAt(index) == '7'
								|| IExpr.charAt(index) == '8' || IExpr.charAt(index) == '9')) {

					str = str + String.valueOf(IExpr.charAt(index));
					index++;
				}
				number = Double.parseDouble(str);
				tok = TOKEN.TOK_NUMERIC;

			}
			break;
			case '!':
				if (IExpr.charAt(index + 1) == '=') {
					tok = TOKEN.TOK_NEQ;
					index += 2;
				} else {
					tok = TOKEN.TOK_NOT;
					index++;
				}
				break;
			case '>':
				if (IExpr.charAt(index + 1) == '=') {
					tok = TOKEN.TOK_GTE;
					index += 2;
				} else {
					tok = TOKEN.TOK_GT;
					index++;
				}
				break;
			case '<':
				if (IExpr.charAt(index + 1) == '=') {
					tok = TOKEN.TOK_LTE;
					index += 2;
				} else {
					tok = TOKEN.TOK_LT;
					index++;
				}
				break;
			case '=':
				if (IExpr.charAt(index + 1) == '=') {
					tok = TOKEN.TOK_EQ;
					index += 2;
				} else {
					tok = TOKEN.TOK_ASSIGN;
					index++;
				}
				break;
			case '&':
				if (IExpr.charAt(index + 1) == '&') {
					tok = TOKEN.TOK_AND;
					index += 2;
				} else {
					tok = TOKEN.ILLEGAL_TOKEN;
					index++;
				}
				break;
			case '|':
				if (IExpr.charAt(index + 1) == '|') {
					tok = TOKEN.TOK_OR;
					index += 2;
				} else {
					tok = TOKEN.ILLEGAL_TOKEN;
					index++;
				}
				break;
			default:
				if (Character.isLetter(IExpr.charAt(index))) {
					String tempString = String.valueOf(IExpr.charAt(index));
					index++;
					while (index < length && (Character.isLetterOrDigit(IExpr.charAt(index)) || (IExpr.charAt(index) == '_'))) {
						tempString += IExpr.charAt(index);
						index++;
					}
					tempString = tempString.toUpperCase();
					for (int i = 0; i < keyword.length; i++) {
						if (keyword[i].value.compareTo(tempString) == 0) {
							return keyword[i].tok;
						}
					}
					last_str = tempString;
					tok = TOKEN.TOK_UNQUOUTED_STRING;

				} else {

					System.out.println("Error while analyzing tokens");
					tok = TOKEN.ILLEGAL_TOKEN;
				}
			}
		} while (restart);
		return tok;
	}

	public double getNumber() {
		return number;
	}

	private void skipToNextLine() {
		// Moves the index to end of line
		while (index < length && !System.lineSeparator().contains(String.valueOf(IExpr.charAt(index)))) {
			index++;
		}
		// Moves over the line separator characters
		while (index < length && System.lineSeparator().contains(String.valueOf(IExpr.charAt(index)))) {
			index++;
		}
		return;
	}
}
