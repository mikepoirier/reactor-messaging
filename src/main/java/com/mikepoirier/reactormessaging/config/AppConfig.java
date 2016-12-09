package com.mikepoirier.reactormessaging.config;

import com.mikepoirier.reactormessaging.MyMessage;
import com.mikepoirier.reactormessaging.MyRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.ReplayProcessor;

@Configuration
public class AppConfig {
    @Bean
    public ReplayProcessor<MyMessage> messageReplayer() {
        return ReplayProcessor.create();
    }

    @Bean
    @MyListener("Foo")
    public MyRepository fooRepository() {
        return new MyRepository(messageReplayer(), "Foo");
    }

    @Bean
    @MyListener("Bar")
    public MyRepository barRepository() {
        return new MyRepository(messageReplayer(), "Bar");
    }
}
