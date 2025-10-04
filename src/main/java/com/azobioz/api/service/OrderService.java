package com.azobioz.api.service;

import com.azobioz.api.dto.*;
import com.azobioz.api.module.Order;
import com.azobioz.api.module.Product;
import com.azobioz.api.module.ProductOrder;
import com.azobioz.api.module.User;
import com.azobioz.api.repository.OrderRepository;
import com.azobioz.api.repository.ProductRepository;
import com.azobioz.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public List<OrderResponse> getAllUserOrdersById(Long id) {
        List<Order> orders = orderRepository.findAllByUser_Id(id);

        return orders.stream()
                .map(order -> {
                    User user = order.getUser();
                    UserResponseForOrder userResponse = new UserResponseForOrder(
                            user.getId(),
                            user.getName(),
                            user.getEmail()
                    );

                    List<ProductResponse> productResponses = order.getProductOrders().stream()
                            .map(po -> new ProductResponse(
                                    po.getProduct().getId(),
                                    po.getProduct().getName(),
                                    po.getProduct().getPrice()
                            ))
                            .toList();
                    
                    return new OrderResponse(
                            order.getId(),
                            userResponse,
                            productResponses
                    );
                })
                .toList();
    }

    @Transactional
    public OrderResponse createOrder(Long id, OrderRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = new Order();
        order.setUser(user);

        List<ProductOrder> productOrders = request.products().stream()
                .map(p -> {
                    Product product;
                    if (p.id() != null) {
                        product = productRepository.findById(p.id())
                                .orElseThrow(() -> new RuntimeException("Product not found"));
                    }
                    else {
                        product = new Product();
                        product.setName(p.name());
                        product.setPrice(p.price());
                        productRepository.save(product);
                    }

                    ProductOrder productOrder = new ProductOrder();
                    productOrder.setOrder(order);
                    productOrder.setProduct(product);
                    return productOrder;
                })
                .toList();

        order.setProductOrders(productOrders);
        orderRepository.save(order);

        List<ProductResponse> productResponses = productOrders.stream()
                .map(po -> new ProductResponse(
                        po.getProduct().getId(),
                        po.getProduct().getName(),
                        po.getProduct().getPrice()
                ))
                .toList();

        UserResponseForOrder userResponse = new UserResponseForOrder(
                user.getId(),
                user.getName(),
                user.getEmail()
        );

        return new OrderResponse(
                order.getId(),
                userResponse,
                productResponses
        );

    }
}
