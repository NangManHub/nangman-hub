package com.nangman.hub.application.dto.response;

import lombok.Builder;
import org.json.JSONObject;

@Builder
public record NaverRouteResponse(
        Integer duration,
        Integer distance
) {

    public static NaverRouteResponse from(JSONObject json) {
        return NaverRouteResponse.builder()
                .duration(json.getInt("duration"))
                .distance(json.getInt("distance"))
                .build();
    }
}
