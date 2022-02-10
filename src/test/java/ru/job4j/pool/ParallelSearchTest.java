package ru.job4j.pool;

import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

public class ParallelSearchTest {

    @Test
    public void searchString() {
        String[] array = new String[25];
        for (int i = 0; i < array.length; i++) {
            array[i] = String.valueOf((char) (i + 65));
        }
        int actual = ParallelSearch.indexOf(array, "A");
        assertThat(actual, is(0));
    }

    @Test
    public void searchInt() {
        Integer[] array = new Integer[100];
        for (int i = 0; i < array.length; i++) {
            array[i] = i + 1;
        }
        int actual = ParallelSearch.indexOf(array, 100);
        assertThat(actual, is(99));
    }
}