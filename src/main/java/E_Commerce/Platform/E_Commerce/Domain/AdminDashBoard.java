package E_Commerce.Platform.E_Commerce.Domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AdminDashBoard {
    @Id
    private long id;
    @OneToMany
    @JsonIgnore
    private List<CustomerOrder> customerOrders;


}
