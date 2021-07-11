package ru.meleshin;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BackSystem {

    private final Lock lock = new ReentrantLock();

    private CountDownLatch countDownLatch;

    public BackSystem() {
        try {
            getBalanceFromExternalSystems();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void setCountDownLatch(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    //private volatile int balance;
    private AtomicInteger balance = new AtomicInteger(0);


    public AtomicInteger getBalance() {
        return balance;
    }

    public void takeLoan(Request request, int credit) {
        lock.lock();
        try {
            countDownLatch.countDown();
            if (credit > getBalance().get()) {
                throw new RuntimeException("Бэк система: ЗАЯВКА " + request + "НЕ ВЫПОЛНЕНА ." +
                        " Получена от " + Thread.currentThread().getName().toLowerCase(Locale.ROOT) +
                        ". Сумма больше баланса банка. Баланс банка - " + getBalance());
            } else {
                balance.addAndGet(-credit);
                printMessageSuccess(request);
            }
        } finally {
            lock.unlock();
        }
    }

    public void repayLoan(Request request, int repayment) {
        lock.lock();
        try {
            countDownLatch.countDown();
            balance.addAndGet(repayment);
            printMessageSuccess(request);
        } finally {
            lock.unlock();
        }
    }

    private void printMessageSuccess(Request request) {

        System.out.println("Бэк система: ЗАЯВКА " + request + "УСПЕШНО ВЫПОЛНЕНА ." +
                " Получена от " + Thread.currentThread().getName().toLowerCase(Locale.ROOT) +
                ". Баланс банка - " + getBalance());

    }

    private void getBalanceFromExternalSystems() throws InterruptedException, ExecutionException {

        List<Callable<Integer>> callableList = new LinkedList<>();
        ExecutorService executorService = Executors.newCachedThreadPool(new NamingThreadsInPool("Внешняя система"));
        for (int i = 0; i < 3; i++) {
            callableList.add(new ExternalSystem());
        }
        List<Future<Integer>> futures = executorService.invokeAll(callableList);

        executorService.shutdown();
        for (Future<Integer> future : futures) {
            balance.addAndGet(future.get());
        }
    }

}
