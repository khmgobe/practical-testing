package sample.cafekiosk.spring.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {


  List<Product> findBySellingTypeIn(List<SellingType> sellingTypes);

  List<Product> findAllBySellingTypeIn(List<SellingType> selling);

  List<Product> findAllByProductNumberIn(List<String> productNumbers);
}