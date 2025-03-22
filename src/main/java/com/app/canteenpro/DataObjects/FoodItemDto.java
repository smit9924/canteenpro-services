package com.app.canteenpro.DataObjects;

import com.app.canteenpro.common.Enums;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class FoodItemDto {
    private String guid;
    private String itemName;
    private String description;
    private Integer quantity;
    private Enums.FOOD_ITEM_QUANTITY_UNIT quantityUnit;
    private Enums.FOOD_ITEM_TYPE type;
    private Enums.FOOD_ITEM_TASTE taste;
    private Integer price;
    private String imageURL;
    private MediaDataDto imageData;
    private List<FoodItemsCategoriesDto> categories;
}
