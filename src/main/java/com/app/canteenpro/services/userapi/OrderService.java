package com.app.canteenpro.services.userapi;

import com.app.canteenpro.DataObjects.*;
import com.app.canteenpro.common.Enums;
import com.app.canteenpro.database.models.*;
import com.app.canteenpro.database.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    @Autowired
    private CanteenRepo canteenRepo;

    @Transactional
    public void placeOrder(PlaceOrderDto placeOrderDto) {
        User currentUser = commonService.getLoggedInUser();

        // Remove all items from cart
        cartItemRepo.deleteAllByUser(currentUser);

        // Find canteen
        Optional<Canteen> canteen = canteenRepo.findByGuid(placeOrderDto.getCanteenGuid());
        if(canteen.isEmpty()) {
            // TODO: throw error
            throw new RuntimeException();
        }

        // Store order details
        Order order = new Order();
        order.setGuid(UUID.randomUUID().toString());
        order.setUser(currentUser);
        order.setCanteen(canteen.get());
        order.setInstructions(placeOrderDto.getInstructions());
        order.setOrderStatus(Enums.ORDER_STATUS.PLACED);
        orderRepo.save(order);

        // Store order items
        placeOrderDto.getOrderItems().forEach((orderItem) -> {
            FoodItem foodItem = foodItemRepo.findByGuid(orderItem.getGuid());
            OrderItem newOrderItem = new OrderItem();
            newOrderItem.setGuid(UUID.randomUUID().toString());
            newOrderItem.setFoodItem(foodItem);
            newOrderItem.setQuantity(orderItem.getQuantity());
            newOrderItem.getOrder().add(order);
            orderItemRepo.save(newOrderItem);
        });
    }

    public OrderDetailsDto getOrder(String guid) {
        Optional<Order> order = orderRepo.findByGuid(guid);

        if(order.isEmpty()) {
            throw new RuntimeException();
        }

        OrderDetailsDto orderDetailsDto = new OrderDetailsDto(order.get());
        return orderDetailsDto;
    }

    public List<OrderListDto> getOrdersList() {
        // get current logged in user;
        User currentUser = commonService.getLoggedInUser();

        // Get all order of current user
        List<Order> orders = orderRepo.findAllByUser(currentUser);

        List<OrderListDto> orderList = orders
                .stream()
                .map(order -> {
                    final OrderListDto orderInList = new OrderListDto(order);
                    return orderInList;
                })
                .toList();

        return orderList;
    }

    public List<OrderDetailsDto> getPendingOrdersList() {
        // get current logged in user;
        User currentUser = commonService.getLoggedInUser();

        // Get canteen of current user
        Canteen canteen = currentUser.getCanteen();

        // Get all order of current user
        List<Order> orders = orderRepo.findAllByCanteenOrderByCreatedOnDesc(canteen);

        List<OrderDetailsDto> orderList = orders
                .stream()
                .map(order -> {
                    final OrderDetailsDto orderInList = new OrderDetailsDto(order);
                    return orderInList;
                })
                .toList();

        return orderList;
    }

    public void updateOrderStatus(String guid, Enums.ORDER_STATUS status) {
        Optional<Order> order = orderRepo.findByGuid(guid);
        if(order.isEmpty()) {
            throw new RuntimeException();
        }

        final Order orderPresent = order.get();
        orderPresent.setOrderStatus(status);
        orderRepo.save(orderPresent);
    }
}
