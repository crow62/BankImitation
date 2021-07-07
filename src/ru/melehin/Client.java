package ru.melehin;

public class Client {

    FrontSystem frontSystem;
    BackSystem backSystem;

    public Client(FrontSystem frontSystem, BackSystem backSystem) {
        this.frontSystem = frontSystem;
        this.backSystem = backSystem;
    }

    public void buildOrder(String name, int amount, Order.Type type) throws InterruptedException {
        ThreadClient threadClient = new ThreadClient("thread"+ name);
        threadClient.initOrder(name,amount,type);
        threadClient.start();

    }


    public class ThreadClient extends Thread {

        Order order1;

        void initOrder (String name, int amount, Order.Type type) {
            order1 = new Order(name, amount, type);
        }

        public ThreadClient(String s) {
        }

        @Override
        public void run() {
                try {
                    frontSystem.addOrder(order1);
                    System.out.println(order1.getName() + ": Заявка " + order1 + "отправлена в банк");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

        }
    }

}
