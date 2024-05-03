package sample.cafekiosk.spring.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {


  List<Product> findBySellingTypeIn(List<SellingType> sellingTypes);

  List<Product> findAllBySellingTypeIn(List<SellingType> selling);

  List<Product> findAllByProductNumberIn(List<String> productNumbers);

  @Query(value = "select p.product_number from product p order by id desc limit 1", nativeQuery = true)
  String findLatestProductNumber();
}