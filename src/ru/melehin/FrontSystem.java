package ru.melehin;

import java.util.LinkedList;
import java.util.Queue;

public class FrontSystem {

    private Queue<Order> orders = new LinkedList<>();

    public Queue<Order> getOrders() {
        return orders;
    }

    public void setOrders(Queue<Order> orders) {
        this.orders = orders;
    }

    public synchronized void addOrder(Order order) throws InterruptedException {
        //while(orders.isEmpty())
        orders.add(order);
        System.out.println(order.getName() + ": Заявка " + order + "отправлена в банк");
        notify();

    }

    public synchronized Order getOrder() throws InterruptedException {

        while(getOrders().isEmpty()) {
            wait();
        }

        Order order = orders.poll();
        return order;
    }
}
