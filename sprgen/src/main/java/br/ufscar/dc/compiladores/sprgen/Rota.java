package br.ufscar.dc.compiladores.sprgen;

public class Rota {
    MetodoHttp metodoHttp;
    String url;
    String id;

    public enum MetodoHttp {
        GET,
        POST,
        PUT,
        DELETE,
        Invalido;
    }

    Rota(MetodoHttp metodoHttp, String url) {
        this.metodoHttp = metodoHttp;
        this.url = url;
    }

    Rota(MetodoHttp metodoHttp, String url, String id) {
        this.metodoHttp = metodoHttp;
        this.url = url;
        this.id = id;
    }
}
