package br.ufscar.dc.compiladores.sprgen;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;

public class Principal {

    public static void main(String[] args) {
        CharStream cs;
        try {
            cs = CharStreams.fromFileName(args[0]);
        } catch (IOException exception) {
            System.out.println("Erro ao validar abertura de arquivo");
            return;
        }

        SPRGENLexer lexer = new SPRGENLexer(cs);
        CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);
        SPRGENParser parser = new SPRGENParser(commonTokenStream);

        ErrorListener errorListener = new ErrorListener();
        parser.removeErrorListeners();
        parser.addErrorListener(errorListener);

        // Analise sintática
        SPRGENParser.ProgramaContext programaContext = parser.programa();
        // Análise semântica
        SPRGENSemantic semantic = new SPRGENSemantic();
        semantic.visitPrograma(programaContext);
        if (!ValidateErrorHelper.errosSintaticos.isEmpty()) {
            ValidateErrorHelper.errosSintaticos.forEach(System.out::println);
            System.out.println("Compilação interrompida");
        } else if (!ValidateErrorHelper.errosSemanticos.isEmpty()) {
            ValidateErrorHelper.errosSemanticos.forEach(System.out::println);
            System.out.println("Compilação interrompida.");
        } else {
            System.out.println("Compilado com sucesso.");
        }
    }
}