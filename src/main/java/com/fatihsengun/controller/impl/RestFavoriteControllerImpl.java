package com.fatihsengun.controller.impl;

import com.fatihsengun.controller.IRestFavoriteController;
import com.fatihsengun.controller.RestRootResponseController;
import com.fatihsengun.dto.DtoFavorite;
import com.fatihsengun.dto.DtoFavoriteIU;
import com.fatihsengun.entity.RootResponseEntity;
import com.fatihsengun.service.impl.FavoriteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/api/favorite")
public class RestFavoriteControllerImpl extends RestRootResponseController implements IRestFavoriteController {


    @Autowired
    private FavoriteServiceImpl favoriteService;

    @Override
    @PostMapping("/toggle")
    public RootResponseEntity<DtoFavorite> addFavorite(DtoFavoriteIU dtoFavoriteIU) {

        return ok(favoriteService.toggleFavorite(dtoFavoriteIU));
    }

    @Override
    @GetMapping("/myfavorites")
    public RootResponseEntity<List<DtoFavorite>> getMyFavorites() {

        return ok(favoriteService.getMyFavorites());
    }
}
