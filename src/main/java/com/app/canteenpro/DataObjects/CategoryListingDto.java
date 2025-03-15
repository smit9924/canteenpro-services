package com.app.canteenpro.DataObjects;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class CategoryListingDto {
    private String guid;
    private String categoryName;
    private String dateCreated;
    private String dateEdited;

    public CategoryListingDto(
            String guid,
            String categoryName,
            LocalDateTime dateCreated,
            LocalDateTime dateEdited
    ) {
        this.guid = guid;
        this.categoryName = categoryName;
        this.dateCreated = this.formatDate(dateCreated);
        this.dateEdited = this.formatDate(dateEdited);
    }

    private String formatDate(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");  // Omitting seconds
        return dateTime.format(formatter);
    }
}
