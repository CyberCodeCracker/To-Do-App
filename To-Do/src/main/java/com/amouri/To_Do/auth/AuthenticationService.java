package com.amouri.To_Do.auth;

import com.amouri.To_Do.role.RoleRepository;
import com.amouri.To_Do.user.Token;
import com.amouri.To_Do.user.TokenRepository;
import com.amouri.To_Do.user.User;
import com.amouri.To_Do.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.security.config.annotation.web.oauth2.login.TokenEndpointDsl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    public void register(RegistrationRequest request) {
       var userRole = roleRepository.findByName("USER")
               .orElseThrow(() -> new IllegalStateException("ROLE USER was not initialized"));
       var user = User.builder()
               .username(request.username())
               .email(request.email())
               .password(passwordEncoder.encode(request.password()))
               .roles(List.of(userRole))
               .build();
       userRepository.save(user);
       sendValidationEmail(user);
    }

    public void sendValidationEmail(User user) {
        var newToken = generateAndSaveActivationToken(user);
    }

    private String generateAndSaveActivationToken(User user) {
        String generatedToken = generateActivationCode(6);
        var token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(30))
                .user(user)
                .build();
        tokenRepository.save(token);
        return generatedToken;
    }

    private String generateActivationCode(int length) {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }
        return codeBuilder.toString();
    }
}
