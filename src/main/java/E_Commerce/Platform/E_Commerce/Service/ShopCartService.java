package E_Commerce.Platform.E_Commerce.Service;

import E_Commerce.Platform.E_Commerce.Domain.*;
import E_Commerce.Platform.E_Commerce.Repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static E_Commerce.Platform.E_Commerce.Enums.Role.ADMIN;
import static E_Commerce.Platform.E_Commerce.Enums.Status.OrderPlaced;

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
    @Autowired
    private AdminDashBoardRepository adminDashBoardRepository;


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
        Integer currentQuantity = productsWithQuantity.get(product);
            productsWithQuantity.put(product, currentQuantity + 1);


        shopCartRepository.save(shopCart);

    }
    public void decreaseProductQuantity(long productId,long shopId){
        ShopCart shopCart = getShopCartById(shopId);
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        Map<Product, Integer> productsWithQuantity = shopCart.getProductsWithQuantity();
        int currentQuantity = productsWithQuantity.getOrDefault(product, 0);

        if (currentQuantity > 0) {
            productsWithQuantity.put(product, currentQuantity - 1);
        } else {
            productsWithQuantity.remove(product);
        }

        shopCartRepository.save(shopCart);

    }
    @Transactional
    public void placeOrder(long userId,long shopId){
        User user = userRepository.findById(userId).orElse(null);
        User adminUser = userRepository.findAll().stream().filter(role -> role.getRole().equals(ADMIN)).findFirst().get();
        System.out.println(adminUser.getId());
        AdminDashBoard adminDashBoard = adminDashBoardRepository.findById(adminUser.getId()).orElse(null);
        ShopCart shopCart = getShopCartById(shopId);
        shopCart.setUser(user);
        shopCartRepository.save(shopCart);
        CustomerOrder customerOrder = createCustomerOrder(shopCart);
        System.out.println("this is customer order" + customerOrder.getOrderId());
        customerOrderRepository.save(customerOrder);
        adminDashBoard.getCustomerOrders().add(customerOrder);
       adminDashBoardRepository.save(adminDashBoard);
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
    public CustomerOrder createCustomerOrder(ShopCart shopCart){
        CustomerOrder customerOrder= new CustomerOrder();
        customerOrder.setOrderDate(LocalDateTime.now());
        customerOrder.setShopCart(shopCart);
        customerOrder.setStatus(OrderPlaced);
        return customerOrder;
    }


}
