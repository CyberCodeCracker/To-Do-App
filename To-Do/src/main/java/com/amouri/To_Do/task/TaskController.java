package com.amouri.To_Do.task;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("tasks")
@RequiredArgsConstructor
@Tag(name = "Task")
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<Integer> addTask(
            @Valid @RequestBody TaskRequest request,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(taskService.addTask(request, connectedUser));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteTask(@PathVariable Integer taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/edit-task/{task-id}")
    public ResponseEntity<Integer> editTaskDetails(
            @PathVariable("task-id") Integer taskId,
            @RequestParam(required = false) String newTitle,
            @RequestParam(required = false) String newDescription,
            @RequestParam(required = false) LocalDateTime newDueAt,
            Authentication connectedUser
    ) {
        taskService.editTaskDetails(taskId, newTitle, newDescription, newDueAt, connectedUser);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/edit-task/completion/{task-id}")
    public ResponseEntity<Integer> toggleTaskCompletion(
            @PathVariable("task-id") Integer taskId,
            Authentication connectedUser
    ) {
        taskService.toggleTaskCompletion(taskId, connectedUser);
        return ResponseEntity.ok().build();
    }
}
