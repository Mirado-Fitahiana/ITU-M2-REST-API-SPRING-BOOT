package travel.booking.travel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import travel.booking.travel.entity.Flight;

import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    List<Flight> findByTripId(Long tripId);

    @Query("SELECT f FROM Flight f WHERE " +
           "(:departure IS NULL OR LOWER(f.departureCity) LIKE LOWER(CONCAT('%',:departure,'%'))) AND " +
           "(:arrival IS NULL OR LOWER(f.arrivalCity) LIKE LOWER(CONCAT('%',:arrival,'%')))")
    List<Flight> findWithFilters(@Param("departure") String departure, @Param("arrival") String arrival);
}
