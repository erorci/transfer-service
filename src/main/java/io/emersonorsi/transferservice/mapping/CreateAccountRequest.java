package io.emersonorsi.transferservice.mapping;

import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateAccountRequest {

    private final String owner;

    private final Double initialBalance;

    @JsonCreator
    public CreateAccountRequest(@JsonProperty(value="accountOwner", required = true) String owner,
        @JsonProperty(value = "initialBalance", required = true) Double initialBalance) {
        Assert.notNull(owner, "Owner must not be null");
        Assert.notNull(initialBalance, "InitialBalance must not be null");
        this.owner = owner;
        this.initialBalance = initialBalance;
    }

    public Account retrieveDataAndBuildAccount() {
        return Account
                .createFor(this.owner)
                .setAccountBalance(AccountBalance.of(this.initialBalance));
    }

    @Override
    public String toString() {
        return "CreateAccountRequest{ " +
                "owner=" + owner +
                ",initialBalance=" + initialBalance + "}";
    }
}
