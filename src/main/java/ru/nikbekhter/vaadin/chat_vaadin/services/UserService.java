package ru.nikbekhter.vaadin.chat_vaadin.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nikbekhter.vaadin.chat_vaadin.entities.User;
import ru.nikbekhter.vaadin.chat_vaadin.repositories.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public Optional<User> findByUsernameIgnoreCase(String username) {
        return repository.findByUsernameIgnoreCase(username);
    }

    public User save(User user) {
        return repository.save(user);
    }
}
