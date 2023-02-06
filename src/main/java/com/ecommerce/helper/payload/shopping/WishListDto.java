package com.ecommerce.helper.payload.shopping;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class WishListDto implements Serializable {
    private Long id;

    private Long userId;
    private Long productId;
    private Date createdAt;
}