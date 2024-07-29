package com.example.jart_cafe.api;
import com.example.jart_cafe.dto.CheckoutRequest;
import com.example.jart_cafe.dto.PurchaseItemDTO;
import com.example.jart_cafe.model.Material;
import com.example.jart_cafe.model.OrderDetails;
import com.example.jart_cafe.model.PurchaseItem;
import com.example.jart_cafe.services.OrderDetailsService;
import com.example.jart_cafe.services.impl.OrderDetailsServiceImpl;
import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/checkout")
//@RequestMapping("/api")
public class CheckoutController {

    private final OrderDetailsService orderDetailsService;

    @Autowired
    public CheckoutController(OrderDetailsService orderDetailsService) {
        this.orderDetailsService = orderDetailsService;
    }


    @Value("${frontend.baseurl}")
    private String frontendBaseUrl;

    @Value("${stripe.webhook.secret}")
    private String endpointSecret;

    @PostMapping("/create-session")
    public Map<String, Object> createCheckoutSession(@RequestBody CheckoutRequest checkoutRequest) throws StripeException {

        System.out.println(checkoutRequest.getCustomerEmail());
        System.out.println(checkoutRequest.getCustomerName());
        System.out.println(checkoutRequest.getItems());

        if (checkoutRequest.getItems() == null || checkoutRequest.getItems().isEmpty()) {
            throw new IllegalArgumentException("Items list cannot be null or empty");
        }

        List<SessionCreateParams.LineItem> lineItems = checkoutRequest.getItems().stream()
                .map(item -> {
                    String productName = item.getCategory();
                    String imageUrl = item.getProductImage().get(0);


                    return SessionCreateParams.LineItem.builder()
                            .setQuantity(item.getQuantity())
                            .setPriceData(
                                    SessionCreateParams.LineItem.PriceData.builder()
                                            .setCurrency("usd")
                                            .setUnitAmount((long) ((item.getEachPrice() + item.getPrice()) * 100)) // Convert to cents
                                            .setProductData(
                                                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                            .setName(productName)
                                                            .addImage(imageUrl)
                                                            .build())
                                            .build())
                            .build();
                })
                .collect(Collectors.toList());

        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(
                        SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(frontendBaseUrl+"/success")
                .setCancelUrl(frontendBaseUrl+"/cancel")
                .setPaymentIntentData(
                        SessionCreateParams.PaymentIntentData.builder()
                                .setSetupFutureUsage(SessionCreateParams.PaymentIntentData.SetupFutureUsage.OFF_SESSION)
                                .build())
                .addAllLineItem(lineItems)
                .build();


        Session session = Session.create(params);


        orderDetailsService.saveOrder(checkoutRequest);

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("id", session.getId());
        return responseData;
    }

//    @PostMapping("/webhook")
//    public void handleStripeWebhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {
//        Event event;
//        try {
//            event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
//        } catch (SignatureVerificationException e) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid signature", e);
//        }
//
//        if ("checkout.session.completed".equals(event.getType())) {
//            EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
//            Session session = (Session) dataObjectDeserializer.getObject().orElseThrow(() ->
//                    new ResponseStatusException(HttpStatus.BAD_REQUEST, "Deserialization failed"));
//
//            // Retrieve the checkout session to get customer and order details
//            String sessionId = session.getId();
//            CheckoutRequest checkoutRequest = getCheckoutRequestFromSession(sessionId); // Implement this method to retrieve the checkoutRequest using sessionId
//
//            orderDetailsService.saveOrder(checkoutRequest);
//        }
//    }
//
//    // Implement this method to retrieve the checkoutRequest using sessionId
//    private CheckoutRequest getCheckoutRequestFromSession(String sessionId) {
//        // Retrieve your checkoutRequest from your data store using sessionId
//        return new CheckoutRequest();
//    }
}

