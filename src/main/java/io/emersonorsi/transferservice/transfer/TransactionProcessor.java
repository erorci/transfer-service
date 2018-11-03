package io.emersonorsi.transferservice.transfer;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
public class TransactionProcessor {

    private final TransactionService transactionService;

    public TransactionProcessor(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Async
    @TransactionalEventListener
    void processTransaction(TransactionRequested event) {
        System.out.println("New Event: " + event.toString());
        transactionService.process(event.transaction().id());
    }
}
