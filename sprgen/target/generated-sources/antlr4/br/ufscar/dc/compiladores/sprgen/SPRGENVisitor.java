// Generated from java-escape by ANTLR 4.11.1
package br.ufscar.dc.compiladores.sprgen;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link SPRGENParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface SPRGENVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link SPRGENParser#programa}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrograma(SPRGENParser.ProgramaContext ctx);
	/**
	 * Visit a parse tree produced by {@link SPRGENParser#entidades}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEntidades(SPRGENParser.EntidadesContext ctx);
	/**
	 * Visit a parse tree produced by {@link SPRGENParser#entidade}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEntidade(SPRGENParser.EntidadeContext ctx);
	/**
	 * Visit a parse tree produced by {@link SPRGENParser#campo}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCampo(SPRGENParser.CampoContext ctx);
	/**
	 * Visit a parse tree produced by {@link SPRGENParser#endpoints}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEndpoints(SPRGENParser.EndpointsContext ctx);
	/**
	 * Visit a parse tree produced by {@link SPRGENParser#endpoint}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEndpoint(SPRGENParser.EndpointContext ctx);
	/**
	 * Visit a parse tree produced by {@link SPRGENParser#rota}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRota(SPRGENParser.RotaContext ctx);
	/**
	 * Visit a parse tree produced by {@link SPRGENParser#metodoHttp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMetodoHttp(SPRGENParser.MetodoHttpContext ctx);
}