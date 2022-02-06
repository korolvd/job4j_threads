package ru.job4j.store;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import ru.job4j.store.model.User;

import java.util.HashMap;
import java.util.Map;

@ThreadSafe
public final class UserStorage implements Store {

    @GuardedBy("this")
    private final Map<Integer, User> users = new HashMap<>();


    @Override
    public synchronized boolean add(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User is null");
        }
        boolean rsl = false;
        if (!users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            rsl = true;
        }
        return rsl;
    }

    @Override
    public synchronized boolean update(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User is null");
        }
        boolean rsl = false;
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            rsl = true;
        }
        return rsl;
    }

    @Override
    public synchronized boolean delete(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User is null");
        }
        return users.remove(user.getId()) == user;
    }

    @Override
    public synchronized void transfer(int fromId, int toId, int amount) {
        if (fromId == toId || fromId < 0 || toId < 0 || amount <= 0) {
            throw new IllegalArgumentException("Illegal ids or amount");
        }
        if (!users.containsKey(fromId) || !users.containsKey(toId)) {
            throw new IllegalArgumentException("Sours account or destination account is not exist");
        }
        if (users.get(fromId).getAmount() < amount) {
            throw new IllegalArgumentException("Not enough amount on the account to transfer");
        }
        User sourceUser = users.get(fromId);
        User destUser = users.get(toId);
        sourceUser.setAmount(sourceUser.getAmount() - amount);
        destUser.setAmount(destUser.getAmount() + amount);
    }
}
