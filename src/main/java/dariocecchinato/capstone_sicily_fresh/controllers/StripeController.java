package dariocecchinato.capstone_sicily_fresh.controllers;
import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import dariocecchinato.capstone_sicily_fresh.payloads.StripePayloadDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/stripe")
public class StripeController {

    @Value("${Stripe.apiKey}")
    private String stripeApiKey;

    @PostMapping("/create-checkout-session")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'FORNITORE', 'CLIENTE')")
    public Map<String, String> createCheckoutSession(@RequestBody StripePayloadDTO payload) throws Exception {

        Stripe.apiKey = stripeApiKey;
        String YOUR_DOMAIN = "http://localhost:5173";

        String price = String.valueOf(payload.price());

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(YOUR_DOMAIN + "/success")
                .setCancelUrl(YOUR_DOMAIN + "/cancel")
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPrice(price)
                                .build()
                )
                .build();

        Session session = Session.create(params);


        Map<String, String> responseData = new HashMap<>();
        responseData.put("url", session.getUrl());

        return responseData;
    }
}