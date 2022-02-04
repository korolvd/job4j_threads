package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public final class ParseFile {

    private final ISave saver;
    private final IReader reader;

    public ParseFile(File file) {
        this.saver = new SaveToFile(file);
        this.reader = new FileReader(file);
    }

    public synchronized String getContent(Predicate<Character> filter) {
        return reader.getContent(filter);
    }

    public synchronized void saveContent(String content) {
        saver.saveContent(content);
    }
}
