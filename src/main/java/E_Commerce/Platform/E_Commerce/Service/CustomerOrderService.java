package E_Commerce.Platform.E_Commerce.Service;

import E_Commerce.Platform.E_Commerce.Domain.CustomerOrder;
import E_Commerce.Platform.E_Commerce.Domain.Product;
import E_Commerce.Platform.E_Commerce.Domain.ShopCart;
import E_Commerce.Platform.E_Commerce.Repository.CustomerOrderRepository;
import E_Commerce.Platform.E_Commerce.Repository.ProductRepository;
import E_Commerce.Platform.E_Commerce.Repository.ShopCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static E_Commerce.Platform.E_Commerce.Enums.Status.Pending;

@Service
public class CustomerOrderService {
    @Autowired
    private CustomerOrderRepository customerOrderRepository;
    @Autowired
    private ShopCartRepository shopCartRepository;
    @Autowired
    private ProductRepository productRepository;
    private CustomerOrder customerOrder;
    public void saveOrder(CustomerOrder customerOrder){
        customerOrderRepository.save(customerOrder);
    }
    public void removeOrder(CustomerOrder customerOrder){
       customerOrderRepository.delete(customerOrder);
    }
    public void removeOrderById(long id){
        customerOrderRepository.deleteById(id);
    }
    public List<CustomerOrder> getAllOrders(){
        return customerOrderRepository.findAll();
    }
    public CustomerOrder getOrderById(long id){
      return customerOrderRepository.findById(id).orElse(null);
    }
    public Double confirmOrder(long orderId,long shopId){
        customerOrder = customerOrderRepository.findById(orderId).orElse(null);
        customerOrder.setOrderDate(LocalDateTime.now());
        customerOrder.setStatus(Pending);
         //decreasing the number of products after confirming the order
      ShopCart shopCart = shopCartRepository.findById(shopId).orElse(null);
        Map<Product, Integer> productsWithQuantity = shopCart.getProductsWithQuantity();

        // Decrease the product quantity based on the values
        productsWithQuantity.forEach((product, quantity) -> {
            // Decrease the product quantity by the specified amount
            int newQuantity = product.getProductQuantity() - quantity;
            if (newQuantity < 0) {
                throw new RuntimeException("Insufficient stock for product: " + product.getProductName());
            }
            product.setProductQuantity(newQuantity);
            productRepository.save(product);
        });
        customerOrderRepository.save(customerOrder);
        return calculateTotalPrice(customerOrder);
    }
    public Double calculateTotalPrice(CustomerOrder customerOrder){

      double totalPrice = 0;

      for (Map.Entry<Product,Integer> entry:customerOrder.getShopCart().getProductsWithQuantity().entrySet()){
          Product product = entry.getKey();
          double pricePerProduct;
          double price = product.getProductPrice();
          int quantity = entry.getValue();
          pricePerProduct = quantity * price;
          totalPrice = pricePerProduct + totalPrice;
          System.out.println(pricePerProduct + " " + totalPrice);
      }
      return totalPrice;
    }
}
