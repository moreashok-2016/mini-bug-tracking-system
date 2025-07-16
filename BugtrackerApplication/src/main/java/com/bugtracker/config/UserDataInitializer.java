package com.bugtracker.config;

import com.bugtracker.model.User;

import com.bugtracker.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserDataInitializer {

    private final UserRepository userRepository;

    @PostConstruct
    public void initUsers() {
        List<String> emails = List.of("ashok@example.com", "priya@example.com", "rahul@example.com");

        for (String email : emails) {
            if (!userRepository.existsByEmail(email)) {
                switch (email) {
                    case "ashok@example.com" -> userRepository.save(User.builder().name("Ashok More").email(email).build());
                    case "priya@example.com" -> userRepository.save(User.builder().name("Priya Patil").email(email).build());
                    case "rahul@example.com" -> userRepository.save(User.builder().name("Rahul Sharma").email(email).build());
                }
            }
        }

        System.out.println("✅ Sample users loaded (if not already present)");
    }
}
