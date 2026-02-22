package com.fatihsengun.service.impl;

import com.fatihsengun.dto.DtoDeposit;
import com.fatihsengun.dto.DtoWallet;
import com.fatihsengun.entity.User;
import com.fatihsengun.entity.Wallet;
import com.fatihsengun.exception.BaseException;
import com.fatihsengun.exception.ErrorMessage;
import com.fatihsengun.exception.MessageType;
import com.fatihsengun.mapper.IGlobalMapper;
import com.fatihsengun.repository.WalletRepository;
import com.fatihsengun.service.IWalletService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;

@Service
public class WalletServiceImpl implements IWalletService {


    @Autowired
    private IdentityService identityService;

    @Autowired
    private IGlobalMapper globalMapper;

    @Autowired
    private WalletRepository walletRepository;

    @Override
    @Transactional
    public DtoWallet deposit(DtoDeposit dtoDeposit) {

        if (dtoDeposit.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "The uploaded amount must be greater than 0!"));
        }
        User user = identityService.getCurrentUser();
        Wallet wallet = user.getWallet();

        BigDecimal newBalance = wallet.getBalance().add(dtoDeposit.getAmount());
        wallet.setBalance(newBalance);

        return globalMapper.toDtoWallet(walletRepository.save(wallet));
    }

    @Override
    public DtoWallet getMyWallet() {
        User user = identityService.getCurrentUser();
        return globalMapper.toDtoWallet(user.getWallet());

    }
}
