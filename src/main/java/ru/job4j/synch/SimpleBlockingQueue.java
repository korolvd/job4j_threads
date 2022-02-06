package ru.job4j.synch;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();
    private final int size;

    public SimpleBlockingQueue() {
        this.size = Integer.MAX_VALUE;
    }

    public SimpleBlockingQueue(int size) {
        this.size = size;
    }

    public synchronized void offer(T value) throws InterruptedException {
        if (queue.size() == size) {
            this.wait();
        }
        if (queue.size() == 0) {
            notifyAll();
        }
        queue.offer(value);
    }

    public synchronized T poll() throws InterruptedException {
        if (queue.size() == 0) {
            this.wait();
        }
        if (queue.size() == size) {
            notifyAll();
        }
        return queue.poll();
    }
}
