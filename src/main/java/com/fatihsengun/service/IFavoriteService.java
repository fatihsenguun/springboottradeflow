package com.fatihsengun.service;


import com.fatihsengun.dto.DtoFavorite;
import com.fatihsengun.dto.DtoFavoriteIU;

import java.util.List;

public interface IFavoriteService {
    public DtoFavorite toggleFavorite(DtoFavoriteIU dtoFavoriteIU);

    public List<DtoFavorite> getMyFavorites();

}
