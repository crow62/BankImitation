package ru.melehin;

public class Handler {

    FrontSystem frontSystem;
    BackSystem backSystem;

    public Handler(FrontSystem frontSystem, BackSystem backSystem) {
        this.frontSystem = frontSystem;
        this.backSystem = backSystem;
    }

    public void handleOrder() {

        Thread thread1 = new Thread(new Runnable() {

            @Override
            public void run() {
                Order firstOrder = null;
                while (true) {
                    try {
                        firstOrder = frontSystem.getOrder();
                        System.out.println("���������� ������ �1: �������� ������ �� ��������� �� ������� - "
                                + firstOrder.getName());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (firstOrder.getType() == Order.Type.CREDIT) {
                        try {
                            backSystem.takeLoan(firstOrder.getAmount());
                            printMessageSuccess(firstOrder);
                        } catch (Exception exception) {
                            printMessageFail(firstOrder);
                        }

                    } else {
                        backSystem.repayLoan(firstOrder.getAmount());
                        printMessageSuccess(firstOrder);
                    }
                }

            }
        });

        Thread thread2 = new Thread(new Runnable() {

            @Override
            public void run() {
                Order secondOrder = null;
                while (true) {
                    try {
                        secondOrder = frontSystem.getOrder();
                        System.out.println("���������� ������ �2: �������� ������ �� ��������� �� ������� - "
                                + secondOrder.getName());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (secondOrder.getType() == Order.Type.CREDIT) {
                        try {
                            backSystem.takeLoan(secondOrder.getAmount());
                            printMessageSuccess(secondOrder);
                        } catch (Exception exception) {
                            printMessageFail(secondOrder);
                        }

                    } else {
                        backSystem.repayLoan(secondOrder.getAmount());
                        printMessageSuccess(secondOrder);
                    }
                }

            }
        });

        thread1.start();
        thread2.start();

    }

    private void printMessageSuccess(Order order) {
        System.out.println("��� �������: ������ " + order + "������� �������� ." +
                " �������� �� ���������� ������ �2. ������ ����� - " + backSystem.getAmount());
    }

    private void printMessageFail (Order order) {
        System.out.println("��� �������: ������ " + order + "�� �������� ." +
                " �������� �� ���������� ������ �2. ����� ������ ������� �����." +
                " ������ ����� - " + backSystem.getAmount());

    }


}
