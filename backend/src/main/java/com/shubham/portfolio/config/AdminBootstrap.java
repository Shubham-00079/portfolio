package com.shubham.portfolio.config;

import com.shubham.portfolio.domain.Role;
import com.shubham.portfolio.domain.User;
import com.shubham.portfolio.repository.RoleRepository;
import com.shubham.portfolio.repository.UserRepository;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AdminBootstrap implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(AdminBootstrap.class);

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final String email;
    private final String initialPassword;
    private final String firstName;
    private final String lastName;

    public AdminBootstrap(
        RoleRepository roleRepository,
        UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        @Value("${app.bootstrap.admin-email}") String email,
        @Value("${app.bootstrap.admin-initial-password}") String initialPassword,
        @Value("${app.bootstrap.admin-first-name}") String firstName,
        @Value("${app.bootstrap.admin-last-name}") String lastName
    ) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.email = email == null ? "" : email.trim().toLowerCase(Locale.ROOT);
        this.initialPassword = initialPassword == null ? "" : initialPassword;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    @Transactional
    public void run(String... args) {
        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
            .orElseGet(() -> {
                Role role = new Role();
                role.setName("ROLE_ADMIN");
                return roleRepository.save(role);
            });
        roleRepository.findByName("ROLE_USER").orElseGet(() -> {
            Role role = new Role();
            role.setName("ROLE_USER");
            return roleRepository.save(role);
        });

        if (email.isBlank() && initialPassword.isBlank()) {
            log.info("No bootstrap administrator configured; account creation was skipped.");
            return;
        }
        if (email.isBlank() || initialPassword.isBlank() || !email.contains("@")) {
            throw new IllegalStateException("Set both ADMIN_EMAIL and ADMIN_INITIAL_PASSWORD to bootstrap an administrator.");
        }
        if (initialPassword.length() < 12) {
            throw new IllegalStateException("ADMIN_INITIAL_PASSWORD must be at least 12 characters.");
        }
        if (userRepository.findByEmailIgnoreCase(email).isPresent()) {
            log.info("Bootstrap administrator already exists; its password was not changed.");
            return;
        }

        User admin = new User();
        admin.setFirstName(firstName == null || firstName.isBlank() ? "Portfolio" : firstName.trim());
        admin.setLastName(lastName == null || lastName.isBlank() ? "Owner" : lastName.trim());
        admin.setEmail(email);
        admin.setPassword(passwordEncoder.encode(initialPassword));
        admin.setEnabled(true);
        admin.setRole(adminRole);
        userRepository.save(admin);
        log.info("Created the configured bootstrap administrator.");
    }
}
