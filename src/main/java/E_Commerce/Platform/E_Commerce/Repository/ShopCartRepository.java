package E_Commerce.Platform.E_Commerce.Repository;

import E_Commerce.Platform.E_Commerce.Domain.ShopCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopCartRepository extends JpaRepository<ShopCart,Long> {
}
