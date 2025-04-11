package com.app.canteenpro.DataObjects;

import com.app.canteenpro.common.Enums;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class CartItemsDto {
    private String guid;
    private String itemName;
    private Enums.FOOD_ITEM_TYPE type;
    private Integer price;
    private Integer quantity;
    private Enums.FOOD_ITEM_QUANTITY_UNIT quantityUnit;
    private MediaDataDto imageData;
    private Integer itemCount;
    private String canteenGuid;
}
