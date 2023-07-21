package ru.nikbekhter.vaadin.chat_vaadin.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nikbekhter.vaadin.chat_vaadin.entities.Message;
import ru.nikbekhter.vaadin.chat_vaadin.repositories.MessageRepository;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository repository;

    public void save(Message message) {
        repository.save(message);
    }
}
