package io.emersonorsi.transferservice.mapping;

import java.io.IOException;
import java.util.Currency;
import java.util.function.UnaryOperator;

import org.springframework.data.annotation.Id;
import org.springframework.util.Assert;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = AccountJson.Serializer.class)
public class Account {

    @Id
    private String accountId;

    private String owner;

    private AccountBalance accountBalance;

    private Currency currency = Currency.getInstance("EUR");

    private Account(String owner) {
        Assert.notNull(owner, "Owner must not be null");
        this.owner = owner;
    }

    public static Account createFor(String owner) {
        return new Account(owner);
    }

    public Account setAccountBalance(AccountBalance accountBalance) {
        this.accountBalance = accountBalance;
        return this;
    }

    public void writeJson(JsonGenerator jsonGenerator) throws IOException {
        jsonGenerator.writeStringField("accountId", accountId);
        jsonGenerator.writeStringField("accountOwner", owner);
        jsonGenerator.writeStringField("currentCurrency", currency.getDisplayName());
        if (accountBalance != null) {
            jsonGenerator.writeObjectFieldStart("accountBalance");
            accountBalance.writeJson(jsonGenerator);
            jsonGenerator.writeEndObject();
        }
    }

    public boolean balanceIsEnough(Double amountToTransfer) {
        return this.accountBalance.hasFundToTransfer(amountToTransfer);
    }

    public Account withdraw(Double amount) {
        this.accountBalance = accountBalance.withdraw(amount);
        return this;
    }

    public Account deposit(Double amount) {
        this.accountBalance = accountBalance.deposit(amount);
        return this;
    }

    public Account saveTo(UnaryOperator<Account> saveFunc) {
        return saveFunc.apply(this);
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", owner='" + owner + '\'' +
                ", accountBalance=" + accountBalance +
                ", currency=" + currency +
                '}';
    }
}
