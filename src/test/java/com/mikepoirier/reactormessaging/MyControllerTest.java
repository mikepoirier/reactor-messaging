package com.mikepoirier.reactormessaging;

import org.junit.Test;
import reactor.core.publisher.BlockingSink;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.ReplayProcessor;

import static org.junit.Assert.*;

public class MyControllerTest {

    @Test
    public void foo() throws Exception {
        ReplayProcessor<Integer> replayer = ReplayProcessor.create();
        BlockingSink<Integer> sink = replayer.connectSink();
        sink.submit(1);
        sink.submit(2);
        replayer.subscribe(System.out::println); //output 1, 2
        replayer.subscribe(System.out::println); //output 1, 2
        sink.submit(3); //output : ...3 ...3
        sink.finish();
    }

}