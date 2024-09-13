package E_Commerce.Platform.E_Commerce.Domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product {
    private String productName;
    private String productDescription;
    private double productPrice;
    private int productQuantity;
    private String imageUrl;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long productId;
    @OneToOne
    private Category category;





}
