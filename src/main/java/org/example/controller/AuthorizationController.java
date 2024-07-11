package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.dto.ApiResponse;
import org.example.dto.auth.LoginDTO;
import org.example.dto.auth.RegistrationDTO;
import org.example.service.AuthorizationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

@EnableMethodSecurity(prePostEnabled = true)
@RestController
@RequestMapping("/api/v1/authorization")
@Tag(name = "Auth Controller", description = "Api list for authorization, registration and other ....")
public class AuthorizationController {
    private final AuthorizationService authorizationService;

    public AuthorizationController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @PostMapping("/registration")
    @Operation(summary = "Registration", description = "Api for profile registration")
    public ResponseEntity<ApiResponse<?>> registration(@Valid @RequestBody RegistrationDTO registrationDTO) {
        ApiResponse<?> response = authorizationService.registration(registrationDTO);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@Valid @RequestBody LoginDTO loginDTO) {
        ApiResponse<?> response = authorizationService.login(loginDTO);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/verification/{userId}")
    public ResponseEntity<ApiResponse<?>> verification(@PathVariable("userId") Integer userId) {
        ApiResponse<?> response = authorizationService.authorizationVerification(userId);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/registration/resend/{phone}")
    public ResponseEntity<ApiResponse<?>> registrationResend(@PathVariable("phone") String phone) {
        ApiResponse<?> response = authorizationService.registrationResendPhone(phone);
        return ResponseEntity.ok().body(response);
    }
}
