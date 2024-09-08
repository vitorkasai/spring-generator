package com.example.sprGeneratedApi;

import lombok.AllArgsConstructor;
import lombok.Data;
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
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class Pessoa {
        private Long id;
        private String nome;
        private String cpf;
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

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class Carro {
        private Long id;
        private String modelo;
        private String placa;
        private Long ano;
        private String cpfDono;
        private LocalDateTime dataCriacao;
        private LocalDateTime dataAlteracao;
    }

    @Repository
    static class CarroRepository {
        private final Map<Long, Carro> entidades = new HashMap<>();
        private Long idContador = 1L;

        public List<Carro> findAll() {
            return new ArrayList<>(entidades.values());
        }

        public Optional<Carro> findById(Long id) {
            return Optional.ofNullable(entidades.get(id));
        }

        public Carro save(Carro entidade) {
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
            return pessoaRepository.findById(id)
                    .map(e -> {
                        pessoa.setId(e.getId());
                        pessoa.setDataAlteracao(LocalDateTime.now());
                        pessoaRepository.save(pessoa);
                        return new ResponseEntity<>(pessoa, HttpStatus.OK);
                    })
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
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

    @RestController
    @RequiredArgsConstructor
    @RequestMapping("/spr-generated-api/carro")
    static class CarroController {
        private final CarroRepository carroRepository;

        @GetMapping("/{id}")
        public ResponseEntity<Carro> getById(@PathVariable Long id) {
            return carroRepository.findById(id)
                    .map(ResponseEntity::ok)
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }

    }

}
