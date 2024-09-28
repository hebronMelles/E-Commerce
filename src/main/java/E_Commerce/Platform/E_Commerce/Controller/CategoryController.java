package E_Commerce.Platform.E_Commerce.Controller;

import E_Commerce.Platform.E_Commerce.Domain.Category;
import E_Commerce.Platform.E_Commerce.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @PostMapping
    public ResponseEntity<String> saveCategory(@RequestBody Category category){
       if(categoryService.categoryExist(category.getCategoryId())){
           return ResponseEntity.ok(category.getCategoryName() + " Category Already Exist");
       }
        categoryService.saveCategory(category);
        return ResponseEntity.ok("Successfully saved Category");
    }
    @DeleteMapping
    public ResponseEntity<String> removeCategory(@RequestBody Category category){
        categoryService.removeCategory(category);
        return ResponseEntity.ok("Successfully removed Category");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeCategoryById(@PathVariable long id){
        categoryService.removeCategoryById(id);
        return ResponseEntity.ok("Successfully removed Category");
    }
    @GetMapping("/cats")
    public List<Category> getAllCategories(){
        return categoryService.getAllCategories();
    }
    @GetMapping("{id}")
    public Category getCategoryById(@PathVariable long id){
        return categoryService.getCategoryById(id);
    }
}
