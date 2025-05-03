package com.app.canteenpro.DataObjects;

import com.app.canteenpro.common.Enums;
import com.app.canteenpro.database.models.FoodItem;
import com.app.canteenpro.database.models.Order;
import com.app.canteenpro.database.models.OrderItem;
import com.app.canteenpro.database.repositories.FoodItemRepo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Locale;

@Getter
@Setter
public class OrderDetailsDto {
    private String orderId;
    private String orderPlacedDate;
    private Collection<OrderItemsDto> orderItems;
    private int total;
    private Enums.ORDER_STATUS orderStatus;

    public OrderDetailsDto(
            Order order
    ) {
        this.orderId = order.getGuid();
        this.orderPlacedDate = this.formatDate(order.getCreatedOn());
        this.setOrderItems(order.getOrderItems());
        this.orderStatus = order.getOrderStatus();
        this.setOrderData(order.getOrderItems());
    }

    private String formatDate(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH);
        return dateTime.format(formatter);
    }

    private void setOrderItems(Collection<OrderItem> orderItems) {
        this.orderItems = orderItems.stream().map(orderItem -> {
            MediaDataDto mediaDataDto = MediaDataDto.builder()
                    .guid(orderItem.getFoodItem().getImage().getGuid())
                    .fileName(orderItem.getFoodItem().getImage().getFilename())
                    .extension(orderItem.getFoodItem().getImage().getExtension())
                    .initiallyUploadedOn(orderItem.getFoodItem().getImage().getInitiallyUploadedOn())
                    .build();

            return OrderItemsDto.builder()
                    .guid(orderItem.getGuid())
                    .type(Enums.FOOD_ITEM_TYPE.fromValue(orderItem.getFoodItem().getType()))
                    .itemName(orderItem.getFoodItem().getName())
                    .quantity(orderItem.getFoodItem().getQuantity())
                    .quantityUnit(Enums.FOOD_ITEM_QUANTITY_UNIT.fromValue(orderItem.getFoodItem().getQuantityUnit()))
                    .type(Enums.FOOD_ITEM_TYPE.fromValue(orderItem.getFoodItem().getType()))
                    .price(orderItem.getFoodItem().getPrice())
                    .itemCount(orderItem.getQuantity())
                    .imageData(mediaDataDto)
                    .build();
        }).toList();
    }

    private void setOrderData(Collection<OrderItem> orderItems) {
        this.total = 0;
         orderItems.stream()
                .map(orderItem -> {
                    this.total += (orderItem.getFoodItem().getPrice() * orderItem.getQuantity());
                    return orderItem.getFoodItem().getName();
                })
                .toList();
    }

}
