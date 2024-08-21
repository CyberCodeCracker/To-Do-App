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
    private String priority;
    private LocalDateTime createdAt;
    private LocalDateTime dueAt;
    private boolean completed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
