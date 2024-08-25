package com.amouri.To_Do.task;

import com.amouri.To_Do.common.PageResponse;
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

    @PatchMapping("edit-task/priority/{task-id}")
    public ResponseEntity<Integer> setTaskPriority(
            @PathVariable("task-id") Integer taskId,
            @RequestParam(required = false) String priority,
            Authentication connectedUser
    ) {
        taskService.setTaskPriority(taskId, priority, connectedUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/sorted-by-due-date")
    public ResponseEntity<PageResponse<TaskResponse>> getTasksSortedByDueDate(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(taskService.getTasksSortedByDueDate(page, size, connectedUser));
    }

    @GetMapping("/sorted-by-title")
    public ResponseEntity<PageResponse<TaskResponse>> findTasksByTitle(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "5", required = false) int size,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(taskService.findTasksByTitle(page, size, connectedUser));
    }


}
