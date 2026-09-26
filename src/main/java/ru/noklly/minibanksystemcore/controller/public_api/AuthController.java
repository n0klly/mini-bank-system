package ru.noklly.minibanksystemcore.controller.public_api;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.noklly.minibanksystemcore.controller.public_api.dto.RegisterRequest;
import ru.noklly.minibanksystemcore.service.UserService;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    @PostMapping("/register")
    public void register(@Valid @RequestBody RegisterRequest request){
        userService.register(request);
    }
    public void login(){//email, password

    }
}
