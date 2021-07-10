package ru.meleshin;

import java.util.Locale;

public class BackSystem {

    private volatile int balance;
    private volatile int counter;

    public int getBalance() {
        return balance;
    }

    public int getCounter() {
        return counter;
    }

    public synchronized void takeLoan(Request request, int credit) {
        counter++;
        if (credit > getBalance()) {
            throw new RuntimeException("Бэк система: ЗАЯВКА " + request + "НЕ ВЫПОЛНЕНА ." +
                    " Получена от " + Thread.currentThread().getName().toLowerCase(Locale.ROOT) +
                    ". Сумма больше баланса банка. Баланс банка - " + getBalance());
        } else {
            balance = balance - credit;
            printMessageSuccess(request);
        }
    }

    public synchronized void repayLoan(Request request, int repayment) {
        counter++;
        balance = balance + repayment;
        printMessageSuccess(request);

    }

    private void printMessageSuccess(Request request) {

        System.out.println("Бэк система: ЗАЯВКА " + request + "УСПЕШНО ВЫПОЛНЕНА ." +
                " Получена от " + Thread.currentThread().getName().toLowerCase(Locale.ROOT) +
                ". Баланс банка - " + getBalance());

    }

}
