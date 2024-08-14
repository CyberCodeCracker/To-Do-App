package com.amouri.To_Do.task;

import com.amouri.To_Do.common.BaseEntity;
import com.amouri.To_Do.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "_tasks")
@SuperBuilder
@Entity
public class Task extends BaseEntity {

    private String title;
    private String description;
    private LocalDateTime dueAt;
    private String status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void editTaskDetails(String newTitle, String newDescription, LocalDateTime newDueAt) {
        if (newTitle != null && newTitle.isEmpty()) {
            this.title = newTitle;
        }
        if (newDescription != null && newDescription.isEmpty()) {
            this.description = newDescription;
        }
        if (newDueAt != null) {
            this.dueAt = newDueAt;
        }
    }
}
