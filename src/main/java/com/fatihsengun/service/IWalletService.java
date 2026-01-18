package com.fatihsengun.service;

import com.fatihsengun.dto.DtoWallet;

import java.math.BigDecimal;

public interface IWalletService {

    public DtoWallet deposit(BigDecimal amount);

    public DtoWallet getMyWallet();
}
