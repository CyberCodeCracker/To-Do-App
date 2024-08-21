package com.amouri.To_Do.task;

import org.springframework.stereotype.Service;

@Service
public class TaskMapper {

    public Task toTask(TaskRequest request) {
        return Task.builder()
                .id(request.id())
                .title(request.title())
                .description(request.description())
                .priority(request.priority())
                .createdAt(request.createdAt())
                .dueAt(request.dueAt())
                .completed(request.completed())
                .build();
    }

    public TaskResponse toTaskResponse(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .priority(task.getPriority())
                .createdAt(task.getCreatedAt())
                .dueAt(task.getDueAt())
                .completed(task.isCompleted())
                .build();
    }
}
