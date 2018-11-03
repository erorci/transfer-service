package io.emersonorsi.transferservice.transfer;

import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateTransferRequest {

    private final String accountTo;
    private final Double amount;

    @JsonCreator
    public CreateTransferRequest(@JsonProperty(value = "accountTo", required = true) String accountTo,
                                 @JsonProperty(value = "amount", required = true) Double amount) {
        Assert.notNull(accountTo, "AccountTo must not be null");
        Assert.notNull(amount, "Amount must not be null");
        this.accountTo = accountTo;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "CreateTransferRequest{" +
                "accountTo='" + accountTo + '\'' +
                ", amount=" + amount +
                '}';
    }

    public String getAccountTo() {
        return accountTo;
    }

    public Double getAmount() {
        return  amount;
    }
}
