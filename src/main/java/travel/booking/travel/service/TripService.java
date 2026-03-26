package travel.booking.travel.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import travel.booking.travel.dto.request.TripRequest;
import travel.booking.travel.entity.Destination;
import travel.booking.travel.entity.Flight;
import travel.booking.travel.entity.Hotel;
import travel.booking.travel.entity.Trip;
import travel.booking.travel.repository.BookingRepository;
import travel.booking.travel.repository.DestinationRepository;
import travel.booking.travel.repository.FlightRepository;
import travel.booking.travel.repository.HotelRepository;
import travel.booking.travel.repository.ReviewRepository;
import travel.booking.travel.repository.TripRepository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepository;
    private final DestinationRepository destinationRepository;
    private final FlightRepository flightRepository;
    private final HotelRepository hotelRepository;
    private final BookingRepository bookingRepository;
    private final ReviewRepository reviewRepository;

    public List<Trip> findAll() {
        return tripRepository.findAll();
    }

    public Trip findById(Long id) {
        return tripRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trip not found: " + id));
    }

    public List<Trip> findWithFilters(String destination, Double minPrice, Double maxPrice, LocalDate startDate) {
        return tripRepository.findWithFilters(destination, minPrice, maxPrice, startDate);
    }

    public Trip create(TripRequest request) {
        Destination destination = destinationRepository.findById(request.getDestinationId())
                .orElseThrow(() -> new RuntimeException("Destination not found: " + request.getDestinationId()));
        Trip trip = new Trip();
        trip.setTitle(request.getTitle());
        trip.setStartDate(request.getStartDate());
        trip.setEndDate(request.getEndDate());
        trip.setPrice(request.getPrice());
        trip.setDestination(destination);
        return tripRepository.save(trip);
    }

    public Trip update(Long id, TripRequest request) {
        Trip trip = findById(id);
        trip.setTitle(request.getTitle());
        trip.setStartDate(request.getStartDate());
        trip.setEndDate(request.getEndDate());
        trip.setPrice(request.getPrice());
        if (request.getDestinationId() != null) {
            Destination destination = destinationRepository.findById(request.getDestinationId())
                    .orElseThrow(() -> new RuntimeException("Destination not found: " + request.getDestinationId()));
            trip.setDestination(destination);
        }
        return tripRepository.save(trip);
    }

    public void delete(Long id) {
        tripRepository.deleteById(id);
    }

    public Map<String, Object> getFullTrip(Long tripId) {
        Trip trip = findById(tripId);
        List<Flight> flights = flightRepository.findByTripId(tripId);
        List<Hotel> hotels = hotelRepository.findByTripId(tripId);
        long bookingsCount = bookingRepository.findAll().stream()
                .filter(b -> b.getTrip() != null && b.getTrip().getId().equals(tripId))
                .count();
        Double averageRating = reviewRepository.averageRatingByTripId(tripId);
        int availableSeats = flights.stream().mapToInt(Flight::getAvailableSeats).sum();

        Map<String, Object> result = new HashMap<>();
        result.put("trip", trip);
        result.put("destination", trip.getDestination());
        result.put("flights", flights);
        result.put("hotels", hotels);
        result.put("bookingsCount", bookingsCount);
        result.put("averageRating", averageRating != null ? averageRating : 0.0);
        result.put("availableSeats", availableSeats);
        return result;
    }
}
