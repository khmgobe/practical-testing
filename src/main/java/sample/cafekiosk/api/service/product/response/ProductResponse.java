package sample.cafekiosk.api.service.product.response;

import lombok.Builder;
import lombok.Getter;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.SellingType;
import sample.cafekiosk.spring.domain.product.ProductType;

@Getter
public class ProductResponse {

  private Long id;

  private String productNumber;

  private ProductType productType;

  private SellingType sellingType;

  private String name;

  private int price;

  @Builder
  private ProductResponse(Long id, String productNumber, ProductType productType,
      SellingType sellingType, String name, int price) {
    this.id = id;
    this.productNumber = productNumber;
    this.productType = productType;
    this.sellingType = sellingType;
    this.name = name;
    this.price = price;
  }

  public static ProductResponse of(Product product) {
    return ProductResponse.builder()
        .id(product.getId())
        .productNumber(product.getProductNumber())
        .productType(product.getProductType())
        .sellingType(product.getSellingType())
        .name(product.getName())
        .price(product.getPrice())
        .build();
  }

}