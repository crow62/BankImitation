package ru.meleshin;

public class BackSystem {

    private volatile int deposit;

    public int getDeposit() {
        return deposit;
    }

    public synchronized void takeLoan(Order order, int credit) {
        if (credit > getDeposit()) {
            throw new RuntimeException("Бэк система: ЗАЯВКА " + order + "НЕ ВЫПОЛНЕНА ." +
                    " Получена от обработчик заявок №2. Сумма больше баланса банка." +
                    " Баланс банка - " + getDeposit());
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
            System.out.println("Бэк система: ЗАЯВКА " + order + "УСПЕШНО ВЫПОЛНЕНА ." +
                    " Получена от обработчик заявок №2. Баланс банка - " + getDeposit());

    }

}
