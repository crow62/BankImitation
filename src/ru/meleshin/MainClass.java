package ru.meleshin;

public class MainClass {

    public static void main(String[] args) throws InterruptedException {

        FrontSystem frontSystem = new FrontSystem();
        BackSystem backSystem = new BackSystem();

        ClientProvider clientProvider = new ClientProvider(frontSystem);

        clientProvider.sendOrder("Client1", 5000, Order.Type.CREDIT);
        clientProvider.sendOrder("Client2", 10000, Order.Type.REPAYMENT);
        clientProvider.sendOrder("Client3", 20000, Order.Type.REPAYMENT);
        clientProvider.sendOrder("Client4", 150000, Order.Type.CREDIT);
        clientProvider.sendOrder("Client5", 15000, Order.Type.REPAYMENT);

        Handler handler = new Handler(frontSystem, backSystem);
        handler.handleOrder(1);
        handler.handleOrder(2);

    }
}
