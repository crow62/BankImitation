package ru.meleshin;

import java.util.LinkedList;
import java.util.Queue;

public class FrontSystem {

    private Queue<Request> requests = new LinkedList<>();

    public Queue<Request> getRequests() {
        return requests;
    }

    public synchronized void addRequest(Request request) throws InterruptedException {

        while (getRequests().size() == 2) {
            wait();
        }
        requests.add(request);
        System.out.println(request.getName() + ": Заявка " + request + "отправлена в банк");

    }

    public synchronized Request getRequest() {

        try {
            Request request = requests.poll();
            System.out.println(Thread.currentThread().getName() + ": получена заявка на обработку по клиенту - "
                    + request.getName());
            return request;
        } catch (NullPointerException exception) {
            return null;
        } finally {
            notifyAll();
        }


    }
}
