// Generated from java-escape by ANTLR 4.11.1
package br.ufscar.dc.compiladores.sprgen;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link SPRGENParser}.
 */
public interface SPRGENListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link SPRGENParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(SPRGENParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link SPRGENParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(SPRGENParser.ProgramContext ctx);
}