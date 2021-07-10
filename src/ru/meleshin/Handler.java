package ru.meleshin;

public class Handler implements Runnable {

    private final FrontSystem frontSystem;
    private final BackSystem backSystem;

    public Handler(FrontSystem frontSystem, BackSystem backSystem) {
        this.frontSystem = frontSystem;
        this.backSystem = backSystem;
    }


    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            Request request = frontSystem.getRequest();
                if (request != null) {
                    if (request.getType() == Request.Type.CREDIT) {
                        try {
                            backSystem.takeLoan(request, request.getAmount());
                        } catch (RuntimeException exception) {
                            System.out.println(exception.getMessage());
                        }
                    } else {
                        backSystem.repayLoan(request, request.getAmount());
                    }
                }

        }
    }
}
