package travel.booking.travel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import travel.booking.travel.entity.Destination;

public interface DestinationRepository extends JpaRepository<Destination, Long> {
}
