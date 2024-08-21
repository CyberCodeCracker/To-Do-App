package com.amouri.To_Do.task;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskResponse {

    private Integer id;
    private String title;
    private String description;
    private String priority;
    private LocalDateTime createdAt;
    private LocalDateTime dueAt;
    private boolean completed;

}
