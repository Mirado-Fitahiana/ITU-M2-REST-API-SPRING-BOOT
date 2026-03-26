package travel.booking.travel.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import travel.booking.travel.dto.request.PassengerRequest;
import travel.booking.travel.entity.Passenger;
import travel.booking.travel.repository.PassengerRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PassengerService {

    private final PassengerRepository passengerRepository;

    public List<Passenger> findAll() {
        return passengerRepository.findAll();
    }

    public Passenger findById(Long id) {
        return passengerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Passenger not found: " + id));
    }

    public Passenger create(PassengerRequest request) {
        Passenger passenger = new Passenger();
        passenger.setFirstName(request.getFirstName());
        passenger.setLastName(request.getLastName());
        passenger.setPassportNumber(request.getPassportNumber());
        return passengerRepository.save(passenger);
    }

    public Passenger update(Long id, PassengerRequest request) {
        Passenger passenger = findById(id);
        passenger.setFirstName(request.getFirstName());
        passenger.setLastName(request.getLastName());
        passenger.setPassportNumber(request.getPassportNumber());
        return passengerRepository.save(passenger);
    }

    public void delete(Long id) {
        passengerRepository.deleteById(id);
    }
}
