package ru.job4j.cache;

import org.junit.Test;

import static org.junit.Assert.*;

public class CacheTest {

    @Test
    public void whenAddThenTrue() {
        Cache cache = new Cache();
        assertTrue(cache.add(new Base(0, 0)));
    }

    @Test
    public void whenDoubleAddThenFalse() {
        Cache cache = new Cache();
        assertTrue(cache.add(new Base(0, 0)));
        assertFalse(cache.add(new Base(0, 0)));
    }

    @Test
    public void whenUpdateThenTrue() {
        Cache cache = new Cache();
        Base base = new Base(0, 0);
        cache.add(base);
        base.setName("base");
        assertTrue(cache.update(base));
    }

    @Test(expected = OptimisticException.class)
    public void whenUpdateThenException() {
        Cache cache = new Cache();
        Base base = new Base(0, 0);
        cache.add(base);
        base.setName("base");
        cache.update(base);
        base.setName("new name");
        cache.update(base);
    }
}