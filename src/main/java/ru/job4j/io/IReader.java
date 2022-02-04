package ru.job4j.io;

import java.util.function.Predicate;

public interface IReader {

    String getContent(Predicate<Character> filter);
}
