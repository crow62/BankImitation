package ru.melehin;

public class Handler {

    FrontSystem frontSystem;
    BackSystem backSystem;

    public Handler(FrontSystem frontSystem, BackSystem backSystem) {
        this.frontSystem = frontSystem;
        this.backSystem = backSystem;
    }

    public void handleOrder(int i) {

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                Order order = null;
                while (true) {
                    try {
                        order = frontSystem.getOrder();
                        System.out.println("���������� ������ � " + i +": �������� ������ �� ��������� �� ������� - "
                                + order.getName());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (order.getType() == Order.Type.CREDIT) {
                        try {
                            backSystem.takeLoan(order.getAmount());
                            printMessageSuccess(order);
                        } catch (Exception exception) {
                            printMessageFail(order);
                        }

                    } else {
                        backSystem.repayLoan(order.getAmount());
                        printMessageSuccess(order);
                    }
                }

            }
        });

        thread.start();

    }

    private void printMessageSuccess(Order order) {
        System.out.println("��� �������: ������ " + order + "������� �������� ." +
                " �������� �� ���������� ������ �2. ������ ����� - " + backSystem.getDeposit());
    }

    private void printMessageFail (Order order) {
        System.out.println("��� �������: ������ " + order + "�� �������� ." +
                " �������� �� ���������� ������ �2. ����� ������ ������� �����." +
                " ������ ����� - " + backSystem.getDeposit());

    }


}
