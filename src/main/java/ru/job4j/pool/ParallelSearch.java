package ru.job4j.pool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSearch<T> extends RecursiveTask<Integer> {
    private final T[] array;
    private final T value;
    private final int from;
    private final int to;

    public ParallelSearch(T[] array, T value, int from, int to) {
        this.array = array;
        this.value = value;
        this.from = from;
        this.to = to;
    }

    @Override
    protected Integer compute() {
        int rsl;
        if (to - from <= 10) {
            rsl = indexOf(array, value, from, to);
        } else {
            int mid = (from + to) / 2;
            ParallelSearch<T> leftSearch = new ParallelSearch<>(array, value, from, mid);
            ParallelSearch<T> rightSearch = new ParallelSearch<>(array, value, mid + 1, to);
            leftSearch.fork();
            rightSearch.fork();
            int lRsl = leftSearch.join();
            int rRsl = rightSearch.join();
            rsl = Math.max(lRsl, rRsl);
        }
        return rsl;
    }

    private static <T> int indexOf(T[] array, T value, int from, int to) {
        int rsl = -1;
        for (int i = from; i <= to; i++) {
            if (value.equals(array[i])) {
                rsl = i;
                break;
            }
        }
        return rsl;
    }

    public static <T> int indexOf(T[] array, T value) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelSearch<>(array, value, 0, array.length - 1));
    }
}
