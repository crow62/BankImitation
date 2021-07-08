package ru.meleshin;

import java.util.LinkedList;
import java.util.Queue;

public class FrontSystem {

    private Queue<Order> orders = new LinkedList<>();

    public Queue<Order> getOrders() {
        return orders;
    }

    public synchronized void addOrder(Order order) throws InterruptedException {
        System.out.println(order.getName() + ": ������ " + order + "���������� � ����");
        while (getOrders().size()==2){
            wait();
        }
        orders.add(order);
        notify();
    }

    public synchronized Order getOrder(int numberHandler) throws InterruptedException {

        while(getOrders().isEmpty()) {
            wait();
        }

        Order order = orders.poll();
        notify();
        System.out.println("���������� ������ � " + numberHandler +": �������� ������ �� ��������� �� ������� - "
                + order.getName());
        return order;
    }
}
