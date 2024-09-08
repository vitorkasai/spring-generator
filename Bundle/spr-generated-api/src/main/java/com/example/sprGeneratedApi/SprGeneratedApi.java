package com.example.sprGeneratedApi;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.event.EventListener;import org.springframework.boot.context.event.ApplicationReadyEvent;import java.lang.reflect.Field;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@SpringBootApplication
public class SprGeneratedApi {

    public static void main(String[] args) {
        SpringApplication.run(SprGeneratedApi.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void printApiPath() {
       System.out.println("Servidor iniciado em http://localhost:8080/spr-generated-api/");
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class Pessoa {
        private Long id;
        private String nome;
        private String email;
        private Long idade;
        private LocalDateTime dataCriacao;
        private LocalDateTime dataAlteracao;
    }

    @Repository
    static class PessoaRepository {
        private final Map<Long, Pessoa> entidades = new HashMap<>();
        private Long idContador = 1L;

        public List<Pessoa> findAll() {
            return new ArrayList<>(entidades.values());
        }

        public Optional<Pessoa> findById(Long id) {
            return Optional.ofNullable(entidades.get(id));
        }

        public Pessoa save(Pessoa entidade) {
            if (entidade.getId() == null) {
                entidade.setId(idContador++);
            }
            entidade.setDataCriacao(LocalDateTime.now());
            entidades.put(entidade.getId(), entidade);
            return entidade;
        }

        public void deleteById(Long id) {
            entidades.remove(id);
        }

    }

    // Endpoints da API gerados
    @RestController
    @RequiredArgsConstructor
    @RequestMapping("/spr-generated-api/pessoa")
    static class PessoaController {
        private final PessoaRepository pessoaRepository;

        @GetMapping
        public List<Pessoa> getAll() {
            return pessoaRepository.findAll();
        }

        @GetMapping("/{id}")
        public ResponseEntity<Pessoa> getById(@PathVariable Long id) {
            return pessoaRepository.findById(id)
                    .map(ResponseEntity::ok)
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }

        @PostMapping
        public Pessoa create(@RequestBody Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
        }

        @PutMapping("/{id}")
        public ResponseEntity<Pessoa> update(@PathVariable Long id, @RequestBody Pessoa pessoa) {
        Optional<Pessoa> optionalPessoa = pessoaRepository.findById(id);
        if (optionalPessoa.isPresent()){
           Pessoa pessoaExistente = optionalPessoa.get();
           try {
               HashMap<String, Object> atributosValores = new HashMap<>();
               Field[] fields = pessoa.getClass().getDeclaredFields();
                   for (Field field : fields) {
                   field.setAccessible(true);
                   if (field.get(pessoa) != null) {
                       atributosValores.put(field.getName(), field.get(pessoa));
                       }
                   }

                   pessoaExistente.setDataAlteracao(LocalDateTime.now());

                   for (Map.Entry<String, Object> entry : atributosValores.entrySet()) {
                       Field field = pessoaExistente.getClass().getDeclaredField(entry.getKey());
                       field.setAccessible(true);
                       field.set(pessoaExistente, entry.getValue());
                       }
                       pessoaRepository.save(pessoaExistente);
                       return ResponseEntity.ok().build();
           } catch (Exception ex) {
               ex.printStackTrace();
               return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
           }
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteById(@PathVariable Long id) {
            if (pessoaRepository.findById(id).isPresent()) {
                pessoaRepository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }

    }

}
