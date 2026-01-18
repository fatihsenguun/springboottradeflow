package com.fatihsengun.controller.impl;

import com.fatihsengun.controller.IRestWalletController;
import com.fatihsengun.controller.RestRootResponseController;
import com.fatihsengun.dto.DtoWallet;
import com.fatihsengun.dto.DtoWalletUI;
import com.fatihsengun.entity.RootResponseEntity;
import com.fatihsengun.service.impl.WalletServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/rest/api/wallet")
public class RestWalletController extends RestRootResponseController implements IRestWalletController {


    @Autowired
    private WalletServiceImpl walletService;


    @Override
    @PostMapping("/deposit")
    public RootResponseEntity<DtoWallet> deposit(@Valid @RequestBody BigDecimal amount) {
        return ok(walletService.deposit(amount));
    }

    @Override
    @GetMapping("/my-wallet")
    public RootResponseEntity<DtoWallet> getMyWallet() {

        return ok(walletService.getMyWallet());
    }
}
