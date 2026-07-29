package com.grocery.portal;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class OrderController {

    private final OrderService orderService;
    private final OrderRepository orderRepository;

    public OrderController(OrderService orderService, OrderRepository orderRepository) {
        this.orderService = orderService;
        this.orderRepository = orderRepository;
    }

    @PostMapping("/checkout/{customerId}")
    public Order checkout(@PathVariable int customerId,
                          @RequestParam(required = false) String discountCode,
                          @RequestParam(defaultValue = "STANDARD") DeliveryOption deliveryOption) {
        return orderService.checkout(customerId, discountCode, deliveryOption);
    }

    @GetMapping("/orders/{customerId}")
    public List<Order> orderHistory(@PathVariable int customerId) {
        return orderRepository.findByCustomerId(customerId);
    }
}