package com.fatihsengun.mapper;

import com.fatihsengun.dto.*;
import com.fatihsengun.entity.*;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IGlobalMapper {


    default <T> DtoPage<T> toDtoPage(Page<T> page) {
        if (page == null) {
            return null;
        }
        return new DtoPage<>(page);
    }

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

    DtoOrderItem toDtoOrderItem(OrderItem orderItem);

    DtoFavorite toDtoFavorite(Favorite favorite);


}
