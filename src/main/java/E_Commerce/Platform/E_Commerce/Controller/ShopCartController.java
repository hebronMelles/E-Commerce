package E_Commerce.Platform.E_Commerce.Controller;

import E_Commerce.Platform.E_Commerce.Domain.ShopCart;
import E_Commerce.Platform.E_Commerce.Service.ShopCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/shop")
public class ShopCartController {
   @Autowired
   private ShopCartService shopCartService;

   @PostMapping
    public ResponseEntity<String> saveShopCart(@RequestBody ShopCart shopCart){
       shopCartService.saveShopCart(shopCart);
       return ResponseEntity.ok("Successfully added shop cart");
    }

    @DeleteMapping()
    public ResponseEntity<String> removeShopCart(@RequestBody ShopCart shopCart){
       shopCartService.removeShopCart(shopCart);
        return ResponseEntity.ok("Successfully removed shop cart");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeShopCartById(@PathVariable long id){
       shopCartService.removeShopCartById(id);
        return ResponseEntity.ok("Successfully removed shop cart");
    }

    @GetMapping("/shops")
    public List<ShopCart> getAllShopCarts(){
        return shopCartService.getAllShopCarts();
    }
    @GetMapping("/{id}")
    public ShopCart getShopCartById(@PathVariable long id){
        return shopCartService.getShopCartById(id);
    }

    @PutMapping("/add/{shopId}")
    public ResponseEntity<String> addProductToProductList(@RequestParam long productid, @PathVariable long shopId){
        if(!shopCartService.productExistInProducts(productid)){
            return ResponseEntity.ok("Product with " + productid + " does not exist");
        }
      else if(shopCartService.productExistInShopCart(shopId,productid)){
           return ResponseEntity.ok("Product with " + productid + " already exist in your cart");
       }
           return shopCartService.addProductToProductList(productid,shopId);

    }
    @DeleteMapping("remove/{shopId}")
    public ResponseEntity<String> removeProductFromProductList(@RequestParam long productid, @PathVariable long shopId){
       shopCartService.removeProductFromProductList(productid,shopId);
        return ResponseEntity.ok("Successfully removed product from shop cart");
    }
    @PutMapping("/increase/{shopId}")
    public ResponseEntity<String> increaseProductQuantity(@RequestParam long productid,@PathVariable long shopId){
       shopCartService.increaseProductQuantity(productid,shopId);
        return ResponseEntity.ok("Successfully increased product quantity by one");
    }
    @PutMapping("decrease/{shopId}")
    public ResponseEntity<String> decreaseProductQuantity(@RequestParam long productid,@PathVariable long shopId){
        shopCartService.decreaseProductQuantity(productid,shopId);
        return ResponseEntity.ok("Successfully decreased product quantity by one");
    }

    @PutMapping("/place-order")
    public ResponseEntity<String> placeOrder(@RequestParam long userid,@RequestParam long shopid){
       shopCartService.placeOrder(userid,shopid);
       return  ResponseEntity.ok("Successfully placed order");
    }
}

