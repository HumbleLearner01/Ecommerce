package com.ecommerce.service.shopping;

import com.ecommerce.helper.Constants;
import com.ecommerce.helper.enums.OrderStatus;
import com.ecommerce.helper.exception.PaymentAlreadyDoneException;
import com.ecommerce.helper.exception.PaymentNotValidException;
import com.ecommerce.helper.payload.shopping.CheckoutDto;
import com.ecommerce.model.shopping.Cart;
import com.ecommerce.model.shopping.Checkout;
import com.ecommerce.model.shopping.Order;
import com.ecommerce.model.user.User;
import com.ecommerce.repository.shopping.CheckoutRepository;
import com.ecommerce.repository.shopping.OrderRepository;
import com.ecommerce.service.user.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class CheckoutService {
    private final CheckoutRepository checkoutRepo;
    private final OrderRepository orderRepo;
    private final UserService userService;
    private final CartService cartService;
    private final OrderService orderService;
    private final ModelMapper mapper;

    //purchase order
    @Transactional
    public CheckoutDto purchaseOrder(CheckoutDto checkoutDto) {
        User user = userService.currentUser();

        boolean hasAlreadyPurchased = orderRepo.existsOrderByUserUserId(user.getUserId());
        if (hasAlreadyPurchased) {
            log.info("user has already made a purchase!!!");
            throw new PaymentAlreadyDoneException("Payment has been already registered!");
        }

        Checkout checkout = mapper.map(checkoutDto, Checkout.class);
        double totalCost = calculateTotalCost(user.getCart());
        checkoutDto.setTotalCost(totalCost);
        checkoutDto.setStatus(OrderStatus.PAID.toString());

        log.info("adding to the orders list...");
        Order order = orderService.saveOrder(checkout);

        if (Constants.CARD_NUMBER.contains(checkout.getCardNumber().replaceAll("[^0-9]", "")) &&
            Constants.CVV2.contains(checkout.getCvv2().replaceAll("[^0-9]", ""))) {
            log.info("CardNumber & Cvv2 are valid. Proceeding to purchase the items...");
            checkout.setOrderStatus(OrderStatus.PAID);
            checkout.setOrder(order);
            log.info("purchased and added to \"Order\" successfully");
            checkout = checkoutRepo.save(checkout);
        } else {
            log.info("CardNumber or Cvv2 not valid");
            throw new PaymentNotValidException("CardNumber", checkout.getCardNumber(), "Cvv2", checkout.getCvv2());
        }

        if (checkout.getOrderStatus().equals(OrderStatus.PAID)) {
            cartService.clearCart(user);
            log.info("cart has been cleared up successfully!");
        }

        checkout.setCardNumber(checkout.getCardNumber().replaceAll("[^0-9]",""));
        checkout.setCvv2(checkout.getCvv2().replaceAll("[^0-9]",""));

        return mapper.map(checkout, CheckoutDto.class);
    }

    //calculate total cost
    //TODO: totalCost is ending up as 0.0
    private double calculateTotalCost(List<Cart> cartItems) {
        double totalCost = 0;
        for (Cart cart : cartItems)
            totalCost += cart.getProduct().getPrice() * cart.getQuantity();
        log.info("-------------------->" + totalCost + "<--------------------");
        return totalCost;
    }
}