package com.amouri.To_Do.task;
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
            throw new AccessDeniedException("You don't have permission to update book archived status.");
        }
        if (newTitle != null && newTitle.isEmpty()) {
            updateTaskTitle(task, newTitle);
        }
        if (newDescription != null && newDescription.isEmpty()) {
            updateTaskDescription(task, newDescription);
        }
        if (newDueAt != null) {
            updateTaskDueAt(task, newDueAt);
        }
        taskRepository.save(task);
    }

    private void updateTaskDueAt(Task task, LocalDateTime newDueAt) {
        task.setDueAt(newDueAt);
    }

    private void updateTaskDescription(Task task, String newDescription) {
        task.setDescription(newDescription);
    }

    private void updateTaskTitle(Task task, String newTitle) {
        task.setTitle(newTitle);
    }

    public void toggleTaskCompletion(Integer taskId, Authentication connectedUser) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));
        task.setCompleted(!task.isCompleted());
        taskRepository.save(task);
    }

    public List<Task> getTasksSortedByDueDate() {
        return taskRepository.findAll(Sort.by(Sort.Direction.ASC, "dueAt"));
    }

    public List<Task> findTasksByTitle(String query, int page, int size, String sortBy, boolean ascending) {
        Pageable pageable = PageRequest.of(page, size, ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());
        Page<Task> resultPage = taskRepository.findTasksByTitle( query, query, pageable);
        return resultPage.getContent();
    }

    public void setTaskPriority(Integer taskId, String priority) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));
        task.setPriority(priority);
        taskRepository.save(task);
    }
}

