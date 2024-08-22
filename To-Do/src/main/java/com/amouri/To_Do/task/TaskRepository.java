package com.amouri.To_Do.task;

import com.amouri.To_Do.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    Page<Task> findByUser(User user, Pageable pageable);

    Page<Task> findTasksByTitle(User user, Pageable pageable);
}
