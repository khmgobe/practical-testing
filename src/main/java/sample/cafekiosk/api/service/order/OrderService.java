package sample.cafekiosk.api.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sample.cafekiosk.api.controller.order.request.OrderCreateServiceRequest;
import sample.cafekiosk.api.service.order.response.OrderResponse;
import sample.cafekiosk.spring.domain.order.Order;
import sample.cafekiosk.spring.domain.order.OrderRepository;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductType;
import sample.cafekiosk.spring.domain.stock.Stock;
import sample.cafekiosk.spring.domain.stock.StockRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class OrderService {

  private final ProductRepository productRepository;
  private final OrderRepository orderRepository;

  private final StockRepository stockRepository;

  public OrderResponse createOrder(OrderCreateServiceRequest request, LocalDateTime registeredDateTime) {
    List<String> productNumbers = request.getProductNumbers();

    List<Product> products = findProductBy(productNumbers);

      deductStockQuantities(products);


      Order order = Order.create(products, registeredDateTime);
    Order saveOrder = orderRepository.save(order);
    return OrderResponse.of(saveOrder);

  }

    private void deductStockQuantities(List<Product> products) {
        List<String> stockProductNumbers = extractsStockProductNumbers(products);

        Map<String, Stock> stockMap = createStockMapBy(stockProductNumbers);

        Map<String, Long> productCountingMap = createCountingMapBy(stockProductNumbers);

        // 재고 차감 시도
        for (String stockProductNumber : new HashSet<>(stockProductNumbers)) {
            Stock stock = stockMap.get(stockProductNumber);
            int quantity = productCountingMap.get(stockProductNumber).intValue();

            if(stock.isQuantityLessThen(quantity)) {
                throw new IllegalArgumentException("재고가 부족한 상품이 있습니다.");
            }
            stock.deductQuantity(quantity);
        }
    }

    private static Map<String, Long> createCountingMapBy(List<String> stockProductNumbers) {
        // 상품별 counting
        return stockProductNumbers.stream()
                .collect(Collectors.groupingBy(p -> p, Collectors.counting()));
    }

    private Map<String, Stock> createStockMapBy(List<String> stockProductNumbers) {
        // 재고 엔티티 조회
        List<Stock> stocks = stockRepository.findAllByProductNumberIn(stockProductNumbers);

        Map<String, Stock> stockMap =
            stocks.stream().collect(Collectors.toMap(Stock::getProductNumber, s -> s));
        return stockMap;
    }

    private static List<String> extractsStockProductNumbers(List<Product> products) {
        // 재고 차감 체크가 필요한 상품들 filter
        List<String> stockProductNumbers =
            products.stream().filter(product -> ProductType.containsStockType(product.getProductType()))
                .map(Product::getProductNumber).collect(Collectors.toList());
        return stockProductNumbers;
    }

    private List<Product> findProductBy(List<String> productNumbers) {
    List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);
    Map<String, Product> productMap =
        products.stream().collect(Collectors.toMap(Product::getProductNumber, p -> p));

    return
        productNumbers.stream()
            .map(productMap::get)
            .collect(Collectors.toList());
  }
}