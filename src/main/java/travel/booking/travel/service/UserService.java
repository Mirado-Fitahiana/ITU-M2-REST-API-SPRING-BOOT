package travel.booking.travel.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import travel.booking.travel.dto.request.RegisterRequest;
import travel.booking.travel.entity.Booking;
import travel.booking.travel.entity.Review;
import travel.booking.travel.entity.Trip;
import travel.booking.travel.entity.User;
import travel.booking.travel.repository.BookingRepository;
import travel.booking.travel.repository.ReviewRepository;
import travel.booking.travel.repository.UserRepository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final ReviewRepository reviewRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found: " + id));
    }

    public User create(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already in use");
        }
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEnabled(true);
        return userRepository.save(user);
    }

    public User update(Long id, RegisterRequest request) {
        User user = findById(id);
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        return userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public Map<String, Object> getDashboard(Long userId) {
        User user = findById(userId);
        List<Booking> bookings = bookingRepository.findByUserId(userId);
        double totalSpent = bookings.stream()
                .mapToDouble(b -> b.getTotalPrice() != null ? b.getTotalPrice() : 0.0)
                .sum();
        LocalDate today = LocalDate.now();
        List<Trip> upcomingTrips = bookings.stream()
                .map(Booking::getTrip)
                .filter(t -> t != null && t.getStartDate() != null && !t.getStartDate().isBefore(today))
                .distinct()
                .collect(Collectors.toList());
        List<Trip> completedTrips = bookings.stream()
                .map(Booking::getTrip)
                .filter(t -> t != null && t.getEndDate() != null && t.getEndDate().isBefore(today))
                .distinct()
                .collect(Collectors.toList());
        List<Review> reviews = reviewRepository.findByUserId(userId);

        Map<String, Object> dashboard = new HashMap<>();
        dashboard.put("user", user);
        dashboard.put("totalBookings", bookings.size());
        dashboard.put("totalSpent", totalSpent);
        dashboard.put("upcomingTrips", upcomingTrips);
        dashboard.put("completedTrips", completedTrips);
        dashboard.put("reviews", reviews);
        return dashboard;
    }
}
