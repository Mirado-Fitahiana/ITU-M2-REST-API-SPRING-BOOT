package travel.booking.travel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import travel.booking.travel.entity.Hotel;

import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

    List<Hotel> findByTripId(Long tripId);

    @Query("SELECT h FROM Hotel h WHERE " +
           "(:city IS NULL OR LOWER(h.city) LIKE LOWER(CONCAT('%',:city,'%'))) AND " +
           "(:stars IS NULL OR h.stars = :stars)")
    List<Hotel> findWithFilters(@Param("city") String city, @Param("stars") Integer stars);
}
