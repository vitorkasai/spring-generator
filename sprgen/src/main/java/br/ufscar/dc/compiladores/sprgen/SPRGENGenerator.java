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
        codigoGerado.append("        return ").append(repositoryInstance).append(".save(").append(id.toLowerCase()).append(");\n");
        codigoGerado.append("        }\n\n");
    }

    /*
     return pessoaRepository.findById(id)
                .map(e -> {

                    HashMap<String, Object> atributosValores = new HashMap<>();
                    Field[] fields = pessoa.getClass().getDeclaredFields();
                    for (Field field : fields) {
                        field.setAccessible(true);
                        if (field.get(pessoa) != null) {
                            atributosValores.put(field.getName(), field.get(pessoa))
                        }
                    }                    

                    e.setDataAlteracao(LocalDateTime.now());

                    // percorre chave/valor do map
                    for (Map.Entry<String, Object> entry : atributosValores.entrySet()) {
                        Field field = e.getClass().getDeclaredField(entry.getKey());
                        field.setAccesible(true)
                        field.set(e, entry.getValue())
                    }
                    
                    pessoaRepository.save(e);
                    return new ResponseEntity<>(e, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
     */

     /*
      * 

      @PutMapping("/{id}")
public ResponseEntity<Pessoa> update(@PathVariable Long id, @RequestBody Pessoa pessoa) {
    // Buscar a pessoa pelo ID no repositório
    Optional<Pessoa> optionalPessoa = pessoaRepository.findById(id);

    // Verifica se a pessoa está presente no Optional
    if (optionalPessoa.isPresent()) {
        // Pessoa encontrada, recupera o objeto
        Pessoa pessoaExistente = optionalPessoa.get();

        try {
            // Mapeia os atributos e valores da nova pessoa
            HashMap<String, Object> atributosValores = new HashMap<>();
            Field[] fields = pessoa.getClass().getDeclaredFields();

            // Percorre os atributos da nova pessoa
            for (Field field : fields) {
                field.setAccessible(true);  // Permite acessar os campos privados
                if (field.get(pessoa) != null) {  // Se o campo não for nulo
                    atributosValores.put(field.getName(), field.get(pessoa));  // Adiciona ao mapa
                }
            }

            // Atualiza a data de alteração
            pessoaExistente.setDataAlteracao(LocalDateTime.now());

            // Atualiza os valores dos atributos da entidade existente
            for (Map.Entry<String, Object> entry : atributosValores.entrySet()) {
                Field field = pessoaExistente.getClass().getDeclaredField(entry.getKey());
                field.setAccessible(true);  // Permite acessar os campos privados
                field.set(pessoaExistente, entry.getValue());  // Define o novo valor no objeto existente
            }

            // Salva a entidade atualizada no repositório
            pessoaRepository.save(pessoaExistente);

            // Retorna a resposta com a entidade atualizada
            return new ResponseEntity<>(pessoaExistente, HttpStatus.OK);

        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);  // Retorna erro em caso de exceção
        }
    } else {
        // Se a pessoa não foi encontrada, retorna 404 Not Found
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

      */

    private void generatePutCode(String id) {
        String repositoryInstance = buildEntityRepositoryInstance(id);
        codigoGerado.append("        @PutMapping(\"/{id}\")\n");
        codigoGerado.append("        public ResponseEntity<").append(id).append("> update(@PathVariable Long id, @RequestBody ").append(id).append(" ").append(id.toLowerCase()).append(") {\n");
        
        //Optional<Pessoa> optionalPessoa = pessoaRepository.findById(id);
        codigoGerado.append("        Optional<" + id + "> optional" + id + " = " + repositoryInstance + ".findById(id);\n");

        //if (optionalPessoa.isPresent()) {
        codigoGerado.append("        if (optional" + id + ".isPresent()){\n");

        //  Pessoa pessoaExistente = optionalPessoa.get();
        codigoGerado.append("           " + id + " " + id.toLowerCase() + "Existente = optional" + id +".get();\n");
        // try {
        codigoGerado.append("           try {\n");

        // HashMap<String, Object> atributosValores = new HashMap<>();
        codigoGerado.append("               HashMap<String, Object> atributosValores = new HashMap<>();\n");
        // Field[] fields = pessoa.getClass().getDeclaredFields();
        codigoGerado.append("               Field[] fields = ").append(id.toLowerCase()).append(".getClass().getDeclaredFields();\n");
        // for (Field field : fields) {
        // field.setAccessible(true);
        codigoGerado.append("                   for (Field field : fields) {\n");
        codigoGerado.append("                   field.setAccessible(true);\n");
        //if (field.get(pessoa) != null) {
        codigoGerado.append("                   if (field.get("+id.toLowerCase() +") != null) {\n");
        //atributosValores.put(field.getName(), field.get(pessoa))
        codigoGerado.append("                       atributosValores.put(field.getName(), field.get("+id.toLowerCase()+"));\n");
        codigoGerado.append("                       }\n");
        codigoGerado.append("                   }\n\n");
        
        codigoGerado.append("                   "+id.toLowerCase()+"Existente.setDataAlteracao(LocalDateTime.now());\n\n");

        codigoGerado.append("                   for (Map.Entry<String, Object> entry : atributosValores.entrySet()) {\n");
        codigoGerado.append("                       Field field = "+id.toLowerCase()+"Existente.getClass().getDeclaredField(entry.getKey());\n");
        codigoGerado.append("                       field.setAccessible(true);\n");
        codigoGerado.append("                       field.set("+id.toLowerCase()+"Existente, entry.getValue());\n");
        codigoGerado.append("                       }\n"); 
        
        codigoGerado.append("                       "+repositoryInstance+".save("+id.toLowerCase()+"Existente);\n");    
        
        codigoGerado.append("                       return new ResponseEntity<>(").append(id.toLowerCase()).append(", HttpStatus.OK);\n");

        // } catch (Exception ex) {
        codigoGerado.append("           } catch (Exception ex) {\n");
        codigoGerado.append("               ex.printStackTrace();\n");
        codigoGerado.append("               return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);\n");
        codigoGerado.append("           }\n");
        codigoGerado.append("        }\n\n");
        codigoGerado.append("        return new ResponseEntity<>(HttpStatus.NO_CONTENT);\n");
        codigoGerado.append("        }\n\n");
        /*
         *ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);  // Retorna erro em caso de exceção
        }
         */
        
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
