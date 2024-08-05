package com.example.jart_cafe.services.impl;


import com.example.jart_cafe.dto.CheckoutRequestDTO;
import com.example.jart_cafe.dto.CheckoutRequestDetailsDTO;
import com.example.jart_cafe.dto.PurchaseItemDTO;
import com.example.jart_cafe.model.OrderDetails;
import com.example.jart_cafe.model.PurchaseItem;
import com.example.jart_cafe.repositories.OrderRepository;
import com.example.jart_cafe.services.OrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderDetailsServiceImpl implements OrderDetailsService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderDetailsServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Long saveOrder(CheckoutRequestDTO checkoutRequestDTO) {
        OrderDetails orderDetails = new OrderDetails();
        updateOrderDetailsFromRequest(orderDetails, checkoutRequestDTO);
        return orderRepository.save(orderDetails).getId();
    }
    @Override
    public void updateTransaction(Long orderId) {
        System.out.println("orderId "+orderId);
        OrderDetails orderDetails = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
        orderDetails.setOrderTransaction(true);
        System.out.println("orderDetails "+orderDetails);
        orderRepository.save(orderDetails);
    }

    @Override
    public List<CheckoutRequestDetailsDTO> findAll() {
        return orderRepository.findByOrderTransactionTrue().stream()
                .map(this::convertOrderDetailsToCheckoutRequest)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<OrderDetails> findOrderById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public void deleteOrder(Long id) {
        System.out.println("delete order");
        orderRepository.findById(id).ifPresent(orderRepository::delete);
    }


    @Override
    public List<OrderDetails> getOrdersByCustomerEmail(String email) {
        System.out.println(email);
        System.out.println(orderRepository.findByCustomerEmailAndOrderTransactionTrue(email));
        return orderRepository.findByCustomerEmailAndOrderTransactionTrue(email);
    }

    @Override
    public void updateOrderStatus(Long orderId, Boolean newStatus) {
        OrderDetails orderDetails = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
        orderDetails.setOrderStatus(newStatus);
        orderRepository.save(orderDetails);
    }

    @Override
    public void updateOrderDate(Long orderId, Date newCompletedDate) {
        OrderDetails orderDetails = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
        orderDetails.setCompletedDate(newCompletedDate);
        orderRepository.save(orderDetails);
    }


    private void updateOrderDetailsFromRequest(OrderDetails orderDetails, CheckoutRequestDTO request) {
        orderDetails.setOrderTransaction(request.getOrderTransaction());
        orderDetails.setOrderStatus(request.getOrderStatus());
        orderDetails.setOrderedDate(request.getOrderedDate());
        orderDetails.setCompletedDate(request.getCompletedDate());
        orderDetails.setCustomerName(request.getCustomerName());
        orderDetails.setCustomerEmail(request.getCustomerEmail());
        List<PurchaseItem> items = request.getItems().stream()
                .map(this::convertDTOToPurchaseItem)
                .collect(Collectors.toList());
        orderDetails.setItems(items);
    }

    private PurchaseItem convertDTOToPurchaseItem(PurchaseItemDTO dto) {
        PurchaseItem item = new PurchaseItem();
        mapDTOToPurchaseItem(dto, item);
        return item;
    }

    private void mapDTOToPurchaseItem(PurchaseItemDTO dto, PurchaseItem item) {
        item.setCategory(dto.getCategory());
        item.setArtworkId(dto.getArtworkId());
        item.setDesignerNote(dto.getDesignerNote());
        item.setEachPrice(dto.getEachPrice());
        item.setFigure(dto.getFigure());
        item.setPhysicalArt(dto.isPhysicalArt());
        item.setMaterialAndSize(dto.getMaterialAndSize());
        item.setNumOfPersons(dto.getNumOfPersons());
        item.setPrice(dto.getPrice());
        item.setQuantity(dto.getQuantity());
        item.setProductImage(dto.getProductImage());
        item.setStyle(dto.getStyle());
        item.setTotal(dto.getTotal());
        item.setUploadedImage(dto.getUploadedImage());
    }

    private CheckoutRequestDetailsDTO convertOrderDetailsToCheckoutRequest(OrderDetails orderDetails) {
        CheckoutRequestDetailsDTO request = new CheckoutRequestDetailsDTO();
        request.setOrderId(orderDetails.getId());
        request.setOrderStatus(orderDetails.getOrderStatus());
        request.setCustomerEmail(orderDetails.getCustomerEmail());
        request.setCustomerName(orderDetails.getCustomerName());
        request.setOrderedDate(orderDetails.getOrderedDate());
        request.setCompletedDate(orderDetails.getCompletedDate());
        List<PurchaseItemDTO> itemDTOs = orderDetails.getItems().stream()
                .map(this::convertPurchaseItemToPurchaseItemDTO)
                .collect(Collectors.toList());
        request.setItems(itemDTOs);
        return request;
    }

    private PurchaseItemDTO convertPurchaseItemToPurchaseItemDTO(PurchaseItem item) {
        PurchaseItemDTO dto = new PurchaseItemDTO();
        mapPurchaseItemToDTO(item, dto);
        return dto;
    }

    private void mapPurchaseItemToDTO(PurchaseItem item, PurchaseItemDTO dto) {
        dto.setCategory(item.getCategory());
        dto.setDesignerNote(item.getDesignerNote());
        dto.setEachPrice(item.getEachPrice());
        dto.setFigure(item.getFigure());
        dto.setPhysicalArt(item.isPhysicalArt());
        dto.setMaterialAndSize(item.getMaterialAndSize());
        dto.setNumOfPersons(item.getNumOfPersons());
        dto.setPrice(item.getPrice());
        dto.setQuantity(item.getQuantity());
        dto.setProductImage(item.getProductImage());
        dto.setStyle(item.getStyle());
        dto.setTotal(item.getTotal());
        dto.setUploadedImage(item.getUploadedImage());
    }

}



