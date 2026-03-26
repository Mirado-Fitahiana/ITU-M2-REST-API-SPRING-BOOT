package travel.booking.travel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import travel.booking.travel.entity.Review;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByUserId(Long userId);

    List<Review> findByTripId(Long tripId);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.trip.id = :tripId")
    Double averageRatingByTripId(@Param("tripId") Long tripId);
}
