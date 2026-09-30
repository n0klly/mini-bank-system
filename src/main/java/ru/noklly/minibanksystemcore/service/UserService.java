package ru.noklly.minibanksystemcore.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.noklly.minibanksystemcore.controller.public_api.dto.LoginRequest;
import ru.noklly.minibanksystemcore.controller.public_api.dto.RegisterRequest;
import ru.noklly.minibanksystemcore.entity.User;
import ru.noklly.minibanksystemcore.entity.UserRole;
import ru.noklly.minibanksystemcore.repository.UserRepository;
import ru.noklly.minibanksystemcore.security.CustomUserDetails;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    //ToDo: responses
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
    @Transactional
    public User login(LoginRequest request){
        Authentication authResult = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        CustomUserDetails userDetails = (CustomUserDetails) authResult.getPrincipal();
        return userDetails.getUser();
    }
}
