package io.emersonorsi.transferservice.mapping;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    private final AccountRepository accountRepository;

    public AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @PostMapping
    ResponseEntity<Account> postAccount(@RequestBody CreateAccountRequest request) {
        System.out.println("New Request:" + request);
        Account account = request
                .retrieveDataAndBuildAccount().saveTo(accountRepository::save);
        return ResponseEntity.ok().body(account);
    }

    @GetMapping
    ResponseEntity<List<Account>> getAllAccounts() {
        return ResponseEntity.ok(accountRepository.findAll());
    }
}
