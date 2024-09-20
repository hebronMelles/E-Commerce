package E_Commerce.Platform.E_Commerce.Service;

import E_Commerce.Platform.E_Commerce.Domain.AdminDashBoard;
import E_Commerce.Platform.E_Commerce.Domain.ShopCart;
import E_Commerce.Platform.E_Commerce.Domain.User;
import E_Commerce.Platform.E_Commerce.Repository.AdminDashBoardRepository;
import E_Commerce.Platform.E_Commerce.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdminDashBoardRepository adminDashBoardRepository;


    public void saveUser(User user){

        if(user.getRole().name().equalsIgnoreCase("USER")){
            ShopCart shopCart = new ShopCart();
            shopCart.setUser(user);
            user.setShopCart(shopCart);
            userRepository.save(user);
        }
        else if (user.getRole().name().equalsIgnoreCase("ADMIN")) {
            userRepository.save(user);
            AdminDashBoard adminDashBoard = new AdminDashBoard();
            adminDashBoard.setId(user.getId());
            adminDashBoard.setCustomerOrders(new ArrayList<>());
            adminDashBoardRepository.save(adminDashBoard);
        }



    }
    public void removeUser(User user){
        userRepository.delete(user);
    }
    public void removeUserById(long id){
        userRepository.deleteById(id);
    }
    public List<User> getAllUsers(){
       return userRepository.findAll();
    }
    public User getUserById(long id){
       return userRepository.findById(id).orElse(null);
    }

    public ResponseEntity<String> logIn(String email, String password, long id){
     User user = userRepository.findById(id).orElse(null);
     if(email.equalsIgnoreCase(user.getEmail()) && password.equalsIgnoreCase(user.getPassword())){
         return ResponseEntity.ok("Logged in Successfully");
     }
       return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }
    public boolean userExist(String userEmail){
        List<User> userList = userRepository.findAll();
        return userList.stream().anyMatch(user -> user.getEmail().equalsIgnoreCase(userEmail));
    }
}
