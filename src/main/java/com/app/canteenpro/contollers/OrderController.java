package com.app.canteenpro.contollers;

import com.app.canteenpro.DataObjects.CartItemsDto;
import com.app.canteenpro.responses.ApiResponse;
import com.app.canteenpro.services.userapi.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("")
    public ResponseEntity<ApiResponse<?>> placeOrder(@RequestBody List<CartItemsDto> orderItems) {
        orderService.placeOrder(orderItems);
        ApiResponse<?> apiResponse = new ApiResponse<>(false, true, "", "");
        return ResponseEntity.ok(apiResponse);
    }
}
