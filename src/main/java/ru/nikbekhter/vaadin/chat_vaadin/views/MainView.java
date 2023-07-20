package ru.nikbekhter.vaadin.chat_vaadin.views;

import com.github.rjeschke.txtmark.Processor;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;
import ru.nikbekhter.vaadin.chat_vaadin.entities.User;
import ru.nikbekhter.vaadin.chat_vaadin.exception.UserNotFoundException;
import ru.nikbekhter.vaadin.chat_vaadin.services.UserService;
import ru.nikbekhter.vaadin.chat_vaadin.utils.Storage;

import java.util.Optional;

@Route("")
public class MainView extends VerticalLayout {
    private final UserService userService;
    private final Storage storage;
    private Registration registration;
    private Grid<Storage.ChatMessage> grid;
    private VerticalLayout chat;
    private VerticalLayout login;
    private User user = new User();

    public MainView(UserService userService, Storage storage) {
        this.userService = userService;
        this.storage = storage;
        buildLogin();
        buildChat();
    }

    private void buildLogin() {
        login = new VerticalLayout() {{
            TextField field = new TextField();

            field.setPlaceholder("Please, introduce yourself");
            add(
                    field,
                    new Button("Login") {{
                        addClickListener(click -> {
                            login.setVisible(false);
                            chat.setVisible(true);
                            user.setUsername(field.getValue());
                            if (userService.findByUsernameIgnoreCase(field.getValue()).isEmpty()) {
                                userService.save(user);
                            }
                            storage.addRecordJoined(user.getUsername());
                        });
                        addClickShortcut(Key.ENTER);
                    }}
            );
        }};
        add(login);
    }

    private void buildChat() {
        chat = new VerticalLayout();
        chat.setVisible(false);
        add(chat);
        grid = new Grid<>();
        grid.setItems(storage.getChatMessages());
        grid.addColumn(new ComponentRenderer<>(chatMessage -> new Html(renderRow(chatMessage))))
                .setAutoWidth(true);

        TextField field = new TextField();

        chat.add(
                new H3("Vaadin chat"),
                grid,
                new HorizontalLayout() {{
                    add(
                            field,
                            new Button("Send message") {{
                                addClickListener(click -> {
                                    storage.addRecord(user.getUsername(), field.getValue());
                                    field.clear();
                                });
                                addClickShortcut(Key.ENTER);
                            }}
                    );

                }}
        );
    }

    public void onMessage(Storage.ChatEvent event) {
        if (getUI().isPresent()) {
            UI ui = getUI().get();
            ui.getSession().lock();
            ui.beforeClientResponse(grid, ctx -> grid.scrollToEnd());
            ui.access(() -> grid.getDataProvider().refreshAll());
            ui.getSession().unlock();
        }
    }

    private String getUsername(String username) {
        Optional<User> user = userService.findByUsernameIgnoreCase(username);
        if (user.isPresent()) {
            return user.get().getUsername();
        } else {
            throw new UserNotFoundException("Пользователь с именем " + username + " не найден!");
        }
    }

    private static String renderRow(Storage.ChatMessage chatMessage) {
        if (chatMessage.getName().isEmpty()) {
            return Processor.process(String.format("_User **%s** is just joined the chat!_", chatMessage.getMessage()));
        } else {
            return Processor.process(String.format("**%s**: %s", chatMessage.getName(), chatMessage.getMessage()));
        }
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        registration = storage.attachListener(this::onMessage);
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        registration.remove();
    }
}
