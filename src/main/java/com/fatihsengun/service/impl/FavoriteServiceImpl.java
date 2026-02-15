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
import java.util.Optional;

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
    public DtoFavorite toggleFavorite(DtoFavoriteIU dtoFavoriteIU) {

        User user = identityService.getCurrentUser();

        Optional<Favorite> optional = favoriteRepository.findByUserIdAndProductId(user.getId(), dtoFavoriteIU.getProduct());

        if (optional.isPresent()) {
            favoriteRepository.delete(optional.get());
            return null;
        } else {
            Favorite newFavorite = new Favorite();

            newFavorite.setUser(user);

            Product product = productRepository.findById(dtoFavoriteIU.getProduct())
                    .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "Product not found")));

            newFavorite.setProduct(product);

            Favorite savedFavorite = favoriteRepository.save(newFavorite);
            return (globalMapper.toDtoFavorite(savedFavorite));
        }


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
