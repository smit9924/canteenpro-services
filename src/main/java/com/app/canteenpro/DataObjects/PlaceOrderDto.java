package com.app.canteenpro.DataObjects;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class PlaceOrderDto {
    private List<OrderItemQuantityDto> orderItems;
    private String instructions;
    private String canteenGuid;
}
