package ru.nikbekhter.vaadin.chat_vaadin.utils;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventBus;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.shared.Registration;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.nikbekhter.vaadin.chat_vaadin.entities.Message;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class Storage {
    @Getter
    private Queue<ChatMessage> chatMessages = new ConcurrentLinkedQueue<>();
    private ComponentEventBus eventBus = new ComponentEventBus(new Div());

    public static class ChatEvent extends ComponentEvent<Div> {
        public ChatEvent() {
            super(new Div(), false);
        }
    }

    @Getter
    @AllArgsConstructor
    public static class ChatMessage {
        private String name;
        private String message;
    }

    public Registration attachListener(ComponentEventListener<ChatEvent> messageListener) {
        return eventBus.addListener(ChatEvent.class, messageListener);
    }

    public int size() {
        return chatMessages.size();
    }

    public void addRecord(String user, String message) {
        chatMessages.add(new ChatMessage(user, message));
        eventBus.fireEvent(new ChatEvent());
    }

    public void addRecordJoined(String user) {
        chatMessages.add(new ChatMessage("", user));
        eventBus.fireEvent(new ChatEvent());
    }

}
