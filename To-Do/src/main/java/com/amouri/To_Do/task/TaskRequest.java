package com.amouri.To_Do.task;

import com.amouri.To_Do.validation.FutureDate;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record TaskRequest(
        Integer id,
        @NotNull(message = "100")
        @NotEmpty(message = "100")
        String title,
        @NotNull(message = "101")
        @NotEmpty(message = "101")
        String description,
        @NotNull(message = "102")
        @NotEmpty(message = "102")
        String priority,
        @NotNull(message = "103")
        LocalDateTime createdAt,
        @NotNull(message = "104")
        @FutureDate
        LocalDateTime dueAt,

        boolean completed
) {
}
