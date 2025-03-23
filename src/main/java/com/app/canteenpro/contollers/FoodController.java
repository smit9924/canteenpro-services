package com.app.canteenpro.contollers;

import com.app.canteenpro.DataObjects.*;
import com.app.canteenpro.responses.ApiResponse;
import com.app.canteenpro.services.userapi.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/food")
public class FoodController {
    @Autowired
    public FoodService foodService;

    // Create category
    @PostMapping("/category")
    public ResponseEntity<ApiResponse<?>> createCategory(@RequestBody CategoryDto categoryDto) {
        foodService.createCategory(categoryDto);
        ApiResponse<?> apiResponse = new ApiResponse<>(false, true, "", "");
        return ResponseEntity.ok(apiResponse);
    }

    // Get category listing data
    @GetMapping("/category")
    public ResponseEntity<ApiResponse<CategoryDto>> getCategoryData(@RequestParam String guid) {
        CategoryDto category = foodService.getCategory(guid);
        ApiResponse<CategoryDto> apiResponse = new ApiResponse<CategoryDto>(category, true, "", "");
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/category")
    public ResponseEntity<ApiResponse<?>> upadteCategoryData(@RequestBody CategoryDto categoryDto) {
        foodService.updateCategory(categoryDto);
        ApiResponse<?> apiResponse = new ApiResponse<>(false, true, "", "");
        return ResponseEntity.ok(apiResponse);
    }

    // delete category
    @DeleteMapping("/category")
    public ResponseEntity<ApiResponse<List<CategoryListingDto>>> deleteFoodCategory(@RequestParam String guid) {
        foodService.deleteCategory(guid);
        List<CategoryListingDto> categories = foodService.getCategoryListing();
        ApiResponse<List<CategoryListingDto>> apiResponse = new ApiResponse<List<CategoryListingDto>>(categories, true, "Category deleted successfully!" ,"");
        return ResponseEntity.ok(apiResponse);
    }

    // Get category listing data
    @GetMapping("/category/listing")
    public ResponseEntity<ApiResponse<List<CategoryListingDto>>> getCategoryListingData() {
        List<CategoryListingDto> categories = foodService.getCategoryListing();
        ApiResponse<List<CategoryListingDto>> apiResponse = new ApiResponse<List<CategoryListingDto>>(categories, true, "", "");
        return ResponseEntity.ok(apiResponse);
    }

    // Create item
    @PostMapping("/item")
    public ResponseEntity<ApiResponse<?>> createFoodItem(@RequestBody FoodItemDto foodItemDto) {
        foodService.createFoodItem(foodItemDto);
        ApiResponse<?> apiResponse = new ApiResponse<>(false, true, "", "");
        return ResponseEntity.ok(apiResponse);
    }

    // Update food item
    @PutMapping("/item")
    public ResponseEntity<ApiResponse<?>> upadteFoodItem(@RequestBody FoodItemDto foodItemDto) {
        foodService.updateFoodItem(foodItemDto);
        ApiResponse<?> apiResponse = new ApiResponse<>(false, true, "", "");
        return ResponseEntity.ok(apiResponse);
    }

    // Get food item data
    @GetMapping("/item")
    public ResponseEntity<ApiResponse<FoodItemDto>> getFoodItemData(@RequestParam String guid) {
        final FoodItemDto foodItem = foodService.getFoodItem(guid);
        ApiResponse<FoodItemDto> apiResponse = new ApiResponse<FoodItemDto>(foodItem, true, "", "");
        return ResponseEntity.ok(apiResponse);
    }

    // delete food item
    @DeleteMapping("/item")
    public ResponseEntity<ApiResponse<List<FoodItemListingDto>>> deleteFoodItem(@RequestParam String guid) {
        foodService.deleteFoodItem(guid);
        List<FoodItemListingDto> foodItemList = foodService.getFoodItemsList();
        ApiResponse<List<FoodItemListingDto>> apiResponse = new ApiResponse<List<FoodItemListingDto>>(foodItemList, true, "Category deleted successfully!" ,"");
        return ResponseEntity.ok(apiResponse);
    }

    // Get category listing data
    @GetMapping("/item/listing")
    public ResponseEntity<ApiResponse<List<FoodItemListingDto>>> getFoodItemListingData() {
        List<FoodItemListingDto> foodItemsList = foodService.getFoodItemsList();
        ApiResponse<List<FoodItemListingDto>> apiResponse = new ApiResponse<List<FoodItemListingDto>>(foodItemsList, true, "", "");
        return ResponseEntity.ok(apiResponse);
    }

    // Get menu data
    @GetMapping("/menu/items")
    public ResponseEntity<ApiResponse<List<MenuFoodItemsDto>>> getMenuItems(@RequestParam Optional<String> category) {
        List<MenuFoodItemsDto> menuItems = foodService.getMenuItems(category);
        ApiResponse<List<MenuFoodItemsDto>> apiResponse = new ApiResponse<List<MenuFoodItemsDto>>(menuItems, true, "", "");
        return ResponseEntity.ok(apiResponse);
    }

    // Add menu item in cart
    @PutMapping("/menu/items/add")
    public ResponseEntity<ApiResponse<?>> addMenuItemIntoCart(@RequestBody UpdateCartItemQuantityDto updateCartItemQuantityDto) {
        foodService.addMenuItemIntoCart(updateCartItemQuantityDto);
        ApiResponse<?> apiResponse = new ApiResponse<>(false, true, "", "");
        return ResponseEntity.ok(apiResponse);
    }

    // Increase menu item quantity into cart
    @PutMapping("/menu/items/increase")
    public ResponseEntity<ApiResponse<?>> increaseMenuItemQuantity(@RequestBody UpdateCartItemQuantityDto updateCartItemQuantityDto) {
        foodService.increaseMenuItemQuantity(updateCartItemQuantityDto);
        ApiResponse<?> apiResponse = new ApiResponse<>(false, true, "", "");
        return ResponseEntity.ok(apiResponse);
    }

    // Decrease menu item quantity into cart
    @PutMapping("/menu/items/decrease")
    public ResponseEntity<ApiResponse<?>> decreaseMenuItemQuantity(@RequestBody UpdateCartItemQuantityDto updateCartItemQuantityDto) {
        foodService.decreaseMenuItemQuantity(updateCartItemQuantityDto);
        ApiResponse<?> apiResponse = new ApiResponse<>(false, true, "", "");
        return ResponseEntity.ok(apiResponse);
    }

    // Get all menu categories
    @GetMapping("/menu/category")
    public ResponseEntity<ApiResponse<List<MenuCategoryDto>>> getMenuCategories() {
        List<MenuCategoryDto> menuCategories = foodService.getMenuCategories();
        ApiResponse<List<MenuCategoryDto>> apiResponse = new ApiResponse<List<MenuCategoryDto>>(menuCategories, true, "", "");
        return ResponseEntity.ok(apiResponse);
    }
}
