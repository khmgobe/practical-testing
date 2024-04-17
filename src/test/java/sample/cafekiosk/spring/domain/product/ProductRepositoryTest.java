package sample.cafekiosk.spring.domain.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static sample.cafekiosk.spring.domain.product.SellingType.*;

//@SpringBootTest
@DataJpaTest
@ActiveProfiles("test")
class ProductRepositoryTest {

  @Autowired
  private ProductRepository productRepository;

  @DisplayName("원하는 판매 상태를 가진 상품들을 조회한다.")
  @Test
  void findByProductSellingTypeIn() {

      // given
    Product product1 = Product.builder().productNumber("001").productType(ProductType.HANDMADE)
        .sellingType(SELLING).name("아메리카노").price(4000).build();

    Product product2 = Product.builder().productNumber("002").productType(ProductType.HANDMADE)
        .sellingType(HOLD).name("카페라떼").price(4500).build();

    Product product3 = Product.builder().productNumber("003").productType(ProductType.HANDMADE)
        .sellingType(STOP_SELLING).name("팥빙수").price(7000).build();

    productRepository.saveAll(List.of(product1, product2, product3));

      // when
      List<Product> products = productRepository.findAllBySellingTypeIn(List.of(SELLING, HOLD));

      // then
    assertThat(products).hasSize(2)
        .extracting("productNumber", "name", "sellingType")
        .containsExactlyInAnyOrder(
        tuple("001", "아메리카노", SELLING),
        tuple("002", "카페라떼", HOLD)
    );

  }

  @DisplayName("상품번호 리스트로 상품들을 조회한다.")
  @Test
  void findAllByProductNumberIn() {

    // given
    Product product1 = Product.builder().productNumber("001").productType(ProductType.HANDMADE)
        .sellingType(SELLING).name("아메리카노").price(4000).build();

    Product product2 = Product.builder().productNumber("002").productType(ProductType.HANDMADE)
        .sellingType(HOLD).name("카페라떼").price(4500).build();

    Product product3 = Product.builder().productNumber("003").productType(ProductType.HANDMADE)
        .sellingType(STOP_SELLING).name("팥빙수").price(7000).build();

    productRepository.saveAll(List.of(product1, product2, product3));

    // when
    List<Product> products = productRepository.findAllByProductNumberIn(List.of("001", "002"));

    // then
    assertThat(products).hasSize(2)
        .extracting("productNumber", "name", "sellingType")
        .containsExactlyInAnyOrder(
            tuple("001", "아메리카노", SELLING),
            tuple("002", "카페라떼", HOLD)
        );

  }
}