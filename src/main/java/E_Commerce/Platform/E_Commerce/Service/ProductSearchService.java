package E_Commerce.Platform.E_Commerce.Service;

import E_Commerce.Platform.E_Commerce.Domain.Product;
import E_Commerce.Platform.E_Commerce.Repository.ProductSpecification;
import E_Commerce.Platform.E_Commerce.Repository.PropertySearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ProductSearchService {
    @Autowired
    private PropertySearchRepository propertySearchRepository;
    public List<Product> findAll(String category, Double maxPrice,Double minPrice, String productName,String productDescription){
        if(category == null && maxPrice == null && productName ==null && minPrice == null && productDescription == null){
            return Collections.emptyList();
        }
        Specification<Product> specification = ProductSpecification.filter(category, maxPrice,minPrice, productName,productDescription);
        if(specification != null){

            return propertySearchRepository.findAll(specification);
        }

        return Collections.emptyList();
    }
}