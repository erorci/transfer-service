package io.emersonorsi.transferservice.transfer;

public class TransactionRequested {
    private final Transaction transaction;

    private TransactionRequested(Transaction transaction) {
        this.transaction = transaction;
    }

    public static TransactionRequested of(Transaction transaction) {
        return new TransactionRequested(transaction);
    }

    public Transaction transaction() {
        return transaction;
    }

    @Override
    public String toString() {
        return "TransactionRequested{" +
                "transaction=" + transaction +
                '}';
    }
}
