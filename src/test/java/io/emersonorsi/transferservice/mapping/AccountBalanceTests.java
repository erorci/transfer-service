package io.emersonorsi.transferservice.mapping;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class AccountBalanceTests {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void hasFundToTransferWhenBalanceValueIsEnoughShouldReturnTrue() {
        Account account = Account.createFor("Bessie Mcdonnell");
        account.setAccountBalance(AccountBalance.of(1000.0));
        Double amountToTest = 999.99;
        assertThat(account.balanceIsEnough(amountToTest)).isTrue();
    }

    @Test
    public void hasFundToTransferWhenBalanceValueIsNotEnoughShouldReturnFalse() {
        Account account = Account.createFor("Bessie Mcdonnell");
        account.setAccountBalance(AccountBalance.of(1000.0));
        Double amountToTest = 1000.01;
        assertThat(account.balanceIsEnough(amountToTest)).isFalse();
    }

    @Test
    public void withdrawWhenBalanceValueIsEnoughShouldDecreaseAccountBalance() {
        Account account = Account.createFor("Bessie Mcdonnell");
        AccountBalance initialBalance = AccountBalance.of(1000.0);
        account.setAccountBalance(initialBalance);
        Double amountToWithdraw = 100.0;
        assertThat(account.withdraw(amountToWithdraw).balance()).isEqualTo(initialBalance.value() - amountToWithdraw);
    }

    @Test
    public void depositAValueShouldIncreaseAccountBalance() {
        Account account = Account.createFor("Bessie Mcdonnell");
        AccountBalance initialBalance = AccountBalance.of(1000.0);
        account.setAccountBalance(initialBalance);
        Double amountToDeposit = 100.0;
        assertThat(account.deposit(amountToDeposit).balance()).isEqualTo(initialBalance.value() + amountToDeposit);
    }

    @Test
    public void withdrawWhenBalanceValueIsNotEnoughShouldReturnAException() {
        Account account = Account.createFor("Bessie Mcdonnell");
        AccountBalance initialBalance = AccountBalance.of(1000.0);
        account.setAccountBalance(initialBalance);
        Double amountToWithdraw = 1000.01;
        this.thrown.expect(IllegalArgumentException.class);
        this.thrown.expectMessage("Account could not have a negative balance");
        account.withdraw(amountToWithdraw).balance();
    }
}
