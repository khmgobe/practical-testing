package sample.cafekiosk.spring.domain.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductType;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static sample.cafekiosk.spring.domain.product.ProductType.*;
import static sample.cafekiosk.spring.domain.product.SellingType.SELLING;

class OrderTest {

  LocalDateTime registeredDateTime = LocalDateTime.now();

  @DisplayName("주문 생성 시 상품 리스트에서 주문의 총 금액을 계산한다.")
  @Test
  void calculateTotalPrice() {

    // given
    List<Product> products = List.of(
        createProduct("001", 1000),
        createProduct("002", 2000));

    // when
    Order order = Order.create(products, LocalDateTime.now());

    // then
    assertThat(order.getTotalPrice()).isEqualTo(3000);
  }


  @DisplayName("주문 생성 시 등록 시각을 기록한다.")
  @Test
  void registeredDateTime() {

    // given
    List<Product> products = List.of(
        createProduct("001", 1000),
        createProduct("002", 2000));

    // when
    Order order = Order.create(products, registeredDateTime);

    // then
    assertThat(order.getRegisteredDateTime()).isEqualTo(registeredDateTime);
  }
  private Product createProduct(String productNumber, int price) {
    return Product.builder().productType(HANDMADE).productNumber(productNumber).price(price)
        .sellingType(SELLING).name("메뉴 이름").build();
  }

}