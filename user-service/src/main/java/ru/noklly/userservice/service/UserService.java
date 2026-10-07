package ru.noklly.userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.noklly.userservice.controller.public_api.dto.LoginRequest;
import ru.noklly.userservice.controller.public_api.dto.LoginResponse;
import ru.noklly.userservice.controller.public_api.dto.RegisterRequest;
import ru.noklly.userservice.controller.public_api.dto.RegisterResponse;
import ru.noklly.userservice.entity.User;
import ru.noklly.userservice.entity.UserRole;
import ru.noklly.userservice.repository.UserRepository;
import ru.noklly.userservice.security.CustomUserDetails;
import ru.noklly.userservice.security.token.JwtService;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public RegisterResponse register(RegisterRequest request){
        if(userRepository.existsByEmail(request.getEmail())){
            throw new  RuntimeException("User is already exist!");
        }
        return new RegisterResponse(userRepository.save(
                new User(request.getEmail(),
                        request.getName(),
                        passwordEncoder.encode(request.getPassword()),
                        UserRole.USER
                )).getName());
    }

    public LoginResponse login(LoginRequest request){
        Authentication authResult = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        CustomUserDetails userDetails = (CustomUserDetails) authResult.getPrincipal();
        User user = userDetails.getUser();
        String jwtToken = jwtService.generateToken(user.getEmail());

        return new LoginResponse(user.getName(), jwtToken);
    }
}
