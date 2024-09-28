package E_Commerce.Platform.E_Commerce.Controller;

import E_Commerce.Platform.E_Commerce.Domain.User;
import E_Commerce.Platform.E_Commerce.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/user")
public class UserController {
   @Autowired
    private UserService userService;

    @PostMapping(value = "/add")
    public ResponseEntity<String> saveUser(@RequestBody User user){
        if(userService.userExist(user.getEmail())){
            return ResponseEntity.ok("User with " + user.getEmail() + " Already Exist");
        }
        userService.saveUser(user);
        return ResponseEntity.ok("Successfully saved user");
    }

    @DeleteMapping
    public ResponseEntity<String> removeUser(@RequestBody User user){
       userService.removeUser(user);
        return ResponseEntity.ok("Successfully removed user");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeUserById(@PathVariable long id){
        userService.removeUserById(id);
        return ResponseEntity.ok("Successfully removed user");
    }
    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }
    @GetMapping("/{id}")
    public User getUserById(@PathVariable long id){
        return userService.getUserById(id);
    }

    @PostMapping("/{id}")
    public ResponseEntity<String> logIn(@RequestParam String email,@RequestParam String password, @PathVariable long id){
     return userService.logIn(email,password,id);
    }

}
