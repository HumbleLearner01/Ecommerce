package com.ecommerce.model.shopping;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryId;

    private @NotBlank String categoryName;
    private @NotBlank String description;
    private @NotBlank String imageUrl;

    /*relationship*/
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "category")
    @JsonIgnore
    private List<Product> products = new ArrayList<>();
    /*end of relationship*/
}
