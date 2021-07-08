package ru.meleshin;

public class ClientProvider {

    private final FrontSystem frontSystem;

    public ClientProvider(FrontSystem frontSystem) {
        this.frontSystem = frontSystem;
    }

    public void sendOrder(String name, int amount, Order.Type type)  {
        ThreadClient threadClient = new ThreadClient("thread"+ name);
        threadClient.initOrder(name,amount,type);
        threadClient.start();
    }

    public class ThreadClient extends Thread {

        Order order;

        void initOrder (String name, int amount, Order.Type type) {
            order = new Order(name, amount, type);
        }

        public ThreadClient(String name) {
            super(name);
        }

        @Override
        public void run() {
                try {
                    frontSystem.addOrder(order);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }
    }
}
