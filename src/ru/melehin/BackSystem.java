package ru.melehin;

public class BackSystem {

    private volatile int deposit;

    public int getDeposit() {
        return deposit;
    }

    public synchronized void takeLoan(int credit) {
        if (credit > deposit) {
            throw new RuntimeException();
        } else {
            deposit = deposit - credit;
        }
    }

    public synchronized void repayLoan(int repayment) {
        deposit = deposit + repayment;
    }

}
