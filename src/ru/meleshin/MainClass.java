package ru.meleshin;

import java.util.Arrays;
import java.util.List;

public class MainClass {

    public static void main(String[] args) throws InterruptedException {

        FrontSystem frontSystem = new FrontSystem();
        BackSystem backSystem = new BackSystem();

        Thread thread1 = new Thread(new Client(frontSystem,new Request("������1", 5000, Request.Type.CREDIT)));
        Thread thread2 = new Thread(new Client(frontSystem,new Request("������2", 10000, Request.Type.REPAYMENT)));
        Thread thread3 = new Thread(new Client(frontSystem,new Request("������3", 20000, Request.Type.REPAYMENT)));
        Thread thread4 = new Thread(new Client(frontSystem,new Request("������4", 150000, Request.Type.CREDIT)));
        Thread thread5 = new Thread(new Client(frontSystem,new Request("������5", 15000, Request.Type.REPAYMENT)));


        List<Thread> threadsClient = Arrays.asList(thread1, thread2, thread3, thread4, thread5);
        for (Thread thread: threadsClient) {
            thread.start();
        }

        Thread.sleep(1000);

        Thread firstThreadHandler = new Thread(new Handler(frontSystem,backSystem),"���������� ������ �1" );
        Thread secondThreadHandler = new Thread(new Handler(frontSystem,backSystem), "���������� ������ �2");

        List<Thread> threadsHandler = Arrays.asList(firstThreadHandler, secondThreadHandler);
        for (Thread thread: threadsHandler) {
            thread.start();
        }

        while (true) {
            if (threadsClient.size() == backSystem.getCounter()) {
                for (Thread thread : threadsHandler) {
                    thread.interrupt();
                }
                break;
            }
        }

    }
}
