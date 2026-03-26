package travel.booking.travel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import travel.booking.travel.entity.Booking;
import travel.booking.travel.entity.BookingStatus;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByUserId(Long userId);

    @Query("SELECT b FROM Booking b WHERE (:status IS NULL OR b.status = :status) AND (:userId IS NULL OR b.user.id = :userId)")
    List<Booking> findWithFilters(@Param("status") BookingStatus status, @Param("userId") Long userId);
}
