package br.ufscar.dc.compiladores.sprgen;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;

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

        // Análise sintática
        SPRGENParser.ProgramaContext programaContext = parser.programa();

        // Análise semântica
        SPRGENSemantic semantic = new SPRGENSemantic();
        semantic.visitPrograma(programaContext);

        // Verificação de erros sintáticos
        if (!ValidateErrorHelper.errosSintaticos.isEmpty()) {
            ValidateErrorHelper.errosSintaticos.forEach(System.out::println);
            System.out.println("Compilação interrompida devido a erros sintáticos.");
            return;
        }

        // Verificação de erros semânticos
        if (!ValidateErrorHelper.errosSemanticos.isEmpty()) {
            ValidateErrorHelper.errosSemanticos.forEach(System.out::println);
            System.out.println("Compilação interrompida devido a erros semânticos.");
            return;
        }

        // Geração de código
        SPRGENGenerator sprgenGenerator = new SPRGENGenerator();
        sprgenGenerator.visitPrograma(programaContext);

        // Verificar se o código gerado não está vazio
        String codigoGerado = sprgenGenerator.codigoGerado.toString();
        if (codigoGerado.isEmpty()) {
            System.out.println("Erro: O código gerado está vazio.");
        } else {
            try {
                String path = "Bundle/spr-generated-api/src/main/java/com/example/sprGeneratedApi/SprGeneratedApi.java";
                Files.write(Paths.get(path), Collections.singletonList(codigoGerado));
                System.out.println("Código gerado com sucesso.");
            } catch (IOException e) {
                System.out.println("Erro ao gerar o arquivo SprGeneratedApi.java: " + e.getMessage());
            }
        }
    }
}
