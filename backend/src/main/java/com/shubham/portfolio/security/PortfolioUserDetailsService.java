package com.shubham.portfolio.security;

import com.shubham.portfolio.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PortfolioUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public PortfolioUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var account = userRepository.findByEmailIgnoreCase(email)
            .orElseThrow(() -> new UsernameNotFoundException("Account not found."));
        return User.withUsername(account.getEmail())
            .password(account.getPassword())
            .authorities(account.getRole().getName())
            .disabled(!Boolean.TRUE.equals(account.getEnabled()))
            .build();
    }
}
