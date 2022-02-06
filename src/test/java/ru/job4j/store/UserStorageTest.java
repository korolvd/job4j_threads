package ru.job4j.store;

import org.junit.Test;
import ru.job4j.Count;
import ru.job4j.CountTest;
import ru.job4j.store.model.User;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class UserStorageTest {

    private class ThreadTransfer extends Thread {
        private final UserStorage storage;
        private final int source;
        private final int dest;
        private final int amount;

        private ThreadTransfer(final UserStorage storage, int source, int dest, int amount) {
            this.storage = storage;
            this.source = source;
            this.dest = dest;
            this.amount = amount;
        }

        @Override
        public void run() {
            this.storage.transfer(source, dest, amount);
        }
    }

    @Test
    public void testStorage() throws InterruptedException {
        final UserStorage storage = new UserStorage();
        User user1 = new User(1, 100);
        User user2 = new User(2, 100);
        User user3 = new User(1, 0);
        User user4 = new User(3, 0);
        assertTrue(storage.add(user1));
        assertFalse(storage.add(user3));
        assertTrue(storage.add(user2));
        assertTrue(storage.add(user4));
        assertTrue(storage.update(new User(3, 100)));
        assertFalse(storage.update(new User(4, 100)));
        Thread firstThread = new UserStorageTest.ThreadTransfer(
                storage, user1.getId(), user2.getId(), 50);
        Thread secondThread = new UserStorageTest.ThreadTransfer(
                storage, user2.getId(), user1.getId(), 150
        );
        firstThread.start();
        firstThread.join();
        assertThat(user1.getAmount(), is(50));
        assertThat(user2.getAmount(), is(150));
        secondThread.start();
        secondThread.join();
        assertThat(user1.getAmount(), is(200));
        assertThat(user2.getAmount(), is(0));


    }

}