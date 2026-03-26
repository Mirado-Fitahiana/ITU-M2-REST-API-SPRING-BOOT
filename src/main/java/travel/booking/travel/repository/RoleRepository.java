package travel.booking.travel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import travel.booking.travel.entity.Role;
import travel.booking.travel.entity.RoleName;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}
