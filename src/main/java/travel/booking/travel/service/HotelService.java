package travel.booking.travel.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import travel.booking.travel.dto.request.HotelRequest;
import travel.booking.travel.entity.Hotel;
import travel.booking.travel.entity.Trip;
import travel.booking.travel.repository.HotelRepository;
import travel.booking.travel.repository.TripRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelService {

    private final HotelRepository hotelRepository;
    private final TripRepository tripRepository;

    public List<Hotel> findAll() {
        return hotelRepository.findAll();
    }

    public Hotel findById(Long id) {
        return hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found: " + id));
    }

    public List<Hotel> findWithFilters(String city, Integer stars) {
        return hotelRepository.findWithFilters(city, stars);
    }

    public Hotel create(HotelRequest request) {
        Trip trip = tripRepository.findById(request.getTripId())
                .orElseThrow(() -> new RuntimeException("Trip not found: " + request.getTripId()));
        Hotel hotel = new Hotel();
        hotel.setName(request.getName());
        hotel.setCity(request.getCity());
        hotel.setStars(request.getStars());
        hotel.setPricePerNight(request.getPricePerNight());
        hotel.setTrip(trip);
        return hotelRepository.save(hotel);
    }

    public Hotel update(Long id, HotelRequest request) {
        Hotel hotel = findById(id);
        hotel.setName(request.getName());
        hotel.setCity(request.getCity());
        hotel.setStars(request.getStars());
        hotel.setPricePerNight(request.getPricePerNight());
        if (request.getTripId() != null) {
            Trip trip = tripRepository.findById(request.getTripId())
                    .orElseThrow(() -> new RuntimeException("Trip not found: " + request.getTripId()));
            hotel.setTrip(trip);
        }
        return hotelRepository.save(hotel);
    }

    public void delete(Long id) {
        hotelRepository.deleteById(id);
    }
}
