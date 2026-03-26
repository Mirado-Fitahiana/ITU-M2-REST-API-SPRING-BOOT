package travel.booking.travel.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import travel.booking.travel.dto.request.FlightRequest;
import travel.booking.travel.entity.Flight;
import travel.booking.travel.entity.Trip;
import travel.booking.travel.repository.FlightRepository;
import travel.booking.travel.repository.TripRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightService {

    private final FlightRepository flightRepository;
    private final TripRepository tripRepository;

    public List<Flight> findAll() {
        return flightRepository.findAll();
    }

    public Flight findById(Long id) {
        return flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found: " + id));
    }

    public List<Flight> findWithFilters(String departure, String arrival) {
        return flightRepository.findWithFilters(departure, arrival);
    }

    public Flight create(FlightRequest request) {
        Trip trip = tripRepository.findById(request.getTripId())
                .orElseThrow(() -> new RuntimeException("Trip not found: " + request.getTripId()));
        Flight flight = new Flight();
        flight.setAirline(request.getAirline());
        flight.setDepartureCity(request.getDepartureCity());
        flight.setArrivalCity(request.getArrivalCity());
        flight.setDepartureTime(request.getDepartureTime());
        flight.setAvailableSeats(request.getAvailableSeats());
        flight.setTrip(trip);
        return flightRepository.save(flight);
    }

    public Flight update(Long id, FlightRequest request) {
        Flight flight = findById(id);
        flight.setAirline(request.getAirline());
        flight.setDepartureCity(request.getDepartureCity());
        flight.setArrivalCity(request.getArrivalCity());
        flight.setDepartureTime(request.getDepartureTime());
        flight.setAvailableSeats(request.getAvailableSeats());
        if (request.getTripId() != null) {
            Trip trip = tripRepository.findById(request.getTripId())
                    .orElseThrow(() -> new RuntimeException("Trip not found: " + request.getTripId()));
            flight.setTrip(trip);
        }
        return flightRepository.save(flight);
    }

    public void delete(Long id) {
        flightRepository.deleteById(id);
    }
}
