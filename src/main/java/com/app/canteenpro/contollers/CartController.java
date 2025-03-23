package com.app.canteenpro.contollers;

import com.app.canteenpro.DataObjects.CartItemsDto;
import com.app.canteenpro.DataObjects.UpdateCartItemQuantityDto;
import com.app.canteenpro.responses.ApiResponse;
import com.app.canteenpro.services.userapi.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @PutMapping("/item")
    public ResponseEntity<ApiResponse<List<CartItemsDto>>> addItemIntoCart(@RequestBody UpdateCartItemQuantityDto updateCartItemQuantityDto) {
        cartService.addItemIntoCart(updateCartItemQuantityDto);
        List<CartItemsDto> cartItems = cartService.getCartItemsList();
        ApiResponse<List<CartItemsDto>> apiResponse = new ApiResponse<List<CartItemsDto>>(cartItems, true, "", "");
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/item/increase")
    public ResponseEntity<ApiResponse<List<CartItemsDto>>> increaseCartItemQuantity(@RequestBody UpdateCartItemQuantityDto updateCartItemQuantityDto) {
        cartService.increaseCartItemQuantity(updateCartItemQuantityDto);
        List<CartItemsDto> cartItems = cartService.getCartItemsList();
        ApiResponse<List<CartItemsDto>> apiResponse = new ApiResponse<List<CartItemsDto>>(cartItems, true, "", "");
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/item/decrease")
    public ResponseEntity<ApiResponse<List<CartItemsDto>>> decreaseCartItemQuantity(@RequestBody UpdateCartItemQuantityDto updateCartItemQuantityDto) {
        cartService.decreaseCartItemQuantity(updateCartItemQuantityDto);
        List<CartItemsDto> cartItems = cartService.getCartItemsList();
        ApiResponse<List<CartItemsDto>> apiResponse = new ApiResponse<List<CartItemsDto>>(cartItems, true, "", "");
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/item")
    public ResponseEntity<ApiResponse<List<CartItemsDto>>> getCartItems() {
        List<CartItemsDto> cartItems = cartService.getCartItems();
        ApiResponse<List<CartItemsDto>> apiResponse = new ApiResponse<List<CartItemsDto>>(cartItems, true, "", "");
        return ResponseEntity.ok(apiResponse);
    }
}
