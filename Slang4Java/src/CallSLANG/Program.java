package CallSLANG;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import slang.ast.statement.Stmt;
import slang.builder.ProcedureBuilder;
import slang.contexts.COMPILATION_CONTEXT;
import slang.contexts.RUNTIME_CONTEXT;
import slang.frontend.RDParser;

public class Program {

	static void TestFileScript(String fileName) {
		String programs2 = "";
		if (fileName == null) {
			return;
		}
		try {
			programs2 = new String(Files.readAllBytes(Paths.get(fileName)));
		} catch (IOException e) {
			System.out.println("File not found");
		}
		RDParser pars = null;
		pars = new RDParser(programs2);

		COMPILATION_CONTEXT ctx = new COMPILATION_CONTEXT();
		ProcedureBuilder pb = new ProcedureBuilder("test", ctx);

		List<Stmt> stmts = pars.parse(pb);

		RUNTIME_CONTEXT f = new RUNTIME_CONTEXT();
		for (Stmt stmt : stmts) {
			stmt.execute(f);
			}
	}

	public static void main(String[] args) {
		if (args == null || args.length != 1) {
			System.out.println("CallSLANG <scriptname>");
			return;
		}
		TestFileScript(args[0]);
	}
}
