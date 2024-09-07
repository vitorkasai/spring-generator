// Generated from java-escape by ANTLR 4.11.1
package br.ufscar.dc.compiladores.sprgen;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link SPRGENParser}.
 */
public interface SPRGENListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link SPRGENParser#programa}.
	 * @param ctx the parse tree
	 */
	void enterPrograma(SPRGENParser.ProgramaContext ctx);
	/**
	 * Exit a parse tree produced by {@link SPRGENParser#programa}.
	 * @param ctx the parse tree
	 */
	void exitPrograma(SPRGENParser.ProgramaContext ctx);
	/**
	 * Enter a parse tree produced by {@link SPRGENParser#entidades}.
	 * @param ctx the parse tree
	 */
	void enterEntidades(SPRGENParser.EntidadesContext ctx);
	/**
	 * Exit a parse tree produced by {@link SPRGENParser#entidades}.
	 * @param ctx the parse tree
	 */
	void exitEntidades(SPRGENParser.EntidadesContext ctx);
	/**
	 * Enter a parse tree produced by {@link SPRGENParser#entidade}.
	 * @param ctx the parse tree
	 */
	void enterEntidade(SPRGENParser.EntidadeContext ctx);
	/**
	 * Exit a parse tree produced by {@link SPRGENParser#entidade}.
	 * @param ctx the parse tree
	 */
	void exitEntidade(SPRGENParser.EntidadeContext ctx);
	/**
	 * Enter a parse tree produced by {@link SPRGENParser#campo}.
	 * @param ctx the parse tree
	 */
	void enterCampo(SPRGENParser.CampoContext ctx);
	/**
	 * Exit a parse tree produced by {@link SPRGENParser#campo}.
	 * @param ctx the parse tree
	 */
	void exitCampo(SPRGENParser.CampoContext ctx);
	/**
	 * Enter a parse tree produced by {@link SPRGENParser#endpoints}.
	 * @param ctx the parse tree
	 */
	void enterEndpoints(SPRGENParser.EndpointsContext ctx);
	/**
	 * Exit a parse tree produced by {@link SPRGENParser#endpoints}.
	 * @param ctx the parse tree
	 */
	void exitEndpoints(SPRGENParser.EndpointsContext ctx);
	/**
	 * Enter a parse tree produced by {@link SPRGENParser#endpoint}.
	 * @param ctx the parse tree
	 */
	void enterEndpoint(SPRGENParser.EndpointContext ctx);
	/**
	 * Exit a parse tree produced by {@link SPRGENParser#endpoint}.
	 * @param ctx the parse tree
	 */
	void exitEndpoint(SPRGENParser.EndpointContext ctx);
	/**
	 * Enter a parse tree produced by {@link SPRGENParser#rota}.
	 * @param ctx the parse tree
	 */
	void enterRota(SPRGENParser.RotaContext ctx);
	/**
	 * Exit a parse tree produced by {@link SPRGENParser#rota}.
	 * @param ctx the parse tree
	 */
	void exitRota(SPRGENParser.RotaContext ctx);
	/**
	 * Enter a parse tree produced by {@link SPRGENParser#metodoHttp}.
	 * @param ctx the parse tree
	 */
	void enterMetodoHttp(SPRGENParser.MetodoHttpContext ctx);
	/**
	 * Exit a parse tree produced by {@link SPRGENParser#metodoHttp}.
	 * @param ctx the parse tree
	 */
	void exitMetodoHttp(SPRGENParser.MetodoHttpContext ctx);
}