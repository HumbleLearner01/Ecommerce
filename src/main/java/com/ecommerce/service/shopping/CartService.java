package com.ecommerce.service.shopping;

import com.ecommerce.helper.exception.AuthenticationFailedException;
import com.ecommerce.helper.exception.ResourceNotFoundException;
import com.ecommerce.helper.payload.shopping.CartDto;
import com.ecommerce.helper.payload.shopping.ProductDto;
import com.ecommerce.model.shopping.Cart;
import com.ecommerce.model.shopping.Product;
import com.ecommerce.model.user.User;
import com.ecommerce.repository.shopping.CartRepository;
import com.ecommerce.service.user.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class CartService {
    private final CartRepository cartRepo;
    private final UserService userService;
    private final ProductService productService;
    private final ModelMapper mapper;

    //add to cart
    @Transactional
    public CartDto save(CartDto cartDto) {
        User user = userService.currentUser();
        if (userService.isLoggedIn(user)) {
            Product product = productService.findById(cartDto.getProductDto().getProductId());
            Cart savedCart = cartRepo.save(new Cart(user, product, cartDto.getQuantity()));
            return mapper.map(savedCart, CartDto.class);
        } else throw new AuthenticationFailedException("Authentication failed!");
    }

    //delete an item from cart
    @Transactional
    public void delete(Long cartItemId) {
        if (userService.isLoggedIn(userService.currentUser())) {
            Cart cart = cartRepo.findById(cartItemId)
                    .orElseThrow(() -> new ResourceNotFoundException("CartItem", "CartItemID", cartItemId.toString()));
            cartRepo.delete(cart);
        }
    }

    //get all items from the cart
    @Transactional
    public List<CartDto> findAll() {
        User user = userService.currentUser();
        if (userService.isLoggedIn(user)) {
            double totalCost = 0;
            List<Cart> cartItems = cartRepo.findAllByUserOrderByCreatedAtDesc(user);
            for (Cart cart : cartItems)
                totalCost += cart.getProduct().getPrice() * cart.getQuantity();
            double finalTotalCost = totalCost;
            return cartItems.stream().map(cart -> {
                CartDto cartDto = mapper.map(cart, CartDto.class);
                ProductDto productDto = mapper.map(cart.getProduct(), ProductDto.class);

                cartDto.setProductDto(productDto);
                cartDto.setTotalCost(finalTotalCost);
                return cartDto;
            }).collect(toList());
        } else throw new AuthenticationFailedException("Authentication failed!");
    }

    //update items in the cart i.e. update the quantity
    @Transactional
    public CartDto save(Map<String, Integer> updatePayload, Long cartItemId) {
        Cart cart;
        if (userService.isLoggedIn(userService.currentUser())) {
            cart = cartRepo.findById(cartItemId)
                    .orElseThrow(() -> new ResourceNotFoundException("CartItem", "CartItemID", cartItemId.toString()));
            cart.setQuantity(updatePayload.get("quantity"));
            cart = cartRepo.save(cart);

            double totalCost = cart.getProduct().getPrice() * cart.getQuantity();
            CartDto cartDto = mapper.map(cart, CartDto.class);
            ProductDto productDto = mapper.map(cart.getProduct(), ProductDto.class);
            cartDto.setProductDto(productDto);
            cartDto.setTotalCost(totalCost);

            return cartDto;
        } else throw new AuthenticationFailedException("Authentication failed!");
    }

    /*---------------------------helping methods-------------------------*/

    //find all by user
    //TODO: implement the endpoint or I might have wanted to use it inside another method.
    @Transactional
    public List<Cart> getAllItemsInCart(Long userId) {
        Optional<Cart> cartItemsByUser = cartRepo.findAllByUserUserIdOrderByCreatedAtDesc(userId);
        return (cartItemsByUser.isPresent()) ? cartItemsByUser.stream().collect(Collectors.toList()) : new ArrayList<>();
    }

    //clear the cart after purchasing is done
    //TODO: It is not clearing the cart
    @Transactional
    public void clearCart(User user) {
        List<Cart> items = user.getCart();
        log.debug("deleting cart items {}", items);
        try {
            cartRepo.deleteAll(items);
            log.debug("deleted from cart successfully!");
        } catch (Exception e) {
            log.debug("could not clear the cart items: {}", e.getMessage());
        }
    }
}