package ru.meleshin;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


public class FrontSystem {

//    BlockingQueue ������ ������ �������� �������� ��������� ����� ������� ���������
//    ��� ��������� � ������ ����� ��� ����� ������ � ��������� �������������.
//    ������� �������� (put) ������� � ������ ������� �������� � ������������ ������ ������;
//    ������� ������� (take) ������� �� ������ ������� ����� ��������� �����.

    private BlockingQueue<Request> requests = new ArrayBlockingQueue<>(2,true);


    public Queue<Request> getRequests() {
        return requests;
    }

    public void addRequest(Request request) throws InterruptedException {
        System.out.println(request.getName() + ": ������ " + request + "���������� � ����");
        requests.put(request);

    }

    public Request getRequest() throws InterruptedException {
        Request request = requests.take();
        System.out.println(Thread.currentThread().getName() + ": �������� ������ �� ��������� �� ������� - "
                + request.getName());

        return request;

    }
}
