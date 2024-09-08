package br.ufscar.dc.compiladores.sprgen;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

public class Principal {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Uso: java Principal <entrada>");
            return;
        }

        String inputFilePath = args[0];
        Path inputPath = Paths.get(inputFilePath);

        CharStream cs;
        try {
            cs = CharStreams.fromFileName(inputFilePath);
        } catch (IOException e) {
            System.out.println("Erro ao validar abertura de arquivo: " + e.getMessage());
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
        String generatedJavaCodePath = "Bundle/spr-generated-api/src/main/java/com/example/sprGeneratedApi/";

        // Verificar se o código gerado não está vazio
        String codigoGerado = sprgenGenerator.codigoGerado.toString();
        if (codigoGerado.isEmpty()) {
            System.out.println("Erro: O código gerado está vazio.");
        } else {
            try {
                // Gerar o arquivo .java no caminho especificado
                Path outputJavaPath = Paths.get(generatedJavaCodePath, "SprGeneratedApi.java");
                Files.write(outputJavaPath, Collections.singletonList(codigoGerado));
                System.out.println("Código Java gerado com sucesso.");

                // Gerar e escrever o arquivo .out no mesmo local que o arquivo de entrada
                String outputFileName = inputPath.getFileName().toString().split("\\.")[0] + ".out";
                Path outputOutPath = inputPath.resolveSibling(outputFileName);
                Files.write(outputOutPath, Collections.singletonList(codigoGerado));
                System.out.println("Arquivo de saída .out gerado com sucesso.");

            } catch (IOException e) {
                System.out.println("Erro ao gerar os arquivos: " + e.getMessage());
            }
        }
    }
}
