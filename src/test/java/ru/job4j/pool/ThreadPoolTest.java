package ru.job4j.pool;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ThreadPoolTest {

    @Test
    public void work() throws InterruptedException {
        ThreadPool threadPool = new ThreadPool();
        List<Integer> buffer = new ArrayList<>();
        Runnable task = () -> buffer.add(1);
        for (int i = 0; i < 8; i++) {
            threadPool.work(task);
        }
        Thread.sleep(3000);
        threadPool.shutdown();
        assertThat(buffer, is(List.of(1, 1, 1, 1, 1, 1, 1, 1)));
    }
}