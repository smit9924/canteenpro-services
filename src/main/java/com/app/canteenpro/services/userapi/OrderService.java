package com.app.canteenpro.services.userapi;

import com.app.canteenpro.DataObjects.CartItemsDto;
import com.app.canteenpro.DataObjects.OrderDto;
import com.app.canteenpro.database.models.FoodItem;
import com.app.canteenpro.database.models.Order;
import com.app.canteenpro.database.models.OrderItem;
import com.app.canteenpro.database.models.User;
import com.app.canteenpro.database.repositories.CartItemRepo;
import com.app.canteenpro.database.repositories.FoodItemRepo;
import com.app.canteenpro.database.repositories.OrderItemRepo;
import com.app.canteenpro.database.repositories.OrderRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private OrderItemRepo orderItemRepo;

    @Autowired
    private CommonService commonService;

    @Autowired
    private CartItemRepo cartItemRepo;

    @Autowired
    private FoodItemRepo foodItemRepo;

    @Transactional
    public void placeOrder(List<CartItemsDto> orderItems) {
        User currentUser = commonService.getLoggedInUser();

        // Remove all items from cart
        cartItemRepo.deleteAllByUser(currentUser);

        // Store order details
        Order order = new Order();
        order.setGuid(UUID.randomUUID().toString());
        order.setUser(currentUser);
        orderRepo.save(order);

        // Store order items
        orderItems.forEach((orderItem) -> {
            FoodItem foodItem = foodItemRepo.findByGuid(orderItem.getGuid());
            OrderItem newOrderItem = new OrderItem();
            newOrderItem.setGuid(UUID.randomUUID().toString());
            newOrderItem.setFoodItem(foodItem);
            System.out.println(orderItem.getItemCount());
            newOrderItem.setQuantity(orderItem.getItemCount());
            newOrderItem.setOrder(order);
            orderItemRepo.save(newOrderItem);
        });
    }
}
