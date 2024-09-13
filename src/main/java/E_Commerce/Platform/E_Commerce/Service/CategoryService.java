package E_Commerce.Platform.E_Commerce.Service;

import E_Commerce.Platform.E_Commerce.Domain.Category;
import E_Commerce.Platform.E_Commerce.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    public void saveCategory(Category category){
        categoryRepository.save(category);
    }
    public void removeCategory(Category category){
        categoryRepository.delete(category);
    }
    public void removeCategoryById(long id){
        categoryRepository.deleteById(id);
    }
    public List<Category> getAllCategories(){
       return categoryRepository.findAll();
    }
    public Category getCategoryById(long id){
       return categoryRepository.findById(id).orElse(null);
    }
    public boolean categoryExist(long categoryId){
        List<Category> categoryList = categoryRepository.findAll();
        return categoryList.stream().anyMatch(category -> category.getCategoryId() == categoryId);
    }

}
