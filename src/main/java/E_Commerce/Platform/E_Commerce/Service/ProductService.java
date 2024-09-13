package E_Commerce.Platform.E_Commerce.Service;

import E_Commerce.Platform.E_Commerce.Domain.Product;
import E_Commerce.Platform.E_Commerce.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    public void saveProduct(Product product){
        productRepository.save(product);
    }
    public void removeProduct(Product product){
        productRepository.delete(product);
    }
    public void removeProductById(long id){
        productRepository.deleteById(id);
    }
    public List<Product> getAll(){
      return productRepository.findAll();
    }
    public Product getProductById(long id){
       return  productRepository.findById(id).orElse(null);
    }
    public boolean productExist(long productId){
        List<Product> productList = productRepository.findAll();
        return productList.stream().anyMatch(product -> product.getProductId() == productId);
    }
}
