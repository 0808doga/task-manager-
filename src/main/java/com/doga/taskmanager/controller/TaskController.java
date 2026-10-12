package com.doga.taskmanager.controller;

import com.doga.taskmanager.model.Task;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    // Temporary in-memory storage (replaced by a JPA repository in Lab 2)
    private final List<Task> tasks = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    // Optional filter: GET /api/tasks?completed=true
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks(@RequestParam(required = false) Boolean completed) {
        if (completed == null) {
            return ResponseEntity.ok(tasks);
        }
        return ResponseEntity.ok(tasks.stream()
                .filter(task -> task.isCompleted() == completed)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        return tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        task.setId(idCounter.getAndIncrement());
        tasks.add(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(
            @PathVariable Long id,
            @RequestBody Task updatedTask) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId().equals(id)) {
                updatedTask.setId(id);
                tasks.set(i, updatedTask);
                return ResponseEntity.ok(updatedTask);
            }
        }
        return ResponseEntity.notFound().build();
    }

    // Partial update: only the fields present in the body are changed
    @PatchMapping("/{id}")
    public ResponseEntity<Task> patchTask(@PathVariable Long id, @RequestBody Map<String, Object> changes) {
        return tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst()
                .map(task -> {
                    if (changes.containsKey("title")) task.setTitle((String) changes.get("title"));
                    if (changes.containsKey("description")) task.setDescription((String) changes.get("description"));
                    if (changes.containsKey("completed")) task.setCompleted((Boolean) changes.get("completed"));
                    return ResponseEntity.ok(task);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        boolean removed = tasks.removeIf(task -> task.getId().equals(id));
        return removed ?
                ResponseEntity.noContent().build() :
                ResponseEntity.notFound().build();
    }
}
