package E_Commerce.Platform.E_Commerce.Domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data

public class CustomerOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long orderId;
    @OneToOne
    private ShopCart shopCart;
    private String status;
    private LocalDate orderDate;
}
