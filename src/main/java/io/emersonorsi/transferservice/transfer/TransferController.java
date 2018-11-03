package io.emersonorsi.transferservice.transfer;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account/{id}/transfer")
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping
    public ResponseEntity<Void> postTransfer(@PathVariable("id") String accountIdFrom,
                                             @RequestBody CreateTransferRequest request) {

        Transaction transaction = transferService.transferAmount(accountIdFrom, request);
        return ResponseEntity.created(URI.create("/api/transaction/" + transaction.id())).build();
    }
}
