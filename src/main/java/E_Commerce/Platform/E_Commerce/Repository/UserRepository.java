package E_Commerce.Platform.E_Commerce.Repository;

import E_Commerce.Platform.E_Commerce.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}
