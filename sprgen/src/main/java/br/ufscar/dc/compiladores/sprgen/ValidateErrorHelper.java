package br.ufscar.dc.compiladores.sprgen;

import org.antlr.v4.runtime.Token;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValidateErrorHelper {
    public static List<String> errosSemanticos = new ArrayList<>();
    public static List<String> errosSintaticos = new ArrayList<>();

    private static final Map<Integer, String> errorTokens = new HashMap<>();

    static {
        errorTokens.put(SPRGENLexer.UNCLOSED_STRING, "cadeia literal nao fechada");
        errorTokens.put(SPRGENLexer.INVALID_CHAR, "simbolo nao identificado");
    }

    // Recebe um tipo de Token e retorna se está presente no tipo conjunto de token de erro
    public static Boolean isError(int tipoToken) {
        return errorTokens.containsKey(tipoToken);
    }

   // Recebe um Token de erro e o formata
    public static String stringifyError(Token token) {
        StringBuilder tokenStringBuilder = new StringBuilder();
        tokenStringBuilder.append("Linha ");
        tokenStringBuilder.append(token.getLine());
        tokenStringBuilder.append(": ");
        if (token.getType() == SPRGENLexer.INVALID_CHAR) {
            tokenStringBuilder.append(token.getText());
            tokenStringBuilder.append(" - ");
        }
        tokenStringBuilder.append(errorTokens.getOrDefault(token.getType(), ""));
        return tokenStringBuilder.toString();
    }

    // Recebe a localização da linha onde o token falhou e retorna o erro em String
    public static String stringifySyntaxError(int line, Token token) {
        StringBuilder tokenStringBuilder = new StringBuilder();
        tokenStringBuilder.append("Linha ");
        tokenStringBuilder.append(line);
        tokenStringBuilder.append(": erro próximo a ");
        if (token.getType() == SPRGENLexer.EOF) {
            tokenStringBuilder.append("EOF");
        } else {
            tokenStringBuilder.append(token.getText());
        }
        return tokenStringBuilder.toString();
    }

    // Recebe o token e a mensagem do erro, e adiciona na lista de erros
    public static void addErroSemantico(Token tk, String msg) {
        errosSemanticos.add(String.format("Linha %d: %s", tk.getLine(), msg));
    }

    public static void addErroSintatico(Token tk, String msg) {
        errosSintaticos.add(String.format("Linha %d: %s", tk.getLine(), msg));
    }

}
