package com.app.canteenpro.services.userapi;

import com.app.canteenpro.DataObjects.CategoryDto;
import com.app.canteenpro.DataObjects.CategoryListingDto;
import com.app.canteenpro.DataObjects.MediaDataDto;
import com.app.canteenpro.common.Enums;
import com.app.canteenpro.database.models.FoodCategory;
import com.app.canteenpro.database.models.MediaMetaData;
import com.app.canteenpro.database.models.User;
import com.app.canteenpro.database.repositories.FoodCategoryRepo;
import com.app.canteenpro.database.repositories.MediaMetaDataRepo;
import com.app.canteenpro.exceptions.UserNotFoundException;
import com.google.firebase.internal.FirebaseService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FoodService {
    @Autowired
    final CommonService commonService;

    @Autowired
    final FirestorageService firestorageService;

    @Autowired
    final FoodCategoryRepo foodCategoryRepo;

    @Autowired
    final MediaMetaDataRepo mediaMetaDataRepo;

    public boolean createCategory(CategoryDto categoryDto) {
        final User currentUser = commonService.getLoggedInUser();

        // Upload image to bucket
        final MediaMetaData mediaMetaData = firestorageService.uploadMedia(categoryDto.getImageURL(), categoryDto.getImageData(), Enums.FILE_TYPES.IMAGE);

        FoodCategory newCategory = new FoodCategory();
        newCategory.setGuid(UUID.randomUUID().toString());
        newCategory.setName(categoryDto.getCategoryName());
        newCategory.setDescription(categoryDto.getDescription());
        newCategory.setCanteen(currentUser.getCanteen());
        newCategory.setMedia(mediaMetaData);
        foodCategoryRepo.save(newCategory);
        return true;
    }

    public List<CategoryListingDto> getCategoryListing() {
        User currentUser = commonService.getLoggedInUser();

        final List<CategoryListingDto> categories = this.foodCategoryRepo.findAllByCanteen(currentUser.getCanteen()).stream().map((category) -> {
            return new CategoryListingDto(
                    category.getGuid(),
                    category.getName(),
                    category.getCreatedOn(),
                    category.getEditedOn()
            );
        }).toList();

        return categories;
    }

    public CategoryDto getCategory(String guid) {
        FoodCategory category = foodCategoryRepo.findByGuid(guid);

        MediaDataDto mediaDataDto = MediaDataDto.builder()
                .guid(category.getMedia().getGuid())
                .fileName(category.getMedia().getFilename())
                .extension(category.getMedia().getExtension())
                .build();

        CategoryDto categoryDto = CategoryDto.builder()
                .guid(category.getGuid())
                .categoryName(category.getName())
                .description(category.getDescription())
                .imageData(mediaDataDto)
                .build();

        return categoryDto;
    }

    public void updateCategory(CategoryDto categoryDto) {
        FoodCategory category = foodCategoryRepo.findByGuid(categoryDto.getGuid());
        if(category == null) {
            throw new UserNotFoundException("Category with give id is not found");
        }

        category.setName(categoryDto.getCategoryName());
        category.setDescription(categoryDto.getDescription());

        if(categoryDto.getImageData().getGuid().isEmpty()) {
            final MediaMetaData existingMedia = mediaMetaDataRepo.findByGuid(category.getMedia().getGuid());
            final String existingMediaUniqueName = existingMedia.getGuid() + "." + existingMedia.getExtension();

            // Delete previous media from bucket
            firestorageService.deleteMedia(existingMediaUniqueName, Enums.FILE_TYPES.IMAGE);

            // Upload image to bucket
            final MediaMetaData mediaMetaData = firestorageService.uploadMedia(categoryDto.getImageURL(), categoryDto.getImageData(), Enums.FILE_TYPES.IMAGE);

            // Update media link with new one
            category.setMedia(mediaMetaData);

            // Delete existing media meta data entry from database
            mediaMetaDataRepo.deleteById(existingMedia.getId());
        }

        foodCategoryRepo.save(category);
    }
}
