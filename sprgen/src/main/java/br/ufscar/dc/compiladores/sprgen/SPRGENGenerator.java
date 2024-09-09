package br.ufscar.dc.compiladores.sprgen;


import java.util.HashMap;
import java.util.Map;

public class SPRGENGenerator extends SPRGENBaseVisitor<Void> {
    Map<String, Entidade> entidades;
    Map<String, Endpoint> endpoints;
    public StringBuilder codigoGerado;

    @Override
    public Void visitPrograma(SPRGENParser.ProgramaContext ctx) {
        entidades = new HashMap<>();
        endpoints = new HashMap<>();
        codigoGerado = new StringBuilder();
        codigoGerado.append("package com.example.sprGeneratedApi;\n\n");

        codigoGerado.append("import lombok.AllArgsConstructor;\n");
        codigoGerado.append("import lombok.Data;\n");
        codigoGerado.append("import org.springframework.context.event.EventListener;");
        codigoGerado.append("import org.springframework.boot.context.event.ApplicationReadyEvent;");
        codigoGerado.append("import java.lang.reflect.Field;\n");
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
        codigoGerado.append("    }\n\n");

        codigoGerado.append("    @EventListener(ApplicationReadyEvent.class)\n");
        codigoGerado.append("    public void printApiPath() {\n");
        codigoGerado.append("       System.out.println(\"Servidor iniciado em http://localhost:8080/spr-generated-api/\");\n");
        codigoGerado.append("    }\n\n");

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
        for (Campo campo : entidade.campos) {
            codigoGerado.append("        private ").append(campo.tipo.getValue()).append(" ").append(campo.nome()).append(";\n");
        }
        codigoGerado.append("        private LocalDateTime dataCriacao;\n");
        codigoGerado.append("        private LocalDateTime dataAlteracao;\n");
        codigoGerado.append("    }\n\n");

        codigoGerado.append("    @Repository\n");
        codigoGerado.append("    static class ").append(id).append("Repository {\n");
        codigoGerado.append("        private final Map<Long, ").append(id).append("> entidades = new HashMap<>();\n");
        codigoGerado.append("        private Long idContador = 1L;\n\n");

        codigoGerado.append("        public List<").append(id).append("> findAll() {\n");
        codigoGerado.append("            return new ArrayList<>(entidades.values());\n");
        codigoGerado.append("        }\n\n");

        codigoGerado.append("        public Optional<").append(id).append("> findById(Long id) {\n");
        codigoGerado.append("            return Optional.ofNullable(entidades.get(id));\n");
        codigoGerado.append("        }\n\n");

        codigoGerado.append("        public ").append(id).append(" save(").append(id).append(" entidade) {\n");
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
        String repositoryInstance = buildEntityRepositoryInstance(id);

        codigoGerado.append("    @RestController\n");
        codigoGerado.append("    @RequiredArgsConstructor\n");
        codigoGerado.append("    @RequestMapping(\"/spr-generated-api/").append(id.toLowerCase()).append("\")\n");
        codigoGerado.append("    static class ").append(id).append("Controller {\n");
        codigoGerado.append("        private final ").append(id).append("Repository ").append(repositoryInstance).append(";\n\n");

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
        String repositoryInstance = buildEntityRepositoryInstance(id);
        codigoGerado.append("        @GetMapping\n");
        codigoGerado.append("        public List<").append(id).append("> getAll() {\n");
        codigoGerado.append("            return ").append(repositoryInstance).append(".findAll();\n");
        codigoGerado.append("        }\n\n");
    }

    private void generateRotaGetById(String id) {
        String repositoryInstance = buildEntityRepositoryInstance(id);
        codigoGerado.append("        @GetMapping(\"/{id}\")\n");
        codigoGerado.append("        public ResponseEntity<").append(id).append("> getById(@PathVariable Long id) {\n");
        codigoGerado.append("            return ").append(repositoryInstance).append(".findById(id)\n");
        codigoGerado.append("                    .map(ResponseEntity::ok)\n");
        codigoGerado.append("                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));\n");
        codigoGerado.append("        }\n\n");
    }

    private void generatePostCode(String id) {
        String repositoryInstance = buildEntityRepositoryInstance(id);
        codigoGerado.append("        @PostMapping\n");
        codigoGerado.append("        public ").append(id).append(" create(@RequestBody ").append(id).append(" ").append(id.toLowerCase()).append(") {\n");
        codigoGerado.append("           return ").append(repositoryInstance).append(".save(").append(id.toLowerCase()).append(");\n");
        codigoGerado.append("        }\n\n");
    }

    private void generatePutCode(String id) {
        String repositoryInstance = buildEntityRepositoryInstance(id);
        codigoGerado.append("        @PutMapping(\"/{id}\")\n");
        codigoGerado.append("        public ResponseEntity<").append(id).append("> update(@PathVariable Long id, @RequestBody ").append(id).append(" ").append(id.toLowerCase()).append(") {\n");

        codigoGerado.append("        Optional<").append(id).append("> optional").append(id).append(" = ").append(repositoryInstance).append(".findById(id);\n");

        codigoGerado.append("        if (optional").append(id).append(".isPresent()){\n");

        codigoGerado.append("           ").append(id).append(" ").append(id.toLowerCase()).append("Existente = optional").append(id).append(".get();\n");
        codigoGerado.append("           try {\n");

        codigoGerado.append("               HashMap<String, Object> atributosValores = new HashMap<>();\n");
        codigoGerado.append("               Field[] fields = ").append(id.toLowerCase()).append(".getClass().getDeclaredFields();\n");
        codigoGerado.append("                   for (Field field : fields) {\n");
        codigoGerado.append("                   field.setAccessible(true);\n");
        codigoGerado.append("                   if (field.get(").append(id.toLowerCase()).append(") != null) {\n");
        codigoGerado.append("                       atributosValores.put(field.getName(), field.get(").append(id.toLowerCase()).append("));\n");
        codigoGerado.append("                       }\n");
        codigoGerado.append("                   }\n\n");

        codigoGerado.append("                   ").append(id.toLowerCase()).append("Existente.setDataAlteracao(LocalDateTime.now());\n\n");

        codigoGerado.append("                   for (Map.Entry<String, Object> entry : atributosValores.entrySet()) {\n");
        codigoGerado.append("                       Field field = ").append(id.toLowerCase()).append("Existente.getClass().getDeclaredField(entry.getKey());\n");
        codigoGerado.append("                       field.setAccessible(true);\n");
        codigoGerado.append("                       field.set(").append(id.toLowerCase()).append("Existente, entry.getValue());\n");
        codigoGerado.append("                       }\n");

        codigoGerado.append("                       ").append(repositoryInstance).append(".save(").append(id.toLowerCase()).append("Existente);\n");

        codigoGerado.append("                       return ResponseEntity.ok().build();\n");

        codigoGerado.append("           } catch (Exception ex) {\n");
        codigoGerado.append("               ex.printStackTrace();\n");
        codigoGerado.append("               return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);\n");
        codigoGerado.append("           }\n");
        codigoGerado.append("        }\n\n");
        codigoGerado.append("        return new ResponseEntity<>(HttpStatus.NO_CONTENT);\n");
        codigoGerado.append("        }\n\n");
    }

    private void generateDeleteCode(String id) {
        String repositoryInstance = buildEntityRepositoryInstance(id);
        codigoGerado.append("        @DeleteMapping(\"/{id}\")\n");
        codigoGerado.append("        public ResponseEntity<Void> deleteById(@PathVariable Long id) {\n");
        codigoGerado.append("            if (").append(repositoryInstance).append(".findById(id).isPresent()) {\n");
        codigoGerado.append("                ").append(repositoryInstance).append(".deleteById(id);\n");
        codigoGerado.append("                return new ResponseEntity<>(HttpStatus.NO_CONTENT);\n");
        codigoGerado.append("            } else {\n");
        codigoGerado.append("                return new ResponseEntity<>(HttpStatus.NOT_FOUND);\n");
        codigoGerado.append("            }\n");
        codigoGerado.append("        }\n\n");
    }

    private String buildEntityRepositoryInstance(String id) {
        return id.toLowerCase().concat("Repository");
    }
}
