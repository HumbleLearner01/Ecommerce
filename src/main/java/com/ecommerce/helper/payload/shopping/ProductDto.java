package com.ecommerce.helper.payload.shopping;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class ProductDto implements Serializable {
    private @NotNull int productId;

    private @NotNull String name;
    private @NotNull String description;
    private @NotNull String imageUrl;
    private @NotNull double price;
}