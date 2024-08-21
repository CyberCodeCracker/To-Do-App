package com.amouri.To_Do.user;

import com.amouri.To_Do.common.BaseEntity;
import com.amouri.To_Do.task.Task;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "_user")
@SuperBuilder
@Entity
public class User extends BaseEntity implements UserDetails, Principal  {

    private String username;
    private String password;
    @OneToMany(mappedBy = "user")
    private List<Task> tasks;
    @Column(unique = true)
    private String email;

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void removeTask(Task task) {
        tasks.remove(task);
    }

}
