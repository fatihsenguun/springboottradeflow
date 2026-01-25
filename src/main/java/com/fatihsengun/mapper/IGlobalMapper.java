package com.fatihsengun.mapper;

import com.fatihsengun.dto.*;
import com.fatihsengun.entity.*;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IGlobalMapper {
    User toUserEntity(DtoRegisterUI dtoRegisterUI);

    DtoRegister toDtoRegister(User user);

    Product toProductEntity(DtoProductUI dtoProductUI);

    DtoProduct toDtoProduct(Product product);

    List<Product> toListProductEntity(List<DtoProductUI> dtoProductUI);

    List<DtoProduct> toListDtoProduct(List<Product> products);

    Category toCategoryEntity(DtoCategoryUI dtoCategoryUI);

    DtoCategory toDtoCategory(Category category);

    Order toOrderEntity(DtoOrderUI dtoOrderUI);

    DtoOrder toDtoOrder(Order order);

    Wallet toWalletEntity(DtoWalletUI dtoWalletUI);

    DtoWallet toDtoWallet(Wallet wallet);


}
