package travel.booking.travel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import travel.booking.travel.entity.Trip;

import java.time.LocalDate;
import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Long> {

    @Query("SELECT t FROM Trip t WHERE " +
           "(:destination IS NULL OR LOWER(t.destination.name) LIKE LOWER(CONCAT('%',:destination,'%'))) AND " +
           "(:minPrice IS NULL OR t.price >= :minPrice) AND " +
           "(:maxPrice IS NULL OR t.price <= :maxPrice) AND " +
           "(:startDate IS NULL OR t.startDate >= :startDate)")
    List<Trip> findWithFilters(@Param("destination") String destination,
                               @Param("minPrice") Double minPrice,
                               @Param("maxPrice") Double maxPrice,
                               @Param("startDate") LocalDate startDate);
}
