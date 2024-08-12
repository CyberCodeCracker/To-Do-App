package com.amouri.To_Do.task;

import com.amouri.To_Do.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

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

}
