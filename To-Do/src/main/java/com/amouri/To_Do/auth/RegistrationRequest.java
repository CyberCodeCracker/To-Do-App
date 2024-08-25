package com.amouri.To_Do.auth;

import jakarta.validation.constraints.*;

public record RegistrationRequest(

      @NotBlank(message = "Username is required")
      @NotEmpty(message = "Username is required")
      String username,
      @Size(min = 8, message = "Password must be at least 8 characters long")
      @NotBlank(message = "Password is required")
      @NotEmpty(message = "Password is required")
      String password,
      @Email(message = "Email is not formatted correctly")
      @NotBlank(message = "Email is required")
      @NotEmpty(message = "Email is required")
      String email

) {

}
