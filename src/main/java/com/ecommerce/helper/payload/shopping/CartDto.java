package com.ecommerce.helper.payload.shopping;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CartDto implements Serializable {
    private Long cartId;

    private int quantity;
    private double totalCost;
    private Date createdAt;

    private ProductDto productDto;
}