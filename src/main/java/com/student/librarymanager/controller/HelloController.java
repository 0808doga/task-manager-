package com.student.librarymanager.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hello")
public class HelloController {

    @GetMapping
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello from Spring Boot!");
    }

    @GetMapping("/{name}")
    public ResponseEntity<String> sayHelloToName(@PathVariable String name) {
        return ResponseEntity.ok("Hello, " + name + "!");
    }

    @PostMapping
    public ResponseEntity<String> receiveMessage(@RequestBody String message) {
        return ResponseEntity.ok("You sent: " + message);
    }
}
