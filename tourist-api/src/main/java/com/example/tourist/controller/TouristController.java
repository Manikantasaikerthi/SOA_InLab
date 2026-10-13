
package com.example.tourist.controller;

import com.example.tourist.entity.Tourist;
import com.example.tourist.repository.TouristRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tourists")
public class TouristController {

    private final TouristRepository repository;

    public TouristController(TouristRepository repository) {
        this.repository = repository;
    }

    // Create a tourist for testing
    @PostMapping
    public ResponseEntity<Tourist> addTourist(
            @RequestBody Tourist tourist) {

        Tourist saved = repository.save(tourist);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(saved);
    }

    // Delete tourist with token validation
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTourist(
            @PathVariable Long id,
            @RequestHeader(value = "Token", required = false)
            String token) {

        if (token == null || !token.equals("VALID_TOKEN")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Deletion Failed: Token Error");
        }

        if (!repository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Tourist record not found");
        }

        repository.deleteById(id);

        return ResponseEntity.ok(
                "Tourist Record Deleted Successfully");
    }
}
