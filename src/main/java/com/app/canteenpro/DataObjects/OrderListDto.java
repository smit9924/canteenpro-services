package com.app.canteenpro.DataObjects;

import com.app.canteenpro.common.Enums;
import com.app.canteenpro.database.models.FoodItem;
import com.app.canteenpro.database.models.Order;
import com.app.canteenpro.database.models.OrderItem;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@Getter
@Setter
public class OrderListDto {
    private String orderId;
    private String orderPlacedDate;
    private List<String> orderItemsName;
    private int total;
    private Enums.ORDER_STATUS orderStatus;

    public OrderListDto(
        Order order
    ) {
        this.orderId = order.getGuid();
        this.orderPlacedDate = this.formatDate(order.getCreatedOn());
        this.setOrderData(order.getOrderItems());
        this.orderStatus = order.getOrderStatus();
    }

    private String formatDate(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH);
        return dateTime.format(formatter);
    }

    private void setOrderData(Collection<OrderItem> orderItems) {
        this.total = 0;
        this.orderItemsName = orderItems
                .stream()
                .map(orderItem -> {
                    this.total += (orderItem.getFoodItem().getPrice() * orderItem.getQuantity());
                    return orderItem.getFoodItem().getName();
                })
                .toList();
    }

}
