package E_Commerce.Platform.E_Commerce.Controller;

import E_Commerce.Platform.E_Commerce.Domain.CustomerOrder;
import E_Commerce.Platform.E_Commerce.Service.CustomerOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class customerOrderController {
    @Autowired
    private CustomerOrderService orderService;

    @PostMapping
    public ResponseEntity<String> saveOrder(@RequestBody CustomerOrder customerOrder){
       orderService.saveOrder(customerOrder);
        return ResponseEntity.ok("Successfully saved Order");
    }

    @DeleteMapping
    public ResponseEntity<String> removeOrder(@RequestBody CustomerOrder customerOrder){
        orderService.removeOrder(customerOrder);
        return ResponseEntity.ok("Successfully removed Order");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeOrderById(@PathVariable long id){
        orderService.removeOrderById(id);
        return ResponseEntity.ok("Successfully removed Order");

    }
    @GetMapping("/orders")
    public List<CustomerOrder> getAllOrders(){
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public CustomerOrder getOrderById(@PathVariable long id){
        return orderService.getOrderById(id);
    }

    @PutMapping("/confirm")
    public ResponseEntity<String> confirmOrder(@RequestParam long orderid,@RequestParam long shopid){
        double totalPrice = orderService.confirmOrder(orderid,shopid);
        return ResponseEntity.ok("Successfully confirmed order and your total price is " + totalPrice );
    }
}
