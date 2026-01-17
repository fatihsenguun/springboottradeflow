package com.fatihsengun.service;

import com.fatihsengun.dto.DtoRefreshToken;
import com.fatihsengun.dto.DtoRefreshTokenUI;
import com.fatihsengun.entity.RefreshToken;
import com.fatihsengun.entity.User;

public interface IRefreshTokenService {
    public RefreshToken saveRefreshToken(User user);
    public DtoRefreshToken refreshToken(DtoRefreshTokenUI dtoRefreshTokenUI);
}
