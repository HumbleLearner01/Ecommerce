package com.ecommerce.helper.payload.shopping;

import com.ecommerce.helper.customvalidation.ValidateCardNumber;
import com.ecommerce.helper.customvalidation.ValidateCvv2;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

@Data
public class CheckoutDto implements Serializable {
    private Long checkoutId;

    @NotEmpty
    private String shippingAddress;
    @NotEmpty
    private String paymentMethod;
    private double totalCost;
    @CreationTimestamp
    private Date purchaseDate;
    private String status;
    @NotEmpty
    @ValidateCardNumber
    private String cardNumber;
    @NotEmpty
    @ValidateCvv2
    private String cvv2;
}