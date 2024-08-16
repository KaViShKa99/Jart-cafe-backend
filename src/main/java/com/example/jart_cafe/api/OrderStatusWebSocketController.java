package com.example.jart_cafe.api;

import com.example.jart_cafe.dto.OrderStatusMessageDTO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class OrderStatusWebSocketController {

    @MessageMapping("/updateOrderStatus")
    @SendTo("/topic/orderStatus")
    public OrderStatusMessageDTO sendUpdate(OrderStatusMessageDTO orderStatusMessage) {
        return orderStatusMessage;
    }
}

