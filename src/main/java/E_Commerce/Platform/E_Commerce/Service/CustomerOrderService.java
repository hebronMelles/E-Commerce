package E_Commerce.Platform.E_Commerce.Service;

import E_Commerce.Platform.E_Commerce.Domain.CustomerOrder;
import E_Commerce.Platform.E_Commerce.Domain.ShopCart;
import E_Commerce.Platform.E_Commerce.Repository.CustomerOrderRepository;
import E_Commerce.Platform.E_Commerce.Repository.ShopCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CustomerOrderService {
    @Autowired
    private CustomerOrderRepository customerOrderRepository;
    @Autowired
    private ShopCartRepository shopCartRepository;
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
    public void confirmOrder(long id){
        customerOrder = customerOrderRepository.findById(id).orElse(null);
        customerOrder.setOrderDate(LocalDate.now());
        customerOrder.setStatus("Pending");
         //decreasing the number of products after confirming the order
      ShopCart shopCart = shopCartRepository.findById(id).orElse(null);
      shopCart.setProductsWithQuantity(null);
      shopCartRepository.save(shopCart);
        customerOrderRepository.save(customerOrder);
    }
}
