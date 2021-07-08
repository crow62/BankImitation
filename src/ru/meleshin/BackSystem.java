package ru.meleshin;

public class BackSystem {

    private volatile int deposit;

    public int getDeposit() {
        return deposit;
    }

    public synchronized void takeLoan(Order order, int credit) {
        if (credit > getDeposit()) {
            throw new RuntimeException("��� �������: ������ " + order + "�� ��������� ." +
                    " �������� �� ���������� ������ �2. ����� ������ ������� �����." +
                    " ������ ����� - " + getDeposit());
        } else {
            deposit = deposit - credit;
            printMessageSuccess(order);
        }
    }

    public synchronized void repayLoan(Order order, int repayment) {
        deposit = deposit + repayment;
        printMessageSuccess(order);
    }

    private void printMessageSuccess(Order order) {
            System.out.println("��� �������: ������ " + order + "������� ��������� ." +
                    " �������� �� ���������� ������ �2. ������ ����� - " + getDeposit());

    }

}
