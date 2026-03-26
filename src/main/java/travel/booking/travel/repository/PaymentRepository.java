package travel.booking.travel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import travel.booking.travel.entity.Payment;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByBookingId(Long bookingId);

    List<Payment> findByBookingUserId(Long userId);
}
