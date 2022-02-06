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
        return users.putIfAbsent(user.getId(), user) == null;
    }

    @Override
    public synchronized boolean update(User user) {
        return users.replace(user.getId(), user) != null;
    }

    @Override
    public synchronized boolean delete(User user) {
        return users.remove(user.getId(), user);
    }

    @Override
    public synchronized void transfer(int fromId, int toId, int amount) {
        User sourceUser = users.get(fromId);
        User destUser = users.get(toId);
        if (sourceUser == null || destUser == null || users.get(fromId).getAmount() < amount) {
            throw new IllegalArgumentException(
                    "Accounts is not exists or not enough amount on the account to transfer");
        }

        sourceUser.setAmount(sourceUser.getAmount() - amount);
        destUser.setAmount(destUser.getAmount() + amount);
    }
}
