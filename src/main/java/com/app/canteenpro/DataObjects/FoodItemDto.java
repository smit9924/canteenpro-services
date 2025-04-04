package com.app.canteenpro.DataObjects;

import com.app.canteenpro.common.Enums;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
