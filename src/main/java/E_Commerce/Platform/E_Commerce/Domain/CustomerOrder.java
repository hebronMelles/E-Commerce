package E_Commerce.Platform.E_Commerce.Domain;

import E_Commerce.Platform.E_Commerce.Enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    @Enumerated(EnumType.STRING)
    private Status status;
    private LocalDateTime orderDate;
}
