package E_Commerce.Platform.E_Commerce.Domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data

public class ShopCart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long shopCartId;
    @OneToOne
    private User user;
//    @OneToMany
//    private List<Product> productList;
    @ElementCollection
    @CollectionTable(name = "cart_products", joinColumns = @JoinColumn(name = "shop_cart_id"))
    @MapKeyJoinColumn(name = "product_id")
    @Column(name = "quantity")
    private Map<Product, Integer> productsWithQuantity;



}
