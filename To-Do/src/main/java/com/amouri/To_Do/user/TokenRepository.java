package com.amouri.To_Do.user;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
// CHANGES AFTER MIGRATION TO KEYCLOAK
public interface TokenRepository extends JpaRepository<Token, Integer> {

    Optional<Token> findByToken(String token);
}

