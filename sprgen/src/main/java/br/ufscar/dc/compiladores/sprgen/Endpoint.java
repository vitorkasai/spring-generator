package br.ufscar.dc.compiladores.sprgen;

import java.util.ArrayList;
import java.util.List;

public class Endpoint {
    List<Rota> rotas;

    Endpoint() {
        this.rotas = new ArrayList<>();
    }

    List<Rota> rotas() {
        return this.rotas;
    }

    void add(Rota.MetodoHttp metodoHttp, String url) {
        Rota rota = new Rota(metodoHttp, url);
        this.rotas.add(rota);
    }

    void add(Rota.MetodoHttp metodoHttp, String url, String id) {
        Rota rota = new Rota(metodoHttp, url, id);
        this.rotas.add(rota);
    }

    boolean contains(Rota.MetodoHttp metodoHttp, String url) {
        for (Rota rota : this.rotas) {
            if (rota.url.equals(url) && rota.metodoHttp == metodoHttp) {
                return true;
            }
        }
        return false;
    }
}
