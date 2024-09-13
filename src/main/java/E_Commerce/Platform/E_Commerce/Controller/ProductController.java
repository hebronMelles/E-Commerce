package E_Commerce.Platform.E_Commerce.Controller;

import E_Commerce.Platform.E_Commerce.Domain.Product;
import E_Commerce.Platform.E_Commerce.Service.ProductSearchService;
import E_Commerce.Platform.E_Commerce.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductSearchService productSearchService;

    @PostMapping
    public ResponseEntity<String> saveProduct(@RequestBody Product product){
        if(productService.productExist(product.getProductId())){
            return ResponseEntity.ok(product.getProductName() + " Product Already Exist");
        }
        productService.saveProduct(product);
        return ResponseEntity.ok("Successfully saved Product");
    }
    @DeleteMapping
    public ResponseEntity<String> removeProduct(@RequestBody Product product){
       productService.removeProduct(product);
        return ResponseEntity.ok("Successfully removed Product");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeProductById(@PathVariable long id){
     productService.removeProductById(id);
        return ResponseEntity.ok("Successfully removed Product");
    }

    @GetMapping("/products")
    public List<Product> getAll(){
        return productService.getAll();
    }
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable long id){
        return productService.getProductById(id);
    }
    @GetMapping("/filter")
    public List<Product> filterProducts(@RequestParam (required = false)String category,
                                        @RequestParam(required = false) Double maxPrice,
                                        @RequestParam(required = false) String name,
                                        @RequestParam (required = false) Double minPrice,
                                        @RequestParam(required = false) String desc) {
        return productSearchService.findAll(category,maxPrice,minPrice,name,desc);
    }
}
