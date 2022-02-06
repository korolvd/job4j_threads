package ru.job4j.store;

import ru.job4j.store.model.User;

public interface Store {

    boolean add(User user);

    boolean update(User user);

    boolean delete(User user);

    void transfer(int fromId, int toId, int amount);
}
