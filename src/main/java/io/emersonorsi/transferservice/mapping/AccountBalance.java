package io.emersonorsi.transferservice.mapping;

import java.io.IOException;

import org.springframework.util.Assert;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = AccountBalanceJson.Serializer.class)
public class AccountBalance {

    public static final AccountBalance ZERO = AccountBalance.of(0.0);

    private final Double value;

    public AccountBalance(Double value) {
        Assert.isTrue(value >= 0, "Account could not have a negative balance");
        this.value = value;
    }

    public static AccountBalance of(Double value) {
        return new AccountBalance(value);
    }

    public boolean hasFundToTransfer(Double amount) {
        return (this.value - amount) >=0 ? true : false;
    }

    public AccountBalance withdraw(Double amount) {
        return of(this.value - amount);
    }

    public AccountBalance deposit(Double amount) {
        return of(this.value + amount);
    }

    public Double value() {
        return value;
    }

    public void writeJson(JsonGenerator jsonGenerator) throws IOException {
        jsonGenerator.writeStringField("value", value.toString());
    }
}
