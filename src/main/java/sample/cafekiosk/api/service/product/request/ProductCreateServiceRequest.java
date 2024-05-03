package sample.cafekiosk.api.service.product.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductType;
import sample.cafekiosk.spring.domain.product.SellingType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@NoArgsConstructor
public class ProductCreateServiceRequest {

    private ProductType productType;

    private SellingType sellingType;

    private String name;

    private int price;

    @Builder
    private ProductCreateServiceRequest(ProductType productType, SellingType sellingType, String name, int price) {
        this.productType = productType;
        this.sellingType = sellingType;
        this.name = name;
        this.price = price;
    }

    public Product toEntity(String nextProductNumber) {
        return Product.builder()
                .productNumber(nextProductNumber)
                .productType(productType)
                .sellingType(sellingType)
                .name(name)
                .price(price)
                .build();
    }
}
