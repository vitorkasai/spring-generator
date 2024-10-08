package br.ufscar.dc.compiladores.sprgen;

import org.antlr.v4.runtime.Token;

import java.util.HashMap;
import java.util.Map;

public class SPRGENSemantic extends SPRGENBaseVisitor<Void> {
    Map<String, Entidade> entidades;
    Map<String, Endpoint> endpoints;

    @Override
    public Void visitPrograma(SPRGENParser.ProgramaContext ctx) {
        entidades = new HashMap<>();
        endpoints = new HashMap<>();

        return super.visitPrograma(ctx);
    }

    @Override
    public Void visitEntidade(SPRGENParser.EntidadeContext ctx) {
        String id = ctx.IDENT().getText();
        if (entidades.containsKey(id)) {
            erroIdentJaDeclarado(id, ctx.IDENT().getSymbol()); // Verifica se já existe entidade com o mesmo nome
            return null;
        }

        Entidade entidade = new Entidade();

        boolean entidadeContemId = false;

        for (SPRGENParser.CampoContext campo : ctx.campos) {
            String nomeCampo = campo.ident.getText();

            if (nomeCampo.equals("id") && campo.tipo.getText().equals("Long")) {
                entidadeContemId = true;
            }

            if (entidade.contains(nomeCampo)) {
                erroIdentJaDeclarado(nomeCampo, campo.ident); // Verifica se já existe atributo com o mesmo nome
            }
            if (nomeCampo.equals("dataCriacao") || nomeCampo.equals("dataAlteracao")) {
                erroNomeNaoPermitido(nomeCampo, campo.ident);
            }
            Tipo tipo = SPRGENUtils.mapStringToTipo(campo.tipo.getText());
            if (tipo == Tipo.OBJETO) {
                erroIdentNaoPermitidoNoEscopo(campo.tipo.getText(), campo.tipo);
            }
            entidade.add(tipo, nomeCampo);
        }

        if (!entidadeContemId) {
            erroEntidadeSemId(id, ctx.getStart());
        }

        entidades.put(id, entidade);
        return super.visitEntidade(ctx);
    }

    @Override
    public Void visitEndpoint(SPRGENParser.EndpointContext ctx) {
        Token rotaPontoGetStart = null;
        try {
            String id = ctx.IDENT().getText();

            if (!entidades.containsKey(id)) {
                erroIdentNaoDeclarado(id, ctx.IDENT().getSymbol()); // Verifica se não existe entidade com o nome de endpoint
                return null;
            }

            if (endpoints.containsKey(id)) {
                erroIdentJaDeclarado(id, ctx.IDENT().getSymbol()); // Verifica se já existe endpoint com o nome declarado
                return null;
            }

            Endpoint endpoint = new Endpoint();
            for (SPRGENParser.RotaContext rota : ctx.rotas) {
                rotaPontoGetStart = rota.getStart();

                String url = rota.STRING().getText();
                Rota.MetodoHttp metodoHttp = SPRGENUtils.mapStringToMetodoHttp(rota.metodoHttp().getText());

                if (endpoint.contains(metodoHttp, url)) {
                    erroRotaJaDeclarada(rota.metodoHttp().getText(), url, rota.getStart());
                }

                String rotaId = SPRGENUtils.extractRotaId(url);
                if (rotaId != null) {
                    if (metodoHttp == Rota.MetodoHttp.POST) {
                        erroNaoDeveConterId(rota.metodoHttp().getText(), rota.getStart());
                    }

                    Entidade entidade = entidades.get(id);
                    if (!entidade.contains(rotaId)) {
                        erroIdentNaoDeclarado(rotaId, rota.getStart());
                    } else {
                        Tipo rotaIdTipo = entidade.getCampo(rotaId).tipo();
                        if (rotaIdTipo == Tipo.OBJETO) {
                            erroTipoInvalido(rota.getStart());
                        }
                    }
                } else if (metodoHttp == Rota.MetodoHttp.PUT || metodoHttp == Rota.MetodoHttp.DELETE) {
                    erroIdRequerido(rota.metodoHttp().getText(), rota.getStart());
                }

                endpoint.add(metodoHttp, url);
            }
            endpoints.put(id, endpoint);
            return super.visitEndpoint(ctx);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro na validação de chaves do path variable");
            erroValidacaoChaves(rotaPontoGetStart, e.getMessage());
            return null;
        }
    }

    public void erroIdentJaDeclarado(String id, Token token) {
        ValidateErrorHelper.addErroSemantico(token, String.format("identificador %s já declarado anteriormente", id));
    }

    public void erroNomeNaoPermitido(String id, Token token) {
        ValidateErrorHelper.addErroSemantico(token, String.format("nome de identificador %s não permitido", id));
    }

    public void erroIdentNaoPermitidoNoEscopo(String id, Token token) {
        ValidateErrorHelper.addErroSemantico(token, String.format("%s - aninhamento de classes do modelo não permitido", id));
    }

    public void erroIdentNaoDeclarado(String id, Token token) {
        ValidateErrorHelper.addErroSemantico(token, String.format("identificador %s não declarado anteriormente", id));
    }

    public void erroRotaJaDeclarada(String metodoHttp, String url, Token token) {
        ValidateErrorHelper.addErroSemantico(token, String.format("rota %s %s declarado anteriormente", metodoHttp, url));

    }

    public void erroTipoInvalido(Token token) {
        ValidateErrorHelper.addErroSemantico(token, "tipos declarados no modelo não são permitidos como identificador de rotas");
    }

    public void erroIdRequerido(String metodoHttp, Token token) {
        ValidateErrorHelper.addErroSemantico(token, String.format("método %s deveria conter um identificador", metodoHttp));
    }

    public void erroNaoDeveConterId(String metodoHttp, Token token) {
        ValidateErrorHelper.addErroSemantico(token, String.format("método %s não deveria conter um identificador", metodoHttp));
    }

    public void erroEntidadeSemId(String entidade, Token token) {
        ValidateErrorHelper.addErroSemantico(token, String.format("Entidade %s não possui id.", entidade));
    }

    public void erroValidacaoChaves(Token token, String message) {
        ValidateErrorHelper.addErroChaves(token, message);
    }
}