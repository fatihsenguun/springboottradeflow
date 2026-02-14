package com.fatihsengun.service.impl;

import com.fatihsengun.dto.DtoFavorite;
import com.fatihsengun.dto.DtoFavoriteIU;
import com.fatihsengun.dto.DtoProduct;
import com.fatihsengun.entity.Favorite;
import com.fatihsengun.entity.Product;
import com.fatihsengun.entity.User;
import com.fatihsengun.exception.BaseException;
import com.fatihsengun.exception.ErrorMessage;
import com.fatihsengun.exception.MessageType;
import com.fatihsengun.mapper.IGlobalMapper;
import com.fatihsengun.repository.FavoriteRepository;
import com.fatihsengun.repository.ProductRepository;
import com.fatihsengun.service.IFavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FavoriteServiceImpl implements IFavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private IGlobalMapper globalMapper;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private ProductRepository productRepository;

    @CacheEvict(value = "product_filter", allEntries = true)
    public DtoFavorite addFavorite(DtoFavoriteIU dtoFavoriteIU) {
        User user = identityService.getCurrentUser();
        Product product = productRepository.findById(dtoFavoriteIU.getProduct())
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "User not found")));

        Favorite favorite = new Favorite();

        favorite.setUser(user);
        favorite.setProduct(product);

        Favorite savedFavorite = favoriteRepository.save(favorite);
        return (globalMapper.toDtoFavorite(savedFavorite));

    }

    @Override
    public List<DtoFavorite> getMyFavorites() {

        User currentUesr = identityService.getCurrentUser();

        List<Favorite> favorites = favoriteRepository.findAllByUserId(currentUesr.getId());

        List<DtoFavorite> dtoFavorites = new ArrayList<>();

        for (Favorite fav : favorites) {
            DtoFavorite dto = new DtoFavorite();

            DtoProduct dtoProduct = globalMapper.toDtoProduct(fav.getProduct());

            dto.setProduct(dtoProduct);

            dtoFavorites.add(dto);

        }


        return dtoFavorites;
    }

}
