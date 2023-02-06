package com.ecommerce.service.shopping;

import com.ecommerce.helper.enums.OrderStatus;
import com.ecommerce.helper.exception.OrderCancellationException;
import com.ecommerce.helper.exception.ResourceNotFoundException;
import com.ecommerce.helper.payload.shopping.OrderDto;
import com.ecommerce.model.shopping.Checkout;
import com.ecommerce.model.shopping.Order;
import com.ecommerce.model.user.User;
import com.ecommerce.repository.shopping.CheckoutRepository;
import com.ecommerce.repository.shopping.OrderRepository;
import com.ecommerce.repository.user.UserRepository;
import com.ecommerce.service.mail.Email;
import com.ecommerce.service.mail.MailContentBuilder;
import com.ecommerce.service.mail.MailService;
import com.ecommerce.service.user.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class OrderService {
    private final OrderRepository orderRepo;
    private final UserRepository userRepo;
    private final CheckoutRepository checkoutRepo;
    private final MailService mailService;
    private final UserService userService;
    private final MailContentBuilder contentBuilder;
    private final ModelMapper mapper;

    //save the items in the order after purchasing
    @Transactional
    public Order saveOrder(Checkout checkout) {
        Order order = Order.builder()
                .paymentMethod(checkout.getPaymentMethod())
                .shippingAddress(checkout.getShippingAddress())
                .totalPrice(checkout.getTotalCost())
                .status(OrderStatus.PAID)
                .purchaseTime(LocalDateTime.now())
                .user(userService.currentUser())
                .amountPurchased(checkout.getTotalCost())
                .build();
        return orderRepo.save(order);
    }

    //cancel order
    @Transactional
    public void cancelOrder(Long orderId) {
        LocalDateTime now = LocalDateTime.now();
        //TODO: order cannot be cancelled after 5 minutes, change the time to hour -> now.minusHour(6);
        LocalDateTime minutesAgo = now.minusMinutes(5);
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "OrderId", orderId.toString()));
        if (order.getPurchaseTime().isBefore(minutesAgo)) {
            log.info("Order can no longer be cancelled. " + minutesAgo.format(DateTimeFormatter.ofPattern("mm")) + " minutes have passed.");
            throw new OrderCancellationException("Order can no longer be cancelled. " + minutesAgo.format(DateTimeFormatter.ofPattern("mm")) + " minutes have been passed.");
        }
        order.setStatus(OrderStatus.CANCELLED);
        setCheckoutStatus(orderId);
        orderRepo.save(mapper.map(order, Order.class));
        log.info("your order has been cancelled");

        User user = mapper.map(order.getUser(), User.class);
        user.setBalance(user.getBalance() + order.getTotalPrice());
        userRepo.save(user);
        log.info("your balance have been updated, please check your bank account");

        String body = contentBuilder.build("'" + order.getTotalPrice() + "' has been transferred to your account. " +
                                           "Please check your balance and if there was any mistake, " +
                                           "please feel free to contact our admin and support manager : learn.mike.helloworld@gmail.com");
        mailService.sendEmail(new Email(
                "Order Cancellation",
                user.getEmail(),
                body
        ));
    }

    //get all the orders if the order is not cancelled and the status is PAID
    @Transactional
    public List<OrderDto> getOrderItems(List<Order> orders) {
        Optional<Order> orderByStatus = Optional.empty();
        for (Order order : orders) {
            orderByStatus = orderRepo.findByStatusAndOrderId(OrderStatus.PAID, order.getOrderId());
        }
        return orderByStatus
                .stream().map(ordrs ->
                        mapper.map(ordrs, OrderDto.class)).collect(toList());
    }

    //set the status of checkout as cancelled after cancelling the order
    private void setCheckoutStatus(Long orderId) {
        Checkout byOrderOrderId = checkoutRepo.findByOrderOrderId(orderId);
        byOrderOrderId.setOrderStatus(OrderStatus.CANCELLED);
    }
}