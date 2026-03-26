package travel.booking.travel.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import travel.booking.travel.dto.request.DestinationRequest;
import travel.booking.travel.entity.Booking;
import travel.booking.travel.entity.Destination;
import travel.booking.travel.entity.Flight;
import travel.booking.travel.entity.Hotel;
import travel.booking.travel.entity.Trip;
import travel.booking.travel.repository.BookingRepository;
import travel.booking.travel.repository.DestinationRepository;
import travel.booking.travel.repository.FlightRepository;
import travel.booking.travel.repository.HotelRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DestinationService {

    private final DestinationRepository destinationRepository;
    private final BookingRepository bookingRepository;
    private final FlightRepository flightRepository;
    private final HotelRepository hotelRepository;

    public List<Destination> findAll() {
        return destinationRepository.findAll();
    }

    public Destination findById(Long id) {
        return destinationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Destination not found: " + id));
    }

    public Destination create(DestinationRequest request) {
        Destination destination = new Destination();
        destination.setName(request.getName());
        destination.setCountry(request.getCountry());
        return destinationRepository.save(destination);
    }

    public Destination update(Long id, DestinationRequest request) {
        Destination destination = findById(id);
        destination.setName(request.getName());
        destination.setCountry(request.getCountry());
        return destinationRepository.save(destination);
    }

    public void delete(Long id) {
        destinationRepository.deleteById(id);
    }

    public Map<String, Object> getStats(Long destinationId) {
        Destination destination = findById(destinationId);
        List<Trip> trips = destination.getTrips() != null ? destination.getTrips() : List.of();

        List<Long> tripIds = trips.stream().map(Trip::getId).collect(Collectors.toList());

        List<Booking> allBookings = bookingRepository.findAll();
        long numberOfBookings = allBookings.stream()
                .filter(b -> b.getTrip() != null && tripIds.contains(b.getTrip().getId()))
                .count();

        double avgTripPrice = trips.stream()
                .mapToDouble(t -> t.getPrice() != null ? t.getPrice() : 0.0)
                .average()
                .orElse(0.0);

        List<Hotel> topHotels = tripIds.stream()
                .flatMap(tid -> hotelRepository.findByTripId(tid).stream())
                .limit(5)
                .collect(Collectors.toList());

        List<Flight> topFlights = tripIds.stream()
                .flatMap(tid -> flightRepository.findByTripId(tid).stream())
                .limit(5)
                .collect(Collectors.toList());

        Map<String, Object> stats = new HashMap<>();
        stats.put("destination", destination);
        stats.put("numberOfTrips", trips.size());
        stats.put("numberOfBookings", numberOfBookings);
        stats.put("averageTripPrice", avgTripPrice);
        stats.put("topHotels", topHotels);
        stats.put("topFlights", topFlights);
        return stats;
    }
}
