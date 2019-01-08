package com.spring.reactor;

import org.junit.Test;
import reactor.core.publisher.Mono;

public class MonoTest {

    @Test
    public void firstMono() {
        Mono.just("A")
                .log()
        .subscribe();
    }

    @Test
    public void monoWithConsumer() {
        Mono.just("A")
                .log()
                .subscribe(s -> System.out.println(s));
    }

    @Test
    public void monoWithDoOn() {
        Mono.just("A")
                .log()
                .doOnSubscribe(subs -> System.out.println("Subscribed: " + subs))
                .doOnRequest(req -> System.out.println("Request: " + req))
                .doOnSuccess(success -> System.out.println("Complete: " + success))
                .subscribe(System.out::println);
    }

    @Test
    public void emptyMono() {
        Mono.empty()
                .log()
                .subscribe();
    }

    @Test
    public void errorExceptionWithMono() {
        Mono.error(new RuntimeException())
                .log()
                .subscribe();
    }
}
