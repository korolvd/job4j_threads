package ru.job4j.store;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import ru.job4j.store.model.User;

import java.util.ArrayList;
import java.util.List;

@ThreadSafe
public final class UserStorage implements Store {

    @GuardedBy("this")
    private final List<User> users = new ArrayList<>();


    @Override
    public synchronized boolean add(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User is null");
        }
        boolean rsl = false;
        if (!users.contains(user)) {
            rsl = users.add(user);
        }
        return rsl;
    }

    @Override
    public synchronized boolean update(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User is null");
        }
        boolean rsl = false;
        if (users.contains(user)) {
            users.set(users.indexOf(user), user);
            rsl = true;
        }
        return rsl;
    }

    @Override
    public synchronized boolean delete(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User is null");
        }
        return users.remove(user);
    }

    @Override
    public synchronized void transfer(int fromId, int toId, int amount) {
        if (fromId == toId || fromId < 0 || toId < 0 || amount <= 0) {
            throw new IllegalArgumentException("Illegal ids or amount");
        }
        User sourceUser = getBiId(fromId);
        User destUser = getBiId(toId);
        if (sourceUser == null || destUser == null) {
            throw new IllegalArgumentException("Sours account or destination account is not exist");
        }
        if (sourceUser.getAmount() < amount) {
            throw new IllegalArgumentException("Not enough amount on the account to transfer");
        }
        sourceUser.setAmount(sourceUser.getAmount() - amount);
        destUser.setAmount(destUser.getAmount() + amount);
    }

    private synchronized User getBiId(int id) {
        User user = null;
        for (User u : users) {
            if (u.getId() == id) {
                user = u;
            }
        }
        return user;
    }
}
