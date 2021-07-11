package ru.meleshin;

import java.util.*;
import java.util.concurrent.*;

public class MainClass {

    public static void main(String[] args) {

        System.out.println("������� ���������� ������� �����...");

        FrontSystem frontSystem = new FrontSystem();
        BackSystem backSystem = new BackSystem();

        System.err.println("\n������ ����� - " + backSystem.getBalance());
        Scanner scanner = new Scanner(System.in);


        System.err.println("������� ���������� ��������: ");
        int numberClients;
        numberClients = scanner.nextInt();

        System.err.println("������� ���������� ������������: ");
        int numberHandlers;
        numberHandlers = scanner.nextInt();

        Random random = new Random();

        List<Thread> threadsClient = new ArrayList<>();
        for (int i = 0; i < numberClients; i++) {
            threadsClient.add(new Thread(new Client(frontSystem, new Request
                    ("������" + (i + 1), 5000 * (1 + random.nextInt(10)),
                            Request.Type.values()[random.nextInt(2)]))));
        }

        //������� ��� � ����������� �������� ��� ��������
        ExecutorService firstExecutorService = Executors.newFixedThreadPool(numberClients,
                new NamingThreadsInPool("����� �������"));

        for (Thread thread : threadsClient) {
            firstExecutorService.submit(thread);
        }
        firstExecutorService.shutdown();

        //�������� ������� � ������ ���������
        CountDownLatch countDownLatch = new CountDownLatch(threadsClient.size());;
        backSystem.setCountDownLatch(countDownLatch);

        List<Thread> threadsHandler = new ArrayList<>();
        for (int i = 0; i < numberHandlers; i++) {
            threadsHandler.add(new Thread(new Handler(frontSystem, backSystem)));
        }

        //������� ��� � ����������� �������� ��� ������������
        ExecutorService secondExecutorService = Executors.newFixedThreadPool(numberHandlers,
                new NamingThreadsInPool("���������� ������"));
        for (Thread thread : threadsHandler) {
            secondExecutorService.submit(thread);
        }
        secondExecutorService.shutdown();

        //���� � �������� ������ ���� ��� ������� ���������
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().isInterrupted();
        }

        secondExecutorService.shutdownNow();

    }
}
