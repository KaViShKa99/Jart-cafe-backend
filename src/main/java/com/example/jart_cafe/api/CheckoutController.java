package com.example.jart_cafe.api;
import com.example.jart_cafe.dto.CheckoutRequestDTO;
import com.example.jart_cafe.model.OrderDetails;
import com.example.jart_cafe.services.OrderDetailsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.StripeObject;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
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

//
//    @PostMapping("/create-session")
//    public Map<String, Object> createCheckoutSession(@RequestBody CheckoutRequestDTO checkoutRequestDTO) throws StripeException, JsonProcessingException {
//
//        if (checkoutRequestDTO.getItems() == null || checkoutRequestDTO.getItems().isEmpty()) {
//            throw new IllegalArgumentException("Items list cannot be null or empty");
//        }
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        String metadataJson = objectMapper.writeValueAsString(checkoutRequestDTO);
//
//
//        List<SessionCreateParams.LineItem> lineItems = checkoutRequestDTO.getItems().stream()
//                .map(item -> {
//                    String productName = item.getCategory();
//                    String imageUrl = item.getProductImage().get(0);
//
//
//                    return SessionCreateParams.LineItem.builder()
//                            .setQuantity(item.getQuantity())
//                            .setPriceData(
//                                    SessionCreateParams.LineItem.PriceData.builder()
//                                            .setCurrency("usd")
//                                            .setUnitAmount((long) ((item.getEachPrice() + item.getPrice()) * 100)) // Convert to cents
//                                            .setProductData(
//                                                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
//                                                            .setName(productName)
//                                                            .addImage(imageUrl)
//                                                            .build())
//                                            .build())
//                            .build();
//                })
//                .collect(Collectors.toList());
//
//        SessionCreateParams params = SessionCreateParams.builder()
//                .addPaymentMethodType(
//                        SessionCreateParams.PaymentMethodType.CARD)
//                .setMode(SessionCreateParams.Mode.PAYMENT)
//                .setSuccessUrl(frontendBaseUrl+"/success")
//                .setCancelUrl(frontendBaseUrl+"/cancel")
//                .putMetadata("checkoutRequestDTO", metadataJson) // Add the serialized DTO as metadata
//                .addAllLineItem(lineItems)
//                .build();
//
//
//        Session session = Session.create(params);
//
////        orderDetailsService.saveOrder(checkoutRequestDTO);
//
//        Map<String, Object> responseData = new HashMap<>();
//        responseData.put("id", session.getId());
//        return responseData;
//    }
//
//
//    @PostMapping("/webhook")
//    public ResponseEntity<String> handleStripeWebhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {
//        try {
//            Event event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
////            System.out.println("Received payload: " + payload);
//            System.out.println("Received event type: " + event.getType());
//
//            if ("payment_intent.succeeded".equals(event.getType())) {
//                System.out.println("Payment Intent succeeded");
//
//
//
//                ObjectMapper objectMapper = new ObjectMapper();
//                EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
//                StripeObject stripeObject = null;
//                System.out.println(dataObjectDeserializer.getObject().isPresent());
//
//                if (dataObjectDeserializer.getObject().isPresent()) {
//                    stripeObject = dataObjectDeserializer.getObject().get();
//                } else {
//                    System.out.println("Version mismatch");
//                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid event data");
//                }
//
//                if (stripeObject instanceof PaymentIntent paymentIntent) {
//                    System.out.println(paymentIntent);
//
////                    String sessionId = paymentIntent.getMetadata().get("session_id");
////                    Session session = Session.retrieve(sessionId);
//
//
////                    // Extract relevant details from the payment intent
////                    String paymentIntentId = paymentIntent.getId();
////                    String customerEmail = paymentIntent.getReceiptEmail();
////                    Long amountReceived = paymentIntent.getAmountReceived();
////
////                    // Retrieve session information if needed
////                    Session session = Session.retrieve(paymentIntent.getClientSecret());
////                    String sessionId = session.getId(); // Or however you store and retrieve session information
//                    // Fetch or create CheckoutRequestDTO
////                    CheckoutRequestDTO checkoutRequestDTO = new CheckoutRequestDTO();
////                    checkoutRequestDTO.setSessionId(sessionId);
////                    checkoutRequestDTO.setCustomerEmail(customerEmail);
////                    checkoutRequestDTO.setOrderedDate(new Date()); // Set this to the payment date or relevant date
////                    checkoutRequestDTO.setOrderStatus(true); // Or set based on the order status
////                    checkoutRequestDTO.setCompletedDate(new Date()); // Set appropriately
////                    // Populate items list as needed
////                    // checkoutRequestDTO.setItems(...);
////
////                    // Save the order details
////                    orderDetailsService.saveOrder(checkoutRequestDTO);
//
////                    System.out.println("Order saved successfully for payment intent ID: " + paymentIntentId);
//                }
//            }
//
//            return ResponseEntity.ok().build();
//        } catch (SignatureVerificationException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid signature");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing webhook");
//        }
//    }



    @PostMapping("/create-session")
    public Map<String, Object> createCheckoutSession(@RequestBody CheckoutRequestDTO checkoutRequestDTO) throws StripeException, JsonProcessingException {

        long expirationTime = Instant.now().plusSeconds(30 * 60).getEpochSecond();

        if (checkoutRequestDTO.getItems() == null || checkoutRequestDTO.getItems().isEmpty()) {
            throw new IllegalArgumentException("Items list cannot be null or empty");
        }

        // Save temporary order data
        Long tempOrderId = orderDetailsService.saveOrder(checkoutRequestDTO);

        System.out.println(tempOrderId);
        System.out.println("temp id ");
        List<SessionCreateParams.LineItem> lineItems = checkoutRequestDTO.getItems().stream()
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
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(frontendBaseUrl + "/success")
                .setCancelUrl(frontendBaseUrl + "/cancel?session_id={CHECKOUT_SESSION_ID}")
                .putMetadata("tempOrderId", tempOrderId.toString()) // Save temporary order ID as metadata
                .addAllLineItem(lineItems)
                .setExpiresAt(expirationTime)
                .build();

        Session session = Session.create(params);

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("id", session.getId());
        return responseData;
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> handleStripeWebhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {
        try {
            Event event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
            String eventType = event.getType();


            System.out.println("Received event: " + eventType);
//            System.out.println("Payload: " + payload);


            EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
            StripeObject stripeObject = dataObjectDeserializer.getObject().orElse(null);

            System.out.println("stripe object");

            if (stripeObject instanceof Session session) {
                Long tempOrderId = Long.valueOf(session.getMetadata().get("tempOrderId"));
                System.out.println("check webhooks ");
                System.out.println(tempOrderId);
                switch (eventType) {
                    case "checkout.session.completed":
                    case "payment_intent.succeeded":
                        System.out.println("completed");
//                        OrderDetails permOrder = orderDetailsService.findOrderById(tempOrderId)
//                                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
//                        permOrder.setOrderTransaction(true);
                        orderDetailsService.updateTransaction(tempOrderId);
                        break;

                    case "checkout.session.expired":
                        orderDetailsService.deleteOrder(tempOrderId);
                        break;

                    default:
                        break;
                }


            }

            return ResponseEntity.ok().build();
        } catch (SignatureVerificationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid signature");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing webhook");
        }


    }

    @PostMapping("/cancel")
    public ResponseEntity<String> handleCancel(@RequestParam("session_id") String sessionId) {
        try {
            Session session = Session.retrieve(sessionId);

            Long tempOrderId = Long.valueOf(session.getMetadata().get("tempOrderId"));
            System.out.println("temp data id "+tempOrderId);
            orderDetailsService.deleteOrder(tempOrderId);

            return ResponseEntity.ok("Payment was canceled.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while handling the cancellation.");
        }
    }


}

