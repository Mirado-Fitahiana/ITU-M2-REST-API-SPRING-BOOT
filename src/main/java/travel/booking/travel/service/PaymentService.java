package travel.booking.travel.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import travel.booking.travel.dto.request.PaymentRequest;
import travel.booking.travel.entity.Booking;
import travel.booking.travel.entity.Payment;
import travel.booking.travel.entity.PaymentStatus;
import travel.booking.travel.entity.User;
import travel.booking.travel.repository.BookingRepository;
import travel.booking.travel.repository.PaymentRepository;
import travel.booking.travel.repository.UserRepository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

    public Payment findById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found: " + id));
    }

    public Payment create(PaymentRequest request) {
        Booking booking = bookingRepository.findById(request.getBookingId())
                .orElseThrow(() -> new RuntimeException("Booking not found: " + request.getBookingId()));
        Payment payment = new Payment();
        payment.setAmount(request.getAmount());
        payment.setMethod(request.getMethod());
        payment.setStatus(PaymentStatus.PENDING);
        payment.setPaymentDate(LocalDate.now());
        payment.setBooking(booking);
        return paymentRepository.save(payment);
    }

    public Payment update(Long id, PaymentRequest request) {
        Payment payment = findById(id);
        payment.setAmount(request.getAmount());
        payment.setMethod(request.getMethod());
        return paymentRepository.save(payment);
    }

    public void delete(Long id) {
        paymentRepository.deleteById(id);
    }

    public Map<String, Object> getUserPaymentSummary(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));
        List<Payment> payments = paymentRepository.findByBookingUserId(userId);

        double totalPaid = payments.stream()
                .filter(p -> PaymentStatus.COMPLETED.equals(p.getStatus()))
                .mapToDouble(p -> p.getAmount() != null ? p.getAmount() : 0.0)
                .sum();

        List<Payment> pendingPayments = payments.stream()
                .filter(p -> PaymentStatus.PENDING.equals(p.getStatus()))
                .collect(Collectors.toList());

        List<Payment> refundedPayments = payments.stream()
                .filter(p -> PaymentStatus.REFUNDED.equals(p.getStatus()))
                .collect(Collectors.toList());

        List<Booking> bookings = bookingRepository.findByUserId(userId);

        Map<String, Object> summary = new HashMap<>();
        summary.put("user", user);
        summary.put("totalPaid", totalPaid);
        summary.put("pendingPayments", pendingPayments);
        summary.put("refundedPayments", refundedPayments);
        summary.put("bookings", bookings);
        return summary;
    }
}
