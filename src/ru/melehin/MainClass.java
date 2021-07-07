package ru.melehin;

public class MainClass {
    public static void main(String[] args) throws InterruptedException {

        FrontSystem frontSystem = new FrontSystem();
        BackSystem backSystem = new BackSystem();

        Client client = new Client(frontSystem, backSystem);
        client.buildOrder("Client1", 5000, Order.Type.CREDIT);
        client.buildOrder("Client2", 10000, Order.Type.REPAYMENT);
        client.buildOrder("Client3", 20000, Order.Type.REPAYMENT);
        client.buildOrder("Client4", 150000, Order.Type.CREDIT);
        client.buildOrder("Client5", 15000, Order.Type.REPAYMENT);


        Handler handler = new Handler(frontSystem, backSystem);
        handler.handleOrder(1);
        handler.handleOrder(2);
    }
}
