package by.pavvel.order.controller;

import by.pavvel.order.service.OrderService;
import by.pavvel.order.model.OrderRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public void createOrder(@RequestBody OrderRequest orderRequest) {
        log.info("new order request {}", orderRequest);
        orderService.createOrder(orderRequest);
    }
}
