package ru.meleshin;

public class Client implements Runnable {

    private final FrontSystem frontSystem;
    Request request;

    public Client(FrontSystem frontSystem, Request request) {
        this.frontSystem = frontSystem;
        this.request = request;
    }


    @Override
    public void run() {
        try {
            frontSystem.addRequest(request);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
