package E_Commerce.Platform.E_Commerce.Controller;

import E_Commerce.Platform.E_Commerce.Domain.AdminDashBoard;
import E_Commerce.Platform.E_Commerce.Domain.CustomerOrder;
import E_Commerce.Platform.E_Commerce.Enums.Status;
import E_Commerce.Platform.E_Commerce.Service.AdminDashBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminDashBoardController {
    @Autowired
    private AdminDashBoardService adminDashBoardService;
    @GetMapping
    public List<AdminDashBoard> getAllOrders(){
        return adminDashBoardService.getAllOrders();
    }
    @GetMapping("/{id}")
    public AdminDashBoard getAdminDashBoard(@PathVariable long id){
        return adminDashBoardService.getAdminDashBoard(id);
    }
    @GetMapping("/orders/{id}")
    public List<CustomerOrder> getCustomerOrders(@PathVariable long id){
        return adminDashBoardService.getCustomerOrders(id);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeAdminDashBoardById(@PathVariable long id){
        adminDashBoardService.removeAdminDashBoardById(id);
        return ResponseEntity.ok("Successsfully deleted");
    }
    @DeleteMapping
    public void removeAll(@RequestBody AdminDashBoard adminDashBoard){
        adminDashBoardService.removeAll(adminDashBoard);
    }
    @DeleteMapping("/orders/{id}")
    public void removeOrders(@PathVariable long id) {
        adminDashBoardService.removeOrders(id);
    }
    @PutMapping("/update/{adminid}")
    public ResponseEntity<String> changeOrderStatus(@PathVariable long adminid, @RequestParam long orderid, @RequestParam Status status){
        adminDashBoardService.changeOrderStatus(adminid,orderid,status);
        return ResponseEntity.ok("Successfully changed status to " + status);
    }

}
