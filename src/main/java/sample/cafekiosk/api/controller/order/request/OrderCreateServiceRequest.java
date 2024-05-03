package sample.cafekiosk.api.controller.order.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@NoArgsConstructor
public class OrderCreateServiceRequest {

  public OrderCreateServiceRequest toServiceRequest() {
    return OrderCreateServiceRequest.builder()
            .productNumbers(productNumbers)
            .build();
  }

  @NotEmpty(message = "상품 번호 리스트는 필수입니다.")
  private List<String> productNumbers;

  @Builder
  private OrderCreateServiceRequest(List<String> productNumbers) {
    this.productNumbers = productNumbers;
  }
}