package io.emersonorsi.transferservice.transfer;

import java.util.function.UnaryOperator;

import org.springframework.data.annotation.Id;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.util.Assert;

public class Transaction extends AbstractAggregateRoot {

    @Id
    private String transactionId;

    private String accountIdFrom;

    private String accountIdTo;

    private Double amount;

    private TransactionStatus status;

    private Transaction(String accountIdFrom, String accountIdTo, Double amount) {
        Assert.notNull(accountIdFrom, "AccountIdFrom must not be null");
        Assert.notNull(accountIdTo, "AccountIdTo must not be null");
        Assert.notNull(amount, "Amount must not be null");
        this.accountIdFrom = accountIdFrom;
        this.accountIdTo = accountIdTo;
        this.amount = amount;
    }

    public Transaction markRequested() {
        this.status = TransactionStatus.REQUESTED;
        registerEvent(TransactionRequested.of(this));
        return this;
    }

    public Transaction markFinished() {
        this.status = TransactionStatus.FINISHED;
        System.out.println("Transaction: " + this.toString());
        return this;
    }

    public static Transaction buildWith(String accountIdFrom, String accountIdTo, Double amount) {
        return new Transaction(accountIdFrom, accountIdTo, amount);
    }

    public Transaction saveTo(UnaryOperator<Transaction> saveFunc) {
        return saveFunc.apply(this);
    }

    public String accountIdFrom() {
        return accountIdFrom;
    }

    public String accountIdTo() {
        return accountIdTo;
    }

    public Double amount() {
        return amount;
    }

    public String id() {
        return transactionId;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId='" + transactionId + '\'' +
                ", accountIdFrom='" + accountIdFrom + '\'' +
                ", accountIdTo='" + accountIdTo + '\'' +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                '}';
    }
}

