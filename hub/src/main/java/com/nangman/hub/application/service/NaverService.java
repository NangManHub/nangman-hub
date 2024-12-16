package com.nangman.hub.application.service;

import com.nangman.hub.application.dto.request.NaverRouteRequest;
import com.nangman.hub.application.dto.response.NaverRouteResponse;

import java.util.List;

public interface NaverService {
    NaverRouteResponse directions(NaverRouteRequest reqFrom, NaverRouteRequest reqTo);

    List<NaverRouteResponse> getBiDirections(NaverRouteRequest reqFrom, NaverRouteRequest reqTo);
}
