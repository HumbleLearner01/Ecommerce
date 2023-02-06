package com.ecommerce.service.shopping;

import com.ecommerce.model.shopping.Product;
import com.ecommerce.model.shopping.WishList;
import com.ecommerce.model.user.User;
import com.ecommerce.repository.shopping.ProductRepository;
import com.ecommerce.repository.shopping.WishListRepository;
import com.ecommerce.repository.user.UserRepository;
import com.ecommerce.service.user.UserService;
import com.ecommerce.helper.exception.AuthenticationFailedException;
import com.ecommerce.helper.exception.ResourceNotFoundException;
import com.ecommerce.helper.payload.shopping.WishListDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Transactional
@AllArgsConstructor
public class WishListService {
    private final WishListRepository wishListRepo;
    private final UserRepository userRepo;
    private final ProductRepository productRepo;
    private final UserService userService;
    private final ModelMapper mapper;
    private final Date date;

    //add a product in wishlist
    public WishListDto save(Long productId) {
        User user = userService.currentUser();
        if (userService.isLoggedIn(user)) {
            Product product = productRepo.findById(Math.toIntExact(productId))
                    .orElseThrow(() -> new ResourceNotFoundException("Product", "ProductID", productId.toString()));
            WishList wishList = WishList.builder()
                    .productId(productId)
                    .userId(user.getUserId())
                    .product(product)
                    .createdAt(date)
                    .user(user)
                    .build();
            return mapper.map(wishListRepo.save(wishList), WishListDto.class);
        } else throw new AuthenticationFailedException("You should first login!");
    }

    //delete a product from wishlist
    public void delete(Long id) {
        User user = userService.currentUser();
        if (userService.isLoggedIn(user)) {
            WishList wishList = wishListRepo.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Product", "ProductID", id.toString()));
            wishListRepo.delete(wishList);
        }
    }

    //get all the products of wishlist
    public List<WishListDto> findAll() {
        User user = userService.currentUser();
        return wishListRepo.findAllByUserOrderByCreatedAtDesc(user)
                .stream().map(wishListProduct ->
                        mapper.map(wishListProduct, WishListDto.class)).collect(toList());
    }
}