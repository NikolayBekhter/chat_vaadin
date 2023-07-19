package ru.nikbekhter.vaadin.chat_vaadin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nikbekhter.vaadin.chat_vaadin.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
