package ru.noklly.userservice.controller.public_api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.noklly.userservice.controller.public_api.dto.LoginRequest;
import ru.noklly.userservice.controller.public_api.dto.LoginResponse;
import ru.noklly.userservice.controller.public_api.dto.RegisterRequest;
import ru.noklly.userservice.controller.public_api.dto.RegisterResponse;
import ru.noklly.userservice.service.UserService;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    @PostMapping("/register")
    public RegisterResponse register(@Valid @RequestBody RegisterRequest request){
        return userService.register(request);
    }
    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request){
       return userService.login(request);

    }
}
