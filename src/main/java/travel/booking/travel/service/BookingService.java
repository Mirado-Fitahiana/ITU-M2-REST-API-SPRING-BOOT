package travel.booking.travel.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import travel.booking.travel.dto.request.BookingRequest;
import travel.booking.travel.entity.*;
import travel.booking.travel.repository.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final TripRepository tripRepository;
    private final PassengerRepository passengerRepository;
    private final FlightRepository flightRepository;
    private final HotelRepository hotelRepository;
    private final PaymentRepository paymentRepository;

    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    public Booking findById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found: " + id));
    }

    public List<Booking> findWithFilters(BookingStatus status, Long userId) {
        return bookingRepository.findWithFilters(status, userId);
    }

    public Booking create(BookingRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found: " + request.getUserId()));
        Trip trip = tripRepository.findById(request.getTripId())
                .orElseThrow(() -> new RuntimeException("Trip not found: " + request.getTripId()));

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setTrip(trip);
        booking.setTotalPrice(request.getTotalPrice());
        booking.setBookingDate(LocalDate.now());
        booking.setStatus(BookingStatus.PENDING);

        if (request.getPassengerIds() != null && !request.getPassengerIds().isEmpty()) {
            Set<Passenger> passengers = new HashSet<>(passengerRepository.findAllById(request.getPassengerIds()));
            booking.setPassengers(passengers);
        }

        return bookingRepository.save(booking);
    }

    public Booking update(Long id, BookingRequest request) {
        Booking booking = findById(id);
        if (request.getTotalPrice() != null) {
            booking.setTotalPrice(request.getTotalPrice());
        }
        if (request.getPassengerIds() != null) {
            Set<Passenger> passengers = new HashSet<>(passengerRepository.findAllById(request.getPassengerIds()));
            booking.setPassengers(passengers);
        }
        return bookingRepository.save(booking);
    }

    public void delete(Long id) {
        bookingRepository.deleteById(id);
    }

    public Map<String, Object> getBookingDetails(Long bookingId) {
        Booking booking = findById(bookingId);
        List<Payment> payments = paymentRepository.findByBookingId(bookingId);
        List<Flight> flights = flightRepository.findByTripId(booking.getTrip().getId());
        List<Hotel> hotels = hotelRepository.findByTripId(booking.getTrip().getId());

        Map<String, Object> details = new HashMap<>();
        details.put("booking", booking);
        details.put("user", booking.getUser());
        details.put("passengers", booking.getPassengers());
        details.put("trip", booking.getTrip());
        details.put("flights", flights);
        details.put("hotels", hotels);
        details.put("payments", payments);
        return details;
    }
}
