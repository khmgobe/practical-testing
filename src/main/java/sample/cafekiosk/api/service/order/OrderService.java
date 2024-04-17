package sample.cafekiosk.api.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sample.cafekiosk.api.controller.order.request.OrderCreateRequest;
import sample.cafekiosk.api.service.order.response.OrderResponse;
import sample.cafekiosk.spring.domain.order.Order;
import sample.cafekiosk.spring.domain.order.OrderRepository;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final ProductRepository productRepository;
  private final OrderRepository orderRepository;

  public OrderResponse createOrder(OrderCreateRequest request, LocalDateTime registeredDateTime) {
    List<String> productNumbers = request.getProductNumbers();

    List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);

    Order order = Order.create(products, registeredDateTime);
    Order saveOrder = orderRepository.save(order);

    return OrderResponse.of(saveOrder);

  }
}