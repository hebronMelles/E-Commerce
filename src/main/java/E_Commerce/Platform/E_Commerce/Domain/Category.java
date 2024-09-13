package E_Commerce.Platform.E_Commerce.Domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Category {
    private String categoryName;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long categoryId;

}
