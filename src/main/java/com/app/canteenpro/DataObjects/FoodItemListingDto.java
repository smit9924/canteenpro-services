package com.app.canteenpro.DataObjects;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Getter
@Setter
public class FoodItemListingDto {
    private String guid;
    private String itemName;
    private Integer quantity;
    private Integer quantityUnit;
    private Integer price;
    private Integer type;
    private String dateCreated;
    private String dateEdited;

    public FoodItemListingDto(
        String guid,
        String itemName,
        Integer quantity,
        Integer quantityUnit,
        Integer price,
        Integer type,
        LocalDateTime createdOn,
        LocalDateTime editedOn
    ) {
        this.guid = guid;
        this.itemName = itemName;
        this.quantity = quantity;
        this.quantityUnit = quantityUnit;
        this.price = price;
        this.type = type;
        this.dateCreated = this.formatDate(createdOn);
        this.dateEdited = this.formatDate(editedOn);
    }

    private String formatDate(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");  // Omitting seconds
        return dateTime.format(formatter);
    }
}
