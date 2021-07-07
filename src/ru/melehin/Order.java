package ru.melehin;

public class Order {

    private String name;
    private int amount;
    private Type type;


    public enum Type {
        CREDIT,REPAYMENT
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }


    public Order(String name, int amount, Type type) {
        this.name = name;
        this.amount = amount;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Order{" +
                "name='" + name + '\'' +
                ", amount=" + amount +
                ", type=" + type +
                '}';
    }
}
