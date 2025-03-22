package com.app.canteenpro.DataObjects;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class FoodItemsCategoriesDto {
    private String categoryName;
    private String guid;
}
