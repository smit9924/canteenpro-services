package com.app.canteenpro.DataObjects;

import com.app.canteenpro.common.Enums;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class OrderItemsDto {
    private String guid;
    private String itemName;
    private Integer quantity;
    private Integer itemCount;
    private Enums.FOOD_ITEM_QUANTITY_UNIT quantityUnit;
    private Enums.FOOD_ITEM_TYPE type;
    private Integer price;
    private MediaDataDto imageData;
}
