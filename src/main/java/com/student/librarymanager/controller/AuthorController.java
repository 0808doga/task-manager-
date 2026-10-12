package com.student.librarymanager.controller;

import com.student.librarymanager.model.Author;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    // Temporary in-memory storage (replaced by a JPA repository in Lab 2)
    private final List<Author> authors = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    @GetMapping
    public ResponseEntity<List<Author>> getAllAuthors() {
        return ResponseEntity.ok(authors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable Long id) {
        return authors.stream()
                .filter(author -> author.getId().equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
        author.setId(idCounter.getAndIncrement());
        authors.add(author);
        return ResponseEntity.status(HttpStatus.CREATED).body(author);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(
            @PathVariable Long id,
            @RequestBody Author updatedAuthor) {
        for (int i = 0; i < authors.size(); i++) {
            if (authors.get(i).getId().equals(id)) {
                updatedAuthor.setId(id);
                authors.set(i, updatedAuthor);
                return ResponseEntity.ok(updatedAuthor);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        boolean removed = authors.removeIf(author -> author.getId().equals(id));
        return removed ?
                ResponseEntity.noContent().build() :
                ResponseEntity.notFound().build();
    }
}
