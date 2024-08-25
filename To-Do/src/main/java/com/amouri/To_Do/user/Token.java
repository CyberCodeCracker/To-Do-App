package com.amouri.To_Do.user;

import com.amouri.To_Do.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
// CHANGES AFTER MIGRATION TO KEYCLOAK
// @Entity
public class Token {

    @Id
    @GeneratedValue
    private Integer id;
    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private LocalDateTime validatedAt;

    @ManyToOne
    @JoinColumn(
            name = "userId",
            nullable = false
    )
    private User user;
}
