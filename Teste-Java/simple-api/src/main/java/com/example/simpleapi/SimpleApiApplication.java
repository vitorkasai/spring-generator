package com.example.simpleapi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@SpringBootApplication
public class SimpleApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleApiApplication.class, args);
    }

    // Model with Lombok annotations
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class Entity {
        private Long id;
        private String name;
    }

    // Repository
    @Repository
    static class EntityRepository {
        private final Map<Long, Entity> entities = new HashMap<>();
        private Long idCounter = 1L;

        public List<Entity> findAll() {
            return new ArrayList<>(entities.values());
        }

        public Optional<Entity> findById(Long id) {
            return Optional.ofNullable(entities.get(id));
        }

        public Entity save(Entity entity) {
            if (entity.getId() == null) {
                entity.setId(idCounter++);
            }
            entities.put(entity.getId(), entity);
            return entity;
        }

        public void deleteById(Long id) {
            entities.remove(id);
        }
    }

    // Controller
    @RestController
    @RequestMapping("/api/entities")
    static class EntityController {
        private final EntityRepository entityRepository;

        public EntityController(EntityRepository entityRepository) {
            this.entityRepository = entityRepository;
        }

        @GetMapping
        public List<Entity> getAllEntities() {
            return entityRepository.findAll();
        }

        @GetMapping("/{id}")
        public ResponseEntity<Entity> getEntityById(@PathVariable Long id) {
            return entityRepository.findById(id)
                    .map(ResponseEntity::ok)
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }

        @PostMapping
        public Entity createEntity(@RequestBody Entity entity) {
            return entityRepository.save(entity);
        }

        @PutMapping("/{id}")
        public ResponseEntity<Entity> updateEntity(@PathVariable Long id, @RequestBody Entity entity) {
            return entityRepository.findById(id)
                    .map(existingEntity -> {
                        existingEntity.setName(entity.getName());
                        entityRepository.save(existingEntity);
                        return new ResponseEntity<>(existingEntity, HttpStatus.OK);
                    })
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteEntity(@PathVariable Long id) {
            if (entityRepository.findById(id).isPresent()) {
                entityRepository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
    }
}
