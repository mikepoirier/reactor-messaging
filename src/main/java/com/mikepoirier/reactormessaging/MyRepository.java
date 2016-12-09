package com.mikepoirier.reactormessaging;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.ReplayProcessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyRepository implements Subscriber<MyMessage> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyRepository.class);

    private final String name;
    private final Map<String, String> data;

    public MyRepository(ReplayProcessor<MyMessage> messageEmitter, String name) {
        this.name = name;
        this.data = new HashMap<>();

        messageEmitter.subscribe(this);
    }

    public List<String> findAll() {
        return new ArrayList<>(data.values());
    }

    public Map<String, String> getAll() {
        return data;
    }

    public String findByKey(String key) {
        return data.get(key);
    }

    @Override
    public void onSubscribe(Subscription s) {
        LOGGER.info(String.format("%s is subscribed to message updates.", name));
        s.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(MyMessage myMessage) {
        if (!myMessage.getId().equals(name)) return;

        LOGGER.info(String.format("%s Received message: %s", name, myMessage.toString()));
        data.put(myMessage.getKey(), myMessage.getValue());
    }

    @Override
    public void onError(Throwable t) {
        LOGGER.error(String.format("%s encountered an error.", name), t);
    }

    @Override
    public void onComplete() {
        LOGGER.info(String.format("%s completed.", name));
    }
}
