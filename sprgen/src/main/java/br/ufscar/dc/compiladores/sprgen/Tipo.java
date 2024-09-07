package br.ufscar.dc.compiladores.sprgen;

public enum Tipo {
    STRING("String"),
    LONG("Long"),
    BOOLEAN("boolean"),
    OBJETO("Object");

    private final String value;

    Tipo(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
