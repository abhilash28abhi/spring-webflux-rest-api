package com.spring.reactor;

import org.junit.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class OperatorTest {

    @Test
    //works as same as streams map
    public void map() {
        Flux.range(1, 5)
                .map(i -> i * 10)
                .subscribe(System.out::println);
    }

    @Test
    public void flatMap() {
        Flux.range(1, 5)
                .flatMap(i -> Flux.range(i*10, 2))
                .subscribe(System.out::println);
    }

    @Test
    public void zip() {
        Flux<Integer> oneToFive = Flux.range(1,5);
        Flux<Integer> sixToTen = Flux.range(6,5);

        Flux.zip(oneToFive, sixToTen,
                (item1, item2) -> item1 + ", " + item2)
                .subscribe(System.out::println);

        //zipwith used on flux or mono instance
        /*oneToFive.zipWith(sixToTen)
                .subscribe(System.out::println);*/
    }

    @Test
    //concat will produce the result sequentially
    public void concat() throws InterruptedException {
        Flux<Integer> oneToFive = Flux.range(1,5).delayElements(Duration.ofMillis(200));
        Flux<Integer> sixToTen = Flux.range(6,5).delayElements(Duration.ofMillis(400));

        Flux.concat(oneToFive, sixToTen)
                .subscribe(System.out::println);

        //concatWith used on flux or mono instance
        /*oneToFive.concatWith(sixToTen)
                .subscribe(System.out::println);*/

        Thread.sleep(4000);
    }

    @Test
    //merge will have teh results interleaved
    public void merge() throws InterruptedException {
        Flux<Integer> oneToFive = Flux.range(1,5).delayElements(Duration.ofMillis(200));
        Flux<Integer> sixToTen = Flux.range(6,5).delayElements(Duration.ofMillis(400));

        Flux.merge(oneToFive, sixToTen)
                .subscribe(System.out::println);

        //concatWith used on flux or mono instance
        /*oneToFive.mergeWith(sixToTen)
                .subscribe(System.out::println);*/

        Thread.sleep(4000);
    }
}
