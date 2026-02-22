package com.fatihsengun.controller;

import com.fatihsengun.dto.DtoDeposit;
import com.fatihsengun.dto.DtoWallet;
import com.fatihsengun.dto.DtoWalletUI;
import com.fatihsengun.entity.RootResponseEntity;

import java.math.BigDecimal;

public interface IRestWalletController {

    public RootResponseEntity<DtoWallet> deposit(DtoDeposit dtoDeposit);

    public RootResponseEntity<DtoWallet> getMyWallet();


}
