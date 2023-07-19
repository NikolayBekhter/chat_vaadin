package ru.nikbekhter.vaadin.chat_vaadin.configs;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.server.PWA;

@Push
@PWA(name = "My App", shortName = "My App", startPath = "main")
public class AppShellConfig implements AppShellConfigurator {
}
