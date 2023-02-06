package com.ecommerce.model.shopping;

import com.ecommerce.model.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class WishList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long productId;
    private Date createdAt;

    /*relationship*/
    @ManyToOne(fetch = FetchType.EAGER)
    private Product product;

    @OneToOne(fetch = FetchType.EAGER)
    private User user;
    /*end of relationship*/
}