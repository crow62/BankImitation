package ru.meleshin;

public class Handler {

    private final FrontSystem frontSystem;
    private final BackSystem backSystem;

    public Handler(FrontSystem frontSystem, BackSystem backSystem) {
        this.frontSystem = frontSystem;
        this.backSystem = backSystem;
    }

    public void handleOrder(int numberHandler) {
        ThreadHandler threadHandler = new ThreadHandler();
        threadHandler.setDaemon(true);
        threadHandler.initHandler(numberHandler);
        threadHandler.start();
    }

    public class ThreadHandler extends Thread {

        int numberHandler;

        public void initHandler(int numberHandler) {
            this.numberHandler = numberHandler;
        }

        @Override
        public void run() {
            Order order = null;
            while (true) {
                try {
                    order = frontSystem.getOrder(numberHandler);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (order.getType() == Order.Type.CREDIT) {
                    try {
                        backSystem.takeLoan(order, order.getAmount());
                    } catch (RuntimeException exception) {
                        System.out.println(exception.getMessage());
                    }
                } else {
                    backSystem.repayLoan(order, order.getAmount());
                }
            }
        }
    }

}
