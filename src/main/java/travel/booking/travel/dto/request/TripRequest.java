package travel.booking.travel.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TripRequest {

    @NotBlank
    private String title;

    private LocalDate startDate;

    private LocalDate endDate;

    private Double price;

    private Long destinationId;
}
