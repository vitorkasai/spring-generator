package br.ufscar.dc.compiladores.sprgen;

import java.util.ArrayList;
import java.util.List;

public class Entidade {
    List<Campo> campos;

    Entidade() {
        this.campos = new ArrayList<>();
    }

    List<Campo> campos() {
        return this.campos;
    }

    void add(Tipo tipo, String nome) {
        Campo newCampo = new Campo(tipo, nome);
        campos.add(newCampo);
    }

    boolean contains(String nome) {
        for (Campo campo : this.campos) {
            if (campo.nome.equals(nome)) {
                return true;
            }
        }
        return false;
    }

    Campo getCampo(String nome) {
        for (Campo campo : this.campos) {
            if (campo.nome.equals(nome)) {
                return campo;
            }
        }
        return null;
    }
}
