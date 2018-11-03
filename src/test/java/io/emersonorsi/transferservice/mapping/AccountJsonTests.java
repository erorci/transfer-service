package io.emersonorsi.transferservice.mapping;

import java.io.IOException;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@JsonTest
public class AccountJsonTests {

    @Autowired
    private JacksonTester<Account> json;

    @Test
    public void testSerialize() throws IOException {
        Account account = Account.createFor("Bessie Mcdonnell");
        account.setAccountBalance(AccountBalance.of(10000.0));

        System.out.println(json.write(account));

        Assertions.assertThat(json.write(account))
                .hasEmptyJsonPathValue("$.accountId");

        Assertions.assertThat(json.write(account))
                .extractingJsonPathValue("$.accountOwner").isEqualTo("Bessie Mcdonnell");

        Assertions.assertThat(json.write(account))
                .extractingJsonPathValue("$.accountBalance.value").isEqualTo("10000.0");

        Assertions.assertThat(json.write(account))
                .extractingJsonPathValue("$.currentCurrency").isNotNull();
    }
}
