package controllers.offers;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

public class PaymentController {
    private void processPayment() {
        try {
// Set your secret key here
            Stripe.apiKey = "sk_test_51OqZ3YLdtRvtorIBI6PHfTH7iGGb64aLqBG7z3jIEGlSmui5sNxJ3mjr8GsKxg3dDFP6L0O9dx5L3PbOwFhrhXRi00LLRcqQUf";

// Create a PaymentIntent with other payment details
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(1000L) // Amount in cents (e.g., $10.00)
                    .setCurrency("usd")
                    .build();

            PaymentIntent intent = PaymentIntent.create(params);

// If the payment was successful, display a success message
            System.out.println("Payment successful. PaymentIntent ID: " + intent.getId());
        } catch (StripeException e) {
// If there was an error processing the payment, display the error message
            System.out.println("Payment failed. Error: " + e.getMessage());
        }
    }
}
