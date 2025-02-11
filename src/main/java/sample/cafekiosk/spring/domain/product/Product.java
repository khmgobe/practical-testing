package sample.cafekiosk.spring.domain.product;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.cafekiosk.spring.domain.BaseEntity;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String productNumber;

  @Enumerated(EnumType.STRING)
  private ProductType productType;

  @Enumerated(EnumType.STRING)
  private SellingType sellingType;

  private String name;

  private int price;

  @Builder
  private Product(String productNumber, ProductType productType,
      SellingType sellingType, String name, int price) {
    this.productNumber = productNumber;
    this.productType = productType;
    this.sellingType = sellingType;
    this.name = name;
    this.price = price;
  }
}