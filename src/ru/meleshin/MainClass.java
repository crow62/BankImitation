package ru.meleshin;

import java.util.*;
import java.util.concurrent.*;

public class MainClass {

    public static void main(String[] args) {

        System.out.println("Ожидаем пополнение баланса банка...");

        FrontSystem frontSystem = new FrontSystem();
        BackSystem backSystem = new BackSystem();

        System.err.println("\nБАЛАНС БАНКА - " + backSystem.getBalance());
        Scanner scanner = new Scanner(System.in);


        System.err.println("Введите количество клиентов: ");
        int numberClients;
        numberClients = scanner.nextInt();

        System.err.println("Введите количество обработчиков: ");
        int numberHandlers;
        numberHandlers = scanner.nextInt();

        Random random = new Random();

        List<Thread> threadsClient = new ArrayList<>();
        for (int i = 0; i < numberClients; i++) {
            threadsClient.add(new Thread(new Client(frontSystem, new Request
                    ("Клиент" + (i + 1), 5000 * (1 + random.nextInt(10)),
                            Request.Type.values()[random.nextInt(2)]))));
        }

        //создаем пул с именованнми потоками для клиентов
        ExecutorService firstExecutorService = Executors.newFixedThreadPool(numberClients,
                new NamingThreadsInPool("Поток клиента"));

        for (Thread thread : threadsClient) {
            firstExecutorService.submit(thread);
        }
        firstExecutorService.shutdown();

        //передаем защелки в методы бэксистем
        CountDownLatch countDownLatch = new CountDownLatch(threadsClient.size());;
        backSystem.setCountDownLatch(countDownLatch);

        List<Thread> threadsHandler = new ArrayList<>();
        for (int i = 0; i < numberHandlers; i++) {
            threadsHandler.add(new Thread(new Handler(frontSystem, backSystem)));
        }

        //создаем пул с именованнми потоками для обработчиков
        ExecutorService secondExecutorService = Executors.newFixedThreadPool(numberHandlers,
                new NamingThreadsInPool("Обработчик заявок"));
        for (Thread thread : threadsHandler) {
            secondExecutorService.submit(thread);
        }
        secondExecutorService.shutdown();

        //ждем в основном потоке пока все защелки откроются
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().isInterrupted();
        }

        secondExecutorService.shutdownNow();

    }
}
