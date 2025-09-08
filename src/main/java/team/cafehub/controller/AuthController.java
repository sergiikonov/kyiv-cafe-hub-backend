package team.cafehub.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.cafehub.dto.user.UserLoginRequestDto;
import team.cafehub.dto.user.UserLoginResponseDto;
import team.cafehub.security.AuthenticationService;

@RestController
@RequiredArgsConstructor
@Tag(name = "Authorization controller", description = "Operations with authentication")
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationService authenticationService;

    @Operation(summary = "Login method",
            description = "Login into app by email and password")
    @PostMapping("/login")
    public UserLoginResponseDto login(@RequestBody @Valid UserLoginRequestDto request) {
        return authenticationService.authenticate(request);
    }
}
