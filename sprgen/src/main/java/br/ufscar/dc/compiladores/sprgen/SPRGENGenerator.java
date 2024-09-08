package br.ufscar.dc.compiladores.sprgen;


import java.util.HashMap;
import java.util.Map;

public class SPRGENGenerator extends SPRGENBaseVisitor<Void> {
    Map<String, Entidade> entidades;
    Map<String, Endpoint> endpoints;
    public StringBuilder codigoGerado;

    @Override
    public Void visitPrograma(SPRGENParser.ProgramaContext ctx) {
        System.out.println("ENTROU");
        entidades = new HashMap<>();
        endpoints = new HashMap<>();
        codigoGerado = new StringBuilder();
        codigoGerado.append("package com.example.sprGeneratedApi;\n\n");

        codigoGerado.append("import lombok.AllArgsConstructor;\n");
        codigoGerado.append("import lombok.Data;\n");
        codigoGerado.append("import lombok.NoArgsConstructor;\n");
        codigoGerado.append("import lombok.RequiredArgsConstructor;\n");
        codigoGerado.append("import org.springframework.boot.SpringApplication;\n");
        codigoGerado.append("import org.springframework.boot.autoconfigure.SpringBootApplication;\n");
        codigoGerado.append("import org.springframework.http.HttpStatus;\n");
        codigoGerado.append("import org.springframework.http.ResponseEntity;\n");
        codigoGerado.append("import org.springframework.stereotype.Repository;\n");
        codigoGerado.append("import org.springframework.web.bind.annotation.*;\n\n");

        codigoGerado.append("import java.time.LocalDateTime;\n");
        codigoGerado.append("import java.util.*;\n\n");

        codigoGerado.append("@SpringBootApplication\n");
        codigoGerado.append("public class SprGeneratedApi {\n\n");


        codigoGerado.append("    public static void main(String[] args) {\n");
        codigoGerado.append("        SpringApplication.run(SprGeneratedApi.class, args);\n");
        codigoGerado.append("    }\n");

        super.visitPrograma(ctx);

        codigoGerado.append("}");
        return null;
    }

    @Override
    public Void visitEntidade(SPRGENParser.EntidadeContext ctx) {
        String id = ctx.IDENT().getText();
        Entidade entidade = new Entidade();

        for (SPRGENParser.CampoContext campo : ctx.campos) {
            String nomeCampo = campo.ident.getText();
            Tipo tipo = SPRGENUtils.mapStringToTipo(campo.tipo.getText());
            entidade.add(tipo, nomeCampo);
        }
        entidades.put(id, entidade);

        codigoGerado.append("    @Data\n");
        codigoGerado.append("    @NoArgsConstructor\n");
        codigoGerado.append("    @AllArgsConstructor\n");
        codigoGerado.append("    static class ").append(id).append(" {\n");
        codigoGerado.append("        private Long id;\n");
        for (Campo campo : entidade.campos) {
            codigoGerado.append("        private ").append(campo.tipo.getValue()).append(" ").append(campo.nome()).append(";\n");
        }
        codigoGerado.append("        private LocalDateTime dataCriacao;\n");
        codigoGerado.append("        private LocalDateTime dataAlteracao;\n");
        codigoGerado.append("    }\n\n");

        codigoGerado.append("    @Repository\n");
        codigoGerado.append("    static class " + id + "Repository {\n");
        codigoGerado.append("        private final Map<Long, " + id + "> entidades = new HashMap<>();\n");
        codigoGerado.append("        private Long idContador = 1L;\n\n");

        codigoGerado.append("        public List<" + id + "> findAll() {\n");
        codigoGerado.append("            return new ArrayList<>(entidades.values());\n");
        codigoGerado.append("        }\n\n");

        codigoGerado.append("        public Optional<" + id + "> findById(Long id) {\n");
        codigoGerado.append("            return Optional.ofNullable(entidades.get(id));\n");
        codigoGerado.append("        }\n\n");

        codigoGerado.append("        public " + id + " save(" + id + " entidade) {\n");
        codigoGerado.append("            if (entidade.getId() == null) {\n");
        codigoGerado.append("                entidade.setId(idContador++);\n");
        codigoGerado.append("            }\n");
        codigoGerado.append("            entidade.setDataCriacao(LocalDateTime.now());\n");
        codigoGerado.append("            entidades.put(entidade.getId(), entidade);\n");
        codigoGerado.append("            return entidade;\n");
        codigoGerado.append("        }\n\n");

        codigoGerado.append("        public void deleteById(Long id) {\n");
        codigoGerado.append("            entidades.remove(id);\n");
        codigoGerado.append("        }\n\n");

        codigoGerado.append("    }\n\n");

        return super.visitEntidade(ctx);
    }

    @Override
    public Void visitEndpoints(SPRGENParser.EndpointsContext ctx) {
        codigoGerado.append("    // Endpoints da API gerados\n");
        super.visitEndpoints(ctx);
        return null;
    }

    @Override
    public Void visitEndpoint(SPRGENParser.EndpointContext ctx) {
        String id = ctx.IDENT().getText();
        String repositoryInstance = id.toLowerCase() + "Repository";

        codigoGerado.append("    @RestController\n");
        codigoGerado.append("    @RequiredArgsConstructor\n");
        codigoGerado.append("    @RequestMapping(\"/spr-generated-api/" + id.toLowerCase() + "\")\n");
        codigoGerado.append("    static class " + id + "Controller {\n");

        codigoGerado.append("        private final " + id + "Repository " + repositoryInstance +  ";\n\n");

        Endpoint endpoint = new Endpoint();
        for (SPRGENParser.RotaContext rota : ctx.rotas) {
            String url = rota.STRING().getText();
            Rota.MetodoHttp metodoHttp = SPRGENUtils.mapStringToMetodoHttp(rota.metodoHttp().getText());
            endpoint.add(metodoHttp, url);
        }
        endpoints.put(id, endpoint);
        for (Rota rota : endpoint.rotas) {
            generateRotaCode(id, rota);
        }

        codigoGerado.append("    }\n\n");
        return super.visitEndpoint(ctx);
    }

    private void generateRotaCode(String id, Rota rota) {
        switch (rota.metodoHttp) {
            case GET:
                generateGetCode(id, rota.url);
                break;
            case POST:
                generatePostCode(id);
                break;
            case PUT:
                generatePutCode(id);
                break;
            case DELETE:
                generateDeleteCode(id);
                break;
        }
    }

    private void generateGetCode(String id, String url) {
        String routeId = SPRGENUtils.extractRotaId(url);
        if (routeId == null) {
            generateRotaGetAll(id);
        } else {
            generateRotaGetById(id);
        }
    }

    private void generateRotaGetAll(String id) {
        String repositoryInstance = id.toLowerCase() + "Repository";
        codigoGerado.append("        @GetMapping\n");
        codigoGerado.append("        public List<" + id + "> getAll() {\n");
        codigoGerado.append("            return " + repositoryInstance + ".findAll();\n");
        codigoGerado.append("        }\n\n");
    }

    private void generateRotaGetById(String id) {
        String repositoryInstance = id.toLowerCase() + "Repository";
        codigoGerado.append("        @GetMapping(\"/{id}\")\n");
        codigoGerado.append("        public ResponseEntity<" + id + "> getById(@PathVariable Long id) {\n");
        codigoGerado.append("            return " + repositoryInstance + ".findById(id)\n");
        codigoGerado.append("                    .map(ResponseEntity::ok)\n");
        codigoGerado.append("                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));\n");
        codigoGerado.append("        }\n\n");
    }

    private void generatePostCode(String id) {
        String repositoryInstance = id.toLowerCase() + "Repository";
        codigoGerado.append("        @PostMapping\n");
        codigoGerado.append("        public " + id + " create(@RequestBody " + id + " " + id.toLowerCase() + ") {\n");
        codigoGerado.append("        return " + repositoryInstance + ".save(" + id.toLowerCase() + ");\n");
        codigoGerado.append("        }\n\n");
    }

    private void generatePutCode(String id) {
        String repositoryInstance = id.toLowerCase() + "Repository";
        codigoGerado.append("        @PutMapping(\"/{id}\")\n");
        codigoGerado.append("        public ResponseEntity<" + id + "> update(@PathVariable Long id, @RequestBody " + id + " " + id.toLowerCase() + ") {\n");
        codigoGerado.append("            return " + repositoryInstance + ".findById(id)\n");
        codigoGerado.append("                    .map(e -> {\n");
        codigoGerado.append("                        " + id.toLowerCase() + ".setId(e.getId());\n");
        codigoGerado.append("                        " + id.toLowerCase() + ".setDataAlteracao(LocalDateTime.now());\n");
        codigoGerado.append("                        " + repositoryInstance + ".save(" + id.toLowerCase() + ");\n");
        codigoGerado.append("                        return new ResponseEntity<>(" + id.toLowerCase() + ", HttpStatus.OK);\n");
        codigoGerado.append("                    })\n");
        codigoGerado.append("                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));\n");
        codigoGerado.append("        }\n\n");
    }

    private void generateDeleteCode(String id) {
        String repositoryInstance = id.toLowerCase() + "Repository";
        codigoGerado.append("        @DeleteMapping(\"/{id}\")\n");
        codigoGerado.append("        public ResponseEntity<Void> deleteById(@PathVariable Long id) {\n");
        codigoGerado.append("            if (" + repositoryInstance + ".findById(id).isPresent()) {\n");
        codigoGerado.append("                " + repositoryInstance + ".deleteById(id);\n");
        codigoGerado.append("                return new ResponseEntity<>(HttpStatus.NO_CONTENT);\n");
        codigoGerado.append("            } else {\n");
        codigoGerado.append("                return new ResponseEntity<>(HttpStatus.NOT_FOUND);\n");
        codigoGerado.append("            }\n");
        codigoGerado.append("        }\n\n");
    }
}
