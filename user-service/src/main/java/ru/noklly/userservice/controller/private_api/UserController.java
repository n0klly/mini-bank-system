package ru.noklly.userservice.controller.private_api;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.noklly.userservice.controller.public_api.dto.UserProfileResponse;
import ru.noklly.userservice.entity.User;
import ru.noklly.userservice.security.CustomUserDetails;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("/me")
    public UserProfileResponse getCurrentUser(@AuthenticationPrincipal CustomUserDetails userDetails){
        User user = userDetails.getUser();
        return new UserProfileResponse(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getRole(),
                user.getCreatedAt()
                );
    }
}
