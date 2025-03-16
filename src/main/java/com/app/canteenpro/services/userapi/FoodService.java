package com.app.canteenpro.services.userapi;

import com.app.canteenpro.DataObjects.*;
import com.app.canteenpro.common.Enums;
import com.app.canteenpro.common.appConstants;
import com.app.canteenpro.database.models.FoodCategory;
import com.app.canteenpro.database.models.FoodItem;
import com.app.canteenpro.database.models.MediaMetaData;
import com.app.canteenpro.database.models.User;
import com.app.canteenpro.database.repositories.FoodCategoryRepo;
import com.app.canteenpro.database.repositories.FoodItemRepo;
import com.app.canteenpro.database.repositories.MediaMetaDataRepo;
import com.app.canteenpro.exceptions.UserNotFoundException;
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

    @Autowired
    final FoodItemRepo itemsRepo;

    @Autowired
    final FoodItemRepo foodItemRepo;

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

    public void deleteCategory(String guid) {
        FoodCategory category = foodCategoryRepo.findByGuid(guid);
        if(category == null) {
            throw new UserNotFoundException(appConstants.CATEGORY_HAVING_GUID_NOT_FOUND);
        }
        final MediaMetaData categoryMedia = category.getMedia();
        final String fileNameWithExtenion = categoryMedia.getGuid() + "." + categoryMedia.getExtension();
        foodCategoryRepo.delete(category);
        mediaMetaDataRepo.delete(categoryMedia);
        firestorageService.deleteMedia(fileNameWithExtenion, Enums.FILE_TYPES.IMAGE);
    }

    public void createFoodItem(FoodItemDto foodItemDto) {
        final User currentUser = commonService.getLoggedInUser();

        // Upload image to bucket
        final MediaMetaData mediaMetaData = firestorageService.uploadMedia(foodItemDto.getImageURL(), foodItemDto.getImageData(), Enums.FILE_TYPES.IMAGE);

        // Store New Item data
        FoodItem item = new FoodItem();
        item.setGuid(UUID.randomUUID().toString());
        item.setName(foodItemDto.getItemName());
        item.setPrice(foodItemDto.getPrice());
        item.setDescription(foodItemDto.getDescription());
        item.setQuantity(foodItemDto.getQuantity());
        item.setQuantityUnit(foodItemDto.getQuantityUnit().getValue());
        item.setTaste(foodItemDto.getTaste().getValue());
        item.setType(foodItemDto.getType().getValue());
        item.setCanteen(currentUser.getCanteen());
        // item.setCategory();
        item.setImage(mediaMetaData);
        itemsRepo.save(item);
    }

    public List<FoodItemListingDto> getFoodItemsList() {
        User currentUser = commonService.getLoggedInUser();

        final List<FoodItemListingDto> foodItems = this.foodItemRepo.findAllByCanteen(currentUser.getCanteen()).stream().map((foodItem) -> {
            return new FoodItemListingDto(
                    foodItem.getGuid(),
                    foodItem.getName(),
                    foodItem.getQuantity(),
                    foodItem.getQuantityUnit(),
                    foodItem.getPrice(),
                    foodItem.getType(),
                    foodItem.getCreatedOn(),
                    foodItem.getEditedOn()
            );
        }).toList();

        return foodItems;
    }

    public FoodItemDto getFoodItem(String guid) {
        FoodItem foodItem = foodItemRepo.findByGuid(guid);

        MediaDataDto mediaDataDto = MediaDataDto.builder()
                .guid(foodItem.getImage().getGuid())
                .fileName(foodItem.getImage().getFilename())
                .extension(foodItem.getImage().getExtension())
                .build();

        FoodItemDto foodItemDto = FoodItemDto.builder()
                .guid(foodItem.getGuid())
                .itemName(foodItem.getName())
                .description(foodItem.getDescription())
                .price(foodItem.getPrice())
                .quantity(foodItem.getQuantity())
                .quantityUnit(Enums.FOOD_ITEM_QUANTITY_UNIT.fromValue(foodItem.getQuantityUnit()))
                .taste(Enums.FOOD_ITEM_TASTE.fromValue(foodItem.getTaste()))
                .type(Enums.FOOD_ITEM_TYPE.fromValue(foodItem.getType()))
                .imageData(mediaDataDto)
                .build();

        return foodItemDto;
    }

    public void updateFoodItem(FoodItemDto foodItemDto) {
        FoodItem foodItem = foodItemRepo.findByGuid(foodItemDto.getGuid());
        if(foodItem == null) {
            throw new UserNotFoundException("Category with give id is not found");
        }

        foodItem.setName(foodItemDto.getItemName());
        foodItem.setDescription(foodItemDto.getDescription());
        foodItem.setPrice(foodItemDto.getPrice());
        foodItem.setQuantity(foodItemDto.getQuantity());
        foodItem.setQuantityUnit(foodItemDto.getQuantityUnit().getValue());
        foodItem.setTaste(foodItemDto.getTaste().getValue());
        foodItem.setType(foodItemDto.getType().getValue());

        if(foodItemDto.getImageData().getGuid().isEmpty()) {
            final MediaMetaData existingMedia = mediaMetaDataRepo.findByGuid(foodItem.getImage().getGuid());
            final String existingMediaUniqueName = existingMedia.getGuid() + "." + existingMedia.getExtension();

            // Delete previous media from bucket
            firestorageService.deleteMedia(existingMediaUniqueName, Enums.FILE_TYPES.IMAGE);

            // Upload image to bucket
            final MediaMetaData mediaMetaData = firestorageService.uploadMedia(foodItemDto.getImageURL(), foodItemDto.getImageData(), Enums.FILE_TYPES.IMAGE);

            // Update media link with new one
            foodItem.setImage(mediaMetaData);

            // Delete existing media meta data entry from database
            mediaMetaDataRepo.deleteById(existingMedia.getId());
        }

        foodItemRepo.save(foodItem);
    }

    public void deleteFoodItem(String guid) {
        FoodItem foodItem = foodItemRepo.findByGuid(guid);
        if(foodItem == null) {
            throw new UserNotFoundException(appConstants.CATEGORY_HAVING_GUID_NOT_FOUND);
        }
        final MediaMetaData foodItemImage = foodItem.getImage();
        final String fileNameWithExtenion = foodItemImage.getGuid() + "." + foodItemImage.getExtension();
        foodItemRepo.delete(foodItem);
        mediaMetaDataRepo.delete(foodItemImage);
        firestorageService.deleteMedia(fileNameWithExtenion, Enums.FILE_TYPES.IMAGE);
    }
}
