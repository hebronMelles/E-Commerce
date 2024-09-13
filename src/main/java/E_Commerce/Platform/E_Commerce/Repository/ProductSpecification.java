package E_Commerce.Platform.E_Commerce.Repository;

import E_Commerce.Platform.E_Commerce.Domain.Product;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;


public class ProductSpecification {
    public static Specification<Product> filter(String category, Double maxPrice,Double minPrice, String productName,String productDescription){
       return (Root<Product>root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder ) ->{
           Predicate predicate = criteriaBuilder.conjunction();
           if(maxPrice != null){
               predicate = criteriaBuilder.and(predicate,criteriaBuilder.greaterThanOrEqualTo(root.get("productPrice"), maxPrice));
           }
           if(minPrice != null){
               predicate = criteriaBuilder.and(predicate,criteriaBuilder.lessThanOrEqualTo(root.get("productPrice"),minPrice));
           }
           if(productName != null){
               predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("productName"),productName));
           }
           if(productDescription != null){
               predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("productDescription"), productDescription));
           }
           if(category != null){
               predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("category").get("categoryName"),category));
           }
          return predicate;
       };
    }
}
