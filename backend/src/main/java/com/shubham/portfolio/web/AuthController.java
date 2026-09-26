package com.shubham.portfolio.web;

import com.shubham.portfolio.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    public AuthController(
        AuthenticationManager authenticationManager,
        UserDetailsService userDetailsService,
        JwtService jwtService
    ) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ApiModels.LoginView login(@Valid @RequestBody ApiModels.LoginInput input) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(input.email().trim(), input.password())
        );
        UserDetails user = userDetailsService.loadUserByUsername(input.email().trim());
        return new ApiModels.LoginView(jwtService.generateToken(user), "Bearer", "Login Successful");
    }
}
