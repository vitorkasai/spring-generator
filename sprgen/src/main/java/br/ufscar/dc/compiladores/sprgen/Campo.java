package br.ufscar.dc.compiladores.sprgen;

public class Campo {
    Tipo tipo;
    String nome;

    Campo(Tipo tipo, String nome) {
        this.tipo = tipo;
        this.nome = nome;
    }

    Tipo tipo() {
        return this.tipo;
    }

    String nome() {
        return this.nome;
    }
}
