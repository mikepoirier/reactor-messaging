package com.mikepoirier.reactormessaging;

import com.mikepoirier.reactormessaging.config.MyListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.BlockingSink;
import reactor.core.publisher.ReplayProcessor;

import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;

@RestController
@RequestMapping("/message")
public class MyController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyController.class);

    private final BlockingSink<MyMessage> messageSink;
    private final MyRepository fooRepository;
    private final MyRepository barRepository;

    @Autowired
    public MyController(
        ReplayProcessor<MyMessage> messageReplayer,
        @MyListener("Foo") MyRepository fooRepository,
        @MyListener("Bar") MyRepository barRepository
    ) {
        this.messageSink = messageReplayer.connectSink();
        this.fooRepository = fooRepository;
        this.barRepository = barRepository;
    }

    @GetMapping("/{key}")
    public ResponseEntity<List<String>> getMessages(@PathVariable String key) {
        List<String> list;

        switch (key) {
            case "Foo":
                list = fooRepository.findAll();
                break;
            case "Bar":
                list = barRepository.findAll();
                break;
            default:
                list = emptyList();
                break;
        }

        return ResponseEntity.ok(list);
    }

    @PostMapping("/{key}")
    public ResponseEntity addData(@PathVariable String key, @RequestBody Map<String, String> data) {
        MyMessage message = new MyMessage(key, data.get("key"), data.get("value"));
        LOGGER.info(String.format("Submitting message: %s", data.toString()));
        messageSink.submit(message);

        return ResponseEntity.ok(null);
    }
}
