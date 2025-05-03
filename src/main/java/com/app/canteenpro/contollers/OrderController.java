package com.app.canteenpro.contollers;

import com.app.canteenpro.DataObjects.CartItemsDto;
import com.app.canteenpro.DataObjects.OrderDetailsDto;
import com.app.canteenpro.DataObjects.OrderListDto;
import com.app.canteenpro.DataObjects.PlaceOrderDto;
import com.app.canteenpro.common.Enums;
import com.app.canteenpro.responses.ApiResponse;
import com.app.canteenpro.services.userapi.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("")
    public ResponseEntity<ApiResponse<?>> placeOrder(@RequestBody PlaceOrderDto placeOrderDto) {
        orderService.placeOrder(placeOrderDto);
        ApiResponse<?> apiResponse = new ApiResponse<>(false, true, "", "");
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<OrderDetailsDto>> getOrder(@RequestParam String guid) {
        OrderDetailsDto orderDetailsDto = orderService.getOrder(guid);
        ApiResponse<OrderDetailsDto> apiResponse = new ApiResponse<>(orderDetailsDto, true, "", "");
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<OrderListDto>>> getOrdersList() {
        List<OrderListDto> orderList = orderService.getOrdersList();
        ApiResponse<List<OrderListDto>> apiResponse = new ApiResponse<>(orderList, true, "", "");
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/list/pending")
    public ResponseEntity<ApiResponse<List<OrderDetailsDto>>> getPendingOrderList() {
        List<OrderDetailsDto> orderList = orderService.getPendingOrdersList();
        ApiResponse<List<OrderDetailsDto>> apiResponse = new ApiResponse<>(orderList, true, "", "");
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/updatestatus")
    public ResponseEntity<ApiResponse<?>> updateOrderStatus(@RequestParam String guid, @RequestParam int status) {
        orderService.updateOrderStatus(guid, Enums.ORDER_STATUS.fromValue(status));
        ApiResponse<?> apiResponse = new ApiResponse<>(false, true, "", "");
        return ResponseEntity.ok(apiResponse);
    }
}
