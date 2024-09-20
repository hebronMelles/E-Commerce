package E_Commerce.Platform.E_Commerce.Service;

import E_Commerce.Platform.E_Commerce.Domain.AdminDashBoard;
import E_Commerce.Platform.E_Commerce.Domain.CustomerOrder;
import E_Commerce.Platform.E_Commerce.Enums.Status;
import E_Commerce.Platform.E_Commerce.Repository.AdminDashBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminDashBoardService {
    @Autowired
    private AdminDashBoardRepository adminDashBoardRepository;
    public List<AdminDashBoard> getAllOrders(){
        return adminDashBoardRepository.findAll();
    }
    public AdminDashBoard getAdminDashBoard(long id){
        return adminDashBoardRepository.findById(id).orElse(null);
    }
    public List<CustomerOrder> getCustomerOrders(long id){
        AdminDashBoard adminDashBoard = getAdminDashBoard(id);
        return adminDashBoard.getCustomerOrders();
    }
    public void removeAdminDashBoardById(long id){
       adminDashBoardRepository.deleteById(id);
    }
    public void removeAll(AdminDashBoard adminDashBoard){
        adminDashBoardRepository.delete(adminDashBoard);

    }
    public void removeOrders(long id){
       AdminDashBoard adminDashBoard =  getAdminDashBoard(id);
       adminDashBoard.getCustomerOrders().remove(0);
       adminDashBoardRepository.save(adminDashBoard);
    }
    public void changeOrderStatus(long adminId, long orderId, Status status){
        AdminDashBoard adminDashBoard =  getAdminDashBoard(adminId);
        CustomerOrder customerOrder =  adminDashBoard.getCustomerOrders().stream().filter(id -> id.getOrderId() == orderId).findFirst().orElse(null);
        customerOrder.setStatus(status);
        adminDashBoardRepository.save(adminDashBoard);

    }
}
