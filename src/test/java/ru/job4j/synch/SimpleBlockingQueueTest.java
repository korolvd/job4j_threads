package ru.job4j.synch;

import org.junit.Test;

import java.util.*;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class SimpleBlockingQueueTest {

    @Test
    public void test() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(2);
        List<Integer> actual = new ArrayList<>();
        Thread producer = new Thread(() -> {
            queue.offer(1);
            queue.offer(2);
            queue.offer(3);
            queue.offer(4);
            queue.offer(5);
        });
        Thread consumer = new Thread(() -> {
            actual.add(queue.poll());
            actual.add(queue.poll());
            actual.add(queue.poll());
            actual.add(queue.poll());
            actual.add(queue.poll());
        });
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertThat(actual, is(List.of(1, 2, 3, 4, 5)));
    }

}