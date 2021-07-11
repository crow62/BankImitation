package ru.meleshin;

import java.util.Random;
import java.util.concurrent.Callable;

public class ExternalSystem implements Callable<Integer> {
    Random random = new Random();

    @Override
    public Integer call() throws Exception {
        Thread.sleep(1000 * ((random.nextInt(5)) + 5));
        int amount = 1000 * (random.nextInt(10) + 1);
        System.err.println("Получена сумма из: " + Thread.currentThread().getName() + " - " + amount);
        return amount;

    }
}
