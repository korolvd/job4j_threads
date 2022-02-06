package ru.job4j;

import java.util.Scanner;

public class MultiUser {
    public static void main(String[] args) throws InterruptedException {
        Barrier barrier = new Barrier();
        Thread master = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    barrier.on();
                },
                "Master"
        );
        Thread slave = new Thread(
                () -> {
                    barrier.check();
                    System.out.println(Thread.currentThread().getName() + " started");
                },
                "Slave"
        );
        master.start();
        master.join();
        barrier.off();
        slave.start();
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        barrier.on();
    }
}
