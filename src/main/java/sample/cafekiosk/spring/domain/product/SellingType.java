package sample.cafekiosk.spring.domain.product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public enum SellingType {

  SELLING("판매중"),
  HOLD("판매보류"),
  STOP_SELLING("판매중지");

  private final String text;

  public static List<SellingType> forDisplay() {
    return List.of(SELLING, HOLD);
  }
}