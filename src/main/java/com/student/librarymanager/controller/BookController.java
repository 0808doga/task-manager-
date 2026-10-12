package com.student.librarymanager.controller;

import com.student.librarymanager.model.Book;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/books")
public class BookController {

    // Temporary in-memory storage (replaced by a JPA repository in Lab 2)
    private final List<Book> books = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    // Optional filter: GET /api/books?available=true
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks(@RequestParam(required = false) Boolean available) {
        if (available == null) {
            return ResponseEntity.ok(books);
        }
        return ResponseEntity.ok(books.stream()
                .filter(book -> available.equals(book.getAvailable()))
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        book.setId(idCounter.getAndIncrement());
        if (book.getAvailable() == null) {
            book.setAvailable(true);
        }
        books.add(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(
            @PathVariable Long id,
            @RequestBody Book updatedBook) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId().equals(id)) {
                updatedBook.setId(id);
                books.set(i, updatedBook);
                return ResponseEntity.ok(updatedBook);
            }
        }
        return ResponseEntity.notFound().build();
    }

    // Partial update: only non-null fields in the body are applied
    @PatchMapping("/{id}")
    public ResponseEntity<Book> patchBook(@PathVariable Long id, @RequestBody Book changes) {
        return books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst()
                .map(book -> {
                    if (changes.getTitle() != null) book.setTitle(changes.getTitle());
                    if (changes.getIsbn() != null) book.setIsbn(changes.getIsbn());
                    if (changes.getPublicationYear() != null) book.setPublicationYear(changes.getPublicationYear());
                    if (changes.getAuthorId() != null) book.setAuthorId(changes.getAuthorId());
                    if (changes.getAvailable() != null) book.setAvailable(changes.getAvailable());
                    return ResponseEntity.ok(book);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        boolean removed = books.removeIf(book -> book.getId().equals(id));
        return removed ?
                ResponseEntity.noContent().build() :
                ResponseEntity.notFound().build();
    }
}
