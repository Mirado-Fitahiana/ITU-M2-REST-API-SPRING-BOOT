package travel.booking.travel.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FlightRequest {

    @NotBlank
    private String airline;

    @NotBlank
    private String departureCity;

    @NotBlank
    private String arrivalCity;

    private LocalDateTime departureTime;

    private int availableSeats;

    private Long tripId;
}
