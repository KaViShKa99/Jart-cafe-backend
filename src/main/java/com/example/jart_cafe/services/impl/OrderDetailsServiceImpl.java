package com.example.jart_cafe.services.impl;


import com.example.jart_cafe.dto.CheckoutRequest;
import com.example.jart_cafe.dto.PurchaseItemDTO;
import com.example.jart_cafe.model.OrderDetails;
import com.example.jart_cafe.model.PurchaseItem;
import com.example.jart_cafe.repositories.OrderRepository;
import com.example.jart_cafe.services.OrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderDetailsServiceImpl implements OrderDetailsService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderDetailsServiceImpl(OrderRepository orderDetailsRepository) {
        this.orderRepository = orderDetailsRepository;
    }

    public OrderDetails saveOrder(CheckoutRequest checkoutRequest) {

        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setCustomerName(checkoutRequest.getCustomerName());
        orderDetails.setCustomerEmail(checkoutRequest.getCustomerEmail());
        List<PurchaseItem> items = checkoutRequest.getItems().stream()
                .map(item -> {
                    PurchaseItem purchaseItem = new PurchaseItem();
                    purchaseItem.setCategory(item.getCategory());
                    purchaseItem.setDesignerNote(item.getDesignerNote());
                    purchaseItem.setEachPrice(item.getEachPrice());
                    purchaseItem.setFigure(item.getFigure());
                    purchaseItem.setPhysicalArt(item.isPhysicalArt());
                    purchaseItem.setMaterialAndSize(item.getMaterialAndSize());
                    purchaseItem.setNumOfPersons(item.getNumOfPersons());
                    purchaseItem.setPrice(item.getPrice());
                    purchaseItem.setQuantity(item.getQuantity());
                    purchaseItem.setProductImage(item.getProductImage());
                    purchaseItem.setStyle(item.getStyle());
                    purchaseItem.setTotal(item.getTotal());
                    purchaseItem.setUploadedImage(item.getUploadedImage());
                    return purchaseItem;
                })
                .collect(Collectors.toList());
        orderDetails.setItems(items);

        System.out.println(orderDetails);

        return orderRepository.save(orderDetails);
    }

    @Override
    public List<CheckoutRequest> findAll() {
        List<OrderDetails> orderList = orderRepository.findAll();
        List<CheckoutRequest> checkoutRequestList =  new ArrayList<>();


        for (OrderDetails orderDetails : orderList){
            CheckoutRequest checkoutRequest = new CheckoutRequest();
            checkoutRequest.setCustomerEmail(orderDetails.getCustomerEmail());
            checkoutRequest.setCustomerName(orderDetails.getCustomerName());

            List<PurchaseItemDTO> purchaseItemDTOList = orderDetails.getItems().stream()
                    .map(this::convertPurchaseItemToPurchaseItemDTO)
                    .collect(Collectors.toList());

            checkoutRequest.setItems(purchaseItemDTOList);
            checkoutRequestList.add(checkoutRequest);
        }
        return checkoutRequestList;
    }

    // Method to convert PurchaseItem to PurchaseItemDTO
    private PurchaseItemDTO convertPurchaseItemToPurchaseItemDTO(PurchaseItem purchaseItem) {

        PurchaseItemDTO purchaseItemDTO = new PurchaseItemDTO();
        purchaseItemDTO.setCategory(purchaseItem.getCategory());
        purchaseItemDTO.setDesignerNote(purchaseItem.getDesignerNote());
        purchaseItemDTO.setEachPrice(purchaseItem.getEachPrice());
        purchaseItemDTO.setFigure(purchaseItem.getFigure());
        purchaseItemDTO.setPhysicalArt(purchaseItem.isPhysicalArt());
        purchaseItemDTO.setMaterialAndSize(purchaseItem.getMaterialAndSize());
        purchaseItemDTO.setNumOfPersons(purchaseItem.getNumOfPersons());
        purchaseItemDTO.setPrice(purchaseItem.getPrice());
        purchaseItemDTO.setQuantity(purchaseItem.getQuantity());
        purchaseItemDTO.setProductImage(purchaseItem.getProductImage());
        purchaseItemDTO.setStyle(purchaseItem.getStyle());
        purchaseItemDTO.setTotal(purchaseItem.getTotal());
        purchaseItemDTO.setUploadedImage(purchaseItem.getUploadedImage());
        return purchaseItemDTO;
    }

}
