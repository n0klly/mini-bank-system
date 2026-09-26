package ru.noklly.minibanksystemcore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.noklly.minibanksystemcore.entity.User;
import ru.noklly.minibanksystemcore.controller.public_api.dto.RegisterRequest;
import ru.noklly.minibanksystemcore.entity.UserRole;
import ru.noklly.minibanksystemcore.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public User register(RegisterRequest request){
        if(userRepository.existsByEmail(request.getEmail())){
            throw new  RuntimeException("User is already exist!");
        }
        return userRepository.save(
                new User(request.getEmail(),
                        request.getName(),
                        passwordEncoder.encode(request.getPassword()),
                        UserRole.USER
                ));
    }
}
