package E_Commerce.Platform.E_Commerce.Service;

import E_Commerce.Platform.E_Commerce.Domain.CustomerOrder;
import E_Commerce.Platform.E_Commerce.Domain.Product;
import E_Commerce.Platform.E_Commerce.Domain.ShopCart;
import E_Commerce.Platform.E_Commerce.Domain.User;
import E_Commerce.Platform.E_Commerce.Repository.CustomerOrderRepository;
import E_Commerce.Platform.E_Commerce.Repository.ProductRepository;
import E_Commerce.Platform.E_Commerce.Repository.ShopCartRepository;
import E_Commerce.Platform.E_Commerce.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ShopCartService {

    @Autowired
    private ShopCartRepository shopCartRepository;
    @Autowired
    private CustomerOrderRepository customerOrderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    public void saveShopCart(ShopCart shopCart){

        shopCartRepository.save(shopCart);
    }
    public void removeShopCart(ShopCart shopCart){
        shopCartRepository.delete(shopCart);
    }
    @Transactional
    public void removeShopCartById(long id){
        ShopCart shopCart = getShopCartById(id);
        shopCart.setUser(null);
       // shopCart.setProductList(null);
        shopCartRepository.deleteById(id);
    }
    public List<ShopCart> getAllShopCarts(){
       return shopCartRepository.findAll();
    }
    public ShopCart getShopCartById(long id){
        return shopCartRepository.findById(id).orElse(null);
    }
    public ResponseEntity<String> addProductToProductList(long productId, long id){
        Product product = productRepository.findById(productId).orElse(null);
            ShopCart shopCart = getShopCartById(id);
        shopCart.getProductsWithQuantity().merge(product, 0, Integer::sum);

       // shopCart.getProductList().add(product);
            shopCartRepository.save(shopCart);
            return ResponseEntity.ok("Successfully added product to your cart");


    }
    public void removeProductFromProductList(long productId, long shopId){
        Product product = productRepository.findById(productId).orElse(null);
       ShopCart shopCart = getShopCartById(shopId);
        shopCart.getProductsWithQuantity().remove(product);
        shopCartRepository.save(shopCart);
    }
    public void increaseProductQuantity(long productId,long shopId){
//
        ShopCart shopCart = getShopCartById(shopId);
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        Map<Product, Integer> productsWithQuantity = shopCart.getProductsWithQuantity();
        int currentQuantity = productsWithQuantity.getOrDefault(product, 0);

        if (currentQuantity < 1) {
            productsWithQuantity.put(product, currentQuantity + 1);
        }

        shopCartRepository.save(shopCart);

    }
    public void decreaseProductQuantity(long productId,long shopId){
        ShopCart shopCart = getShopCartById(shopId);
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        Map<Product, Integer> productsWithQuantity = shopCart.getProductsWithQuantity();
        int currentQuantity = productsWithQuantity.getOrDefault(product, 0);

        if (currentQuantity > 1) {
            productsWithQuantity.put(product, currentQuantity - 1);
        } else {
            productsWithQuantity.remove(product);
        }

        shopCartRepository.save(shopCart);

    }
    public void placeOrder(long userId,long shopId){
        User user = userRepository.findById(userId).orElse(null);
        ShopCart shopCart = getShopCartById(shopId);
        shopCart.setUser(user);
        shopCartRepository.save(shopCart);
        CustomerOrder customerOrder = new CustomerOrder(userId,shopCart,null,null);
        customerOrderRepository.save(customerOrder);
    }
    public boolean productExistInProducts(long productId){
        List<Product> productList = productRepository.findAll();
        System.out.println( productList.stream().anyMatch(product ->product.getProductId() == productId));
        return productList.stream().anyMatch(product ->product.getProductId() == productId);
    }
    public boolean productExistInShopCart(long shopId,long productId){
        ShopCart shopCart = getShopCartById(shopId);
       return shopCart.getProductsWithQuantity().containsValue(productId);
    }



}
