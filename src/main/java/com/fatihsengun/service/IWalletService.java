package com.fatihsengun.service;

import com.fatihsengun.dto.DtoDeposit;
import com.fatihsengun.dto.DtoWallet;

import java.math.BigDecimal;

public interface IWalletService {

    public DtoWallet deposit(DtoDeposit dtoDeposit);

    public DtoWallet getMyWallet();
}
