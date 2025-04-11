package com.app.canteenpro.services.userapi;

import com.app.canteenpro.DataObjects.CartItemsDto;
import com.app.canteenpro.DataObjects.CheckItemAddedInCartDto;
import com.app.canteenpro.DataObjects.MediaDataDto;
import com.app.canteenpro.DataObjects.UpdateCartItemQuantityDto;
import com.app.canteenpro.common.Enums;
import com.app.canteenpro.database.models.CartItem;
import com.app.canteenpro.database.models.FoodItem;
import com.app.canteenpro.database.models.User;
import com.app.canteenpro.database.repositories.CartItemRepo;
import com.app.canteenpro.database.repositories.FoodItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    @Autowired
    private CommonService commonService;

    @Autowired
    private CartItemRepo cartItemsRepo;

    @Autowired
    private FoodItemRepo foodItemRepo;

    public List<CartItemsDto> getCartItemsList() {
        List<CartItemsDto> cartItems = this.getCartItems();
        return cartItems;
    }

    public void addItemIntoCart(UpdateCartItemQuantityDto updateCartItemQuantityDto) {
        User currentUser = commonService.getLoggedInUser();
        FoodItem foodItem = foodItemRepo.findByGuid(updateCartItemQuantityDto.getGuid());
        CartItem cartItem = new CartItem();
        cartItem.setUser(currentUser);
        cartItem.setFoodItem(foodItem);
        cartItem.setQuantity(1);
        cartItemsRepo.save(cartItem);
    }

    public void increaseCartItemQuantity(UpdateCartItemQuantityDto updateCartItemQuantityDto) {
        User currentUser = commonService.getLoggedInUser();
        FoodItem foodItem = foodItemRepo.findByGuid(updateCartItemQuantityDto.getGuid());
        CartItem cartItem = cartItemsRepo.findByUserAndFoodItem(currentUser, foodItem);
        cartItem.setQuantity(cartItem.getQuantity() + 1);
        cartItemsRepo.save(cartItem);
    }

    public void decreaseCartItemQuantity(UpdateCartItemQuantityDto updateCartItemQuantityDto) {
        User currentUser = commonService.getLoggedInUser();
        FoodItem foodItem = foodItemRepo.findByGuid(updateCartItemQuantityDto.getGuid());
        CartItem cartItem = cartItemsRepo.findByUserAndFoodItem(currentUser, foodItem);

        int updatedItemsQuantity = cartItem.getQuantity() - 1;
        if(updatedItemsQuantity <= 0) {
            cartItemsRepo.delete(cartItem);
        } else {
            cartItem.setQuantity(updatedItemsQuantity);
            cartItemsRepo.save(cartItem);
        }
    }

    public List<CartItemsDto> getCartItems() {
        User currentUser = commonService.getLoggedInUser();
        List<CartItemsDto> cartItems = cartItemsRepo.findAllByUser(currentUser)
                .stream()
                .map(cartItem -> {
                    FoodItem foodItem = cartItem.getFoodItem();

                    MediaDataDto mediaDataDto = MediaDataDto
                            .builder()
                            .guid(foodItem.getImage().getGuid())
                            .fileName(foodItem.getImage().getFilename())
                            .extension(foodItem.getImage().getExtension())
                            .build();

                    return CartItemsDto
                            .builder()
                            .guid(foodItem.getGuid())
                            .itemName(foodItem.getName())
                            .type(Enums.FOOD_ITEM_TYPE.fromValue(foodItem.getType()))
                            .price(foodItem.getPrice())
                            .quantity(foodItem.getQuantity())
                            .quantityUnit(Enums.FOOD_ITEM_QUANTITY_UNIT.fromValue(foodItem.getQuantityUnit()))
                            .itemCount(cartItem.getQuantity())
                            .imageData(mediaDataDto)
                            .canteenGuid(foodItem.getCanteen().getGuid())
                            .build();
                })
                .toList();

        return cartItems;
    }

    public CheckItemAddedInCartDto isItemAddedInCart(String guid) {
        User currentUser = commonService.getLoggedInUser();
        FoodItem foodItem = foodItemRepo.findByGuid(guid);
        CartItem cartItem = cartItemsRepo.findByUserAndFoodItem(currentUser, foodItem);

        if(cartItem == null) {
            return CheckItemAddedInCartDto.builder().build();
        }

        return CheckItemAddedInCartDto
                .builder()
                .isItemAddedInCart(true)
                .itemCount(cartItem.getQuantity())
                .build();
    }
}
