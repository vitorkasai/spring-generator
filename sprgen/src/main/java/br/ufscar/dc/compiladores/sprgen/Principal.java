package br.ufscar.dc.compiladores.sprgen;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.misc.ParseCancellationException;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

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

        String outputFileName = inputPath.getFileName().toString().split("\\.")[0] + ".out";
        Path outputOutPath = inputPath.resolveSibling(outputFileName);

        try {


            // Análise sintática
            SPRGENParser.ProgramaContext programaContext = parser.programa();

            // Análise semântica
            SPRGENSemantic semantic = new SPRGENSemantic();
            semantic.visitPrograma(programaContext);

            // Verificação de erros sintáticos
            if (!ValidateErrorHelper.errosSintaticos.isEmpty()) {
                PrintWriter writerOutFile = new PrintWriter(outputOutPath.toString());
                ValidateErrorHelper.errosSintaticos.forEach(e -> writerOutFile.println(e));
                System.out.println("Compilação interrompida devido a erros sintáticos.");
                writerOutFile.println("Compilacao interrompida.");
                writerOutFile.close();
                return;
            }

            // Verificação de erros semânticos
            if (!ValidateErrorHelper.errosSemanticos.isEmpty()) {
                PrintWriter writerOutFile2 = new PrintWriter(outputOutPath.toString());
                ValidateErrorHelper.errosSemanticos.forEach(e -> writerOutFile2.println(e));
                System.out.println("Compilação interrompida devido a erros semânticos.");
                writerOutFile2.println("Compilacao interrompida.");
                writerOutFile2.close();
                return;
            }

            if (ValidateErrorHelper.errosSintaticos.isEmpty() && ValidateErrorHelper.errosSemanticos.isEmpty()) {
                // Geração de código
                SPRGENGenerator sprgenGenerator = new SPRGENGenerator();
                sprgenGenerator.visitPrograma(programaContext);
                String generatedJavaCodePath = "../Bundle/spr-generated-api/src/main/java/com/example/SprGeneratedApi";

                // Verificar se o código gerado não está vazio
                String codigoGerado = sprgenGenerator.codigoGerado.toString();

                if (codigoGerado.isEmpty()) {
                    System.out.println("Erro: O código gerado está vazio.");
                } else {
                    // Gerar o arquivo .java no caminho especificado
                    PrintWriter writer = new PrintWriter(generatedJavaCodePath + "/SprGeneratedApi.java");
                    writer.println(codigoGerado);

                    PrintWriter writerOutFile = new PrintWriter(outputOutPath.toString());
                    writerOutFile.println(codigoGerado);
                    writerOutFile.close();

                    //System.out.println("Código Java gerado com sucesso.");

                    writer.close();

                }
            }
        } catch (ParseCancellationException er) {
            // Verificação de erros sintáticos
            if (!ValidateErrorHelper.errosSintaticos.isEmpty()) {
                try (PrintWriter writerOutFile = new PrintWriter(outputOutPath.toString())) {
                    ValidateErrorHelper.errosSintaticos.forEach(e -> writerOutFile.println(e));
                    writerOutFile.println("Compilacao interrompida.");
                    System.out.println("Compilação interrompida devido a erros sintáticos.");
                } catch (IOException myError) {
                    System.out.println(myError.getMessage());
                }
                return;
            }
            System.out.println(er.getMessage());
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }
}
