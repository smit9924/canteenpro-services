package com.app.canteenpro.contollers;

import com.app.canteenpro.DataObjects.CategoryDto;
import com.app.canteenpro.DataObjects.CategoryListingDto;
import com.app.canteenpro.responses.ApiResponse;
import com.app.canteenpro.services.userapi.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // Get category listing data
    @GetMapping("/category/listing")
    public ResponseEntity<ApiResponse<List<CategoryListingDto>>> getCategoryListingData() {
        List<CategoryListingDto> categories = foodService.getCategoryListing();
        ApiResponse<List<CategoryListingDto>> apiResponse = new ApiResponse<List<CategoryListingDto>>(categories, true, "", "");
        return ResponseEntity.ok(apiResponse);
    }

    // Create item
    @PostMapping("/item")
    public ResponseEntity<ApiResponse<?>> createFoodItem() {
        ApiResponse<?> apiResponse = new ApiResponse<>(false, false, "", "");
        return ResponseEntity.ok(apiResponse);
    }

    // Get category data
    @GetMapping("/item")
    public ResponseEntity<ApiResponse<?>> getFoodItemData() {
        ApiResponse<?> apiResponse = new ApiResponse<>(false, false, "", "");
        return ResponseEntity.ok(apiResponse);
    }
}
