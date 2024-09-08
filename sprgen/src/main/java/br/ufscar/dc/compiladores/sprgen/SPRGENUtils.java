package br.ufscar.dc.compiladores.sprgen;

import com.ibm.icu.impl.IllegalIcuArgumentException;

public class SPRGENUtils {

    // Recebe uma cadeia e mapeia para o tipo adequado
    public static Tipo mapStringToTipo(String tipo) {
        switch (tipo) {
            case "String":
                return Tipo.STRING;
            case "Long":
                return Tipo.LONG;
            case "boolean":
                return Tipo.BOOLEAN;
            default:
                return Tipo.OBJETO;
        }
    }

    // Recebe uma cadeia e mapeia para o tipo MetodoHttp
    public static Rota.MetodoHttp mapStringToMetodoHttp(String tipo) {
        switch (tipo) {
            case "GET":
                return Rota.MetodoHttp.GET;
            case "POST":
                return Rota.MetodoHttp.POST;
            case "PUT":
                return Rota.MetodoHttp.PUT;
            case "DELETE":
                return Rota.MetodoHttp.DELETE;
            default:
                return Rota.MetodoHttp.Invalido;
        }
    }

    // Recebe um URL e extrai o ID caso exista do path variable
    public static String extractRotaId(String url) {
        if (url.contains("{")) {
            String secondHalf = url.split("\\{")[1];  // escape para o '{'
            if (!secondHalf.contains("/")) {
                if (!secondHalf.contains("}")) {
                    throw new IllegalArgumentException("Fechamento de chaves ( } ) necessário.");
                }
                return secondHalf.substring(0, secondHalf.length() - 2);
            }
            else {
                String aux = secondHalf.split("/")[0];
                if (!aux.contains("}")) {
                    throw new IllegalArgumentException("Fechamento de chaves ( } ) necessário.");
                }
                return aux.substring(0, aux.length() - 2);
            }

        } else {
            if (url.contains("}")) {
                throw new IllegalArgumentException("Abertura de chaves ( { ) necessária.");
            }
        }
        return null;
    }

    // Recebe um tipo e devolve seu valor em String
    public static String mapTipoToString(Tipo tipo) {
        if (tipo != Tipo.STRING && tipo != Tipo.LONG && tipo != Tipo.BOOLEAN) {
            return Tipo.STRING.getValue();
        }
        else {
            return tipo.getValue();
        }
    }
}
