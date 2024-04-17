package sample.cafekiosk.api.service.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sample.cafekiosk.api.service.product.response.ProductResponse;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.SellingType;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService {

  private final ProductRepository productRepository;

  public List<ProductResponse> getSellingProducts() {
    List<Product> products = productRepository.findBySellingTypeIn(SellingType.forDisplay());

    return products.stream()
        .map((ProductResponse::of))
        .collect(Collectors.toList());
  }
}