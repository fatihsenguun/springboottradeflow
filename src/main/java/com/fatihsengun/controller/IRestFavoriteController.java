package com.fatihsengun.controller;

import com.fatihsengun.dto.DtoFavorite;
import com.fatihsengun.dto.DtoFavoriteIU;
import com.fatihsengun.entity.RootResponseEntity;

import java.util.List;

public interface IRestFavoriteController {

    public RootResponseEntity<DtoFavorite> addFavorite(DtoFavoriteIU dtoFavoriteIU);

    public RootResponseEntity<List<DtoFavorite>> getMyFavorites();

}
