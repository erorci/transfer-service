package io.emersonorsi.transferservice.transfer;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.emersonorsi.transferservice.mapping.Account;
import io.emersonorsi.transferservice.mapping.AccountRepository;

@Service
public class TransactionService {

    private final AccountRepository accountRepository;

    private final TransactionRepository transactionRepository;

    public TransactionService(AccountRepository accountRepository,
                              TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public Transaction process(String transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new IllegalArgumentException("Transaction to transfer between accounts could not be found"));

        Account account = accountRepository
                .findById(transaction.accountIdTo())
                .orElseThrow(() -> new IllegalArgumentException("Account to Deposit could not be found"));

        account.deposit(transaction.amount()).saveTo(accountRepository::save);
        return transaction.markFinished().saveTo(transactionRepository::save);
    }
}
