package com.nangman.order.application.dto.response;

import com.nangman.order.application.dto.OrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedModel;

public record OrderSearchGetResponse(
    PagedModel<OrderDto> orders
) {
    public static OrderSearchGetResponse from(Page<OrderDto> searchOrderList) {
        return new OrderSearchGetResponse(new PagedModel<>(searchOrderList));
    }
}
