package ru.nikbekhter.vaadin.chat_vaadin;

import com.vaadin.flow.spring.annotation.EnableVaadin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class ChatVaadinApplication extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ChatVaadinApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(ChatVaadinApplication.class, args);
	}

}
