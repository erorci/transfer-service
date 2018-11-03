package io.emersonorsi.transferservice.transfer;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.emersonorsi.transferservice.mapping.Account;
import io.emersonorsi.transferservice.mapping.AccountRepository;

@Service
public class TransferService {

    private final AccountRepository accountRepository;

    private final TransactionRepository transactionRepository;

    public TransferService(AccountRepository accountRepository,
                           TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional()
    public Transaction transferAmount(String accountIdFrom, CreateTransferRequest request) {

        Account account = accountRepository
                .findById(accountIdFrom)
                .orElseThrow(() -> new IllegalArgumentException("Account could not be found"));

        if (!account.balanceIsEnough(request.getAmount())) {
            throw new IllegalStateException("Account hasn't enough balance to transfer the amount");
        }

        account.withdraw(request.getAmount()).saveTo(accountRepository::save);

        return Transaction
                .buildWith(accountIdFrom, request.getAccountTo(), request.getAmount())
                .markRequested().saveTo(transactionRepository::save);
    }
}
