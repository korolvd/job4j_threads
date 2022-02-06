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
            try {
                queue.offer(1);
                queue.offer(2);
                queue.offer(3);
                queue.offer(4);
                queue.offer(5);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        });
        Thread consumer = new Thread(() -> {
            try {
                actual.add(queue.poll());
                actual.add(queue.poll());
                actual.add(queue.poll());
                actual.add(queue.poll());
                actual.add(queue.poll());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertThat(actual, is(List.of(1, 2, 3, 4, 5)));
    }

}