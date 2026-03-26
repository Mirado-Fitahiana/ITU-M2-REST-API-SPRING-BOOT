package travel.booking.travel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import travel.booking.travel.entity.Passenger;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {
}
