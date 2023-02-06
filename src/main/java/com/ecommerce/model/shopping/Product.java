package com.ecommerce.model.shopping;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;

    private @NotNull String name;
    private @NotNull String description;
    private @NotNull String imageUrl;
    private @NotNull double price;

    /*relationship*/
    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;
    /*end of relationship*/
}