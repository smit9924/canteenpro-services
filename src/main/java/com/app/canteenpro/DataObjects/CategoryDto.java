package com.app.canteenpro.DataObjects;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CategoryDto {
    private String guid;
    private String categoryName;
    private String description;
    private String imageURL;
    private MediaDataDto imageData;
}
