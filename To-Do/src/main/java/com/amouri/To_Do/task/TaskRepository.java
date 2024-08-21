package com.amouri.To_Do.task;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    Page<Task> findTasksByTitle(String titleQuery, String descriptionQuery, Pageable pageable);
}
