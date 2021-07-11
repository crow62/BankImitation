package ru.meleshin;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


public class FrontSystem {

//    BlockingQueue изящно решает проблему передачи собранных одним потоком элементов
//    для обработки в другой поток без явных хлопот о проблемах синхронизации.
//    Попытки вставить (put) элемент в полную очередь приведет к блокированию работы потока;
//    попытка извлечь (take) элемент из пустой очереди также блокирует поток.

    private BlockingQueue<Request> requests = new ArrayBlockingQueue<>(2,true);


    public Queue<Request> getRequests() {
        return requests;
    }

    public void addRequest(Request request) throws InterruptedException {
        System.out.println(request.getName() + ": Заявка " + request + "отправлена в банк");
        requests.put(request);

    }

    public Request getRequest() throws InterruptedException {
        Request request = requests.take();
        System.out.println(Thread.currentThread().getName() + ": получена заявка на обработку по клиенту - "
                + request.getName());

        return request;

    }
}
