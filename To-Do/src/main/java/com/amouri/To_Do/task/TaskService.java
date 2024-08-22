package com.amouri.To_Do.task;
import com.amouri.To_Do.common.PageResponse;
import org.springframework.security.access.AccessDeniedException;

import com.amouri.To_Do.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    final private TaskRepository taskRepository;
    final private TaskMapper taskMapper;

    public Integer addTask(TaskRequest request, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Task task = taskMapper.toTask(request);
        task.setUser(user);
        return taskRepository.save(task).getId();
    }

    public void deleteTask(Integer taskId) {
        taskRepository.deleteById(taskId);
    }

    public void editTaskDetails(Integer taskId, String newTitle, String newDescription, LocalDateTime newDueAt, Authentication connectedUser) throws AccessDeniedException {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));
        if (!Objects.equals(task.getUser().getId(), connectedUser.getName())) {
            throw new AccessDeniedException("You don't have permission to edit task.");
        }
        if (newTitle != null && newTitle.isEmpty()) {
            updateTaskTitle(taskId, newTitle, connectedUser);
        }
        if (newDescription != null && newDescription.isEmpty()) {
            updateTaskDescription(taskId, newDescription, connectedUser);
        }
        if (newDueAt != null) {
            updateTaskDueAt(taskId, newDueAt, connectedUser);
        }
        taskRepository.save(task);
    }

    private void updateTaskDueAt(Integer taskId, LocalDateTime newDueAt,  Authentication connectedUser) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));
        if (!Objects.equals(task.getUser().getId(), connectedUser.getName())) {
            throw new AccessDeniedException("You don't have permission to update task's due at date.");
        }
        task.setDueAt(newDueAt);
    }

    private void updateTaskDescription(Integer taskId, String newDescription, Authentication connectedUser) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));
        if (!Objects.equals(task.getUser().getId(), connectedUser.getName())) {
            throw new AccessDeniedException("You don't have permission to update task description.");
        }
        task.setDescription(newDescription);
    }

    private void updateTaskTitle(Integer taskId, String newTitle, Authentication connectedUser) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));
        if (!Objects.equals(task.getUser().getId(), connectedUser.getName())) {
            throw new AccessDeniedException("You don't have permission to update task title.");
        }
        task.setTitle(newTitle);
    }

    public void toggleTaskCompletion(Integer taskId, Authentication connectedUser) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));
        if (!Objects.equals(task.getUser().getId(), connectedUser.getName())) {
            throw new AccessDeniedException("You don't have permission to change task completion status.");
        }
        task.setCompleted(!task.isCompleted());
        taskRepository.save(task);
    }

    public void setTaskPriority(Integer taskId, String priority, Authentication connectedUser) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));
        if (!Objects.equals(task.getUser().getId(), connectedUser.getName())) {
            throw new AccessDeniedException("You don't have permission to set task priority.");
        }
        task.setPriority(priority);
        taskRepository.save(task);
    }

    public PageResponse<TaskResponse> getTasksSortedByDueDate(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page, size, Sort.by("dueAt").descending());
        Page<Task> taskPage = taskRepository.findByUser(user, pageable);
        List<TaskResponse> taskResponses = taskPage.stream()
                .map(task -> new TaskResponse(
                        task.getId(),
                        task.getTitle(),
                        task.getDescription(),
                        task.getPriority(),
                        task.getCreatedAt(),
                        task.getDueAt(),
                        task.isCompleted()
                ))
                .collect(Collectors.toList());
        return PageResponse.<TaskResponse>builder()
                .content(taskResponses)
                .number(taskPage.getNumber())
                .size(taskPage.getSize())
                .totalElements(taskPage.getTotalElements())
                .firstPosition(taskPage.isFirst())
                .lastPosition(taskPage.isLast())
                .build();
    }

    public PageResponse<TaskResponse> findTasksByTitle(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page, size, Sort.by("title").ascending());
        Page<Task> taskPage = taskRepository.findTasksByTitle(user, pageable);
        List<TaskResponse> taskResponses = taskPage.stream()
                .map(task -> new TaskResponse(
                        task.getId(),
                        task.getTitle(),
                        task.getDescription(),
                        task.getPriority(),
                        task.getCreatedAt(),
                        task.getDueAt(),
                        task.isCompleted()
                ))
                .collect(Collectors.toList());
        return PageResponse.<TaskResponse>builder()
                .content(taskResponses)
                .number(taskPage.getNumber())
                .size(taskPage.getSize())
                .totalElements(taskPage.getTotalElements())
                .firstPosition(taskPage.isFirst())
                .lastPosition(taskPage.isLast())
                .build();
    }
}

