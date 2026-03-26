package travel.booking.travel.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class HotelRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String city;

    @Min(1)
    @Max(5)
    private int stars;

    private Double pricePerNight;

    private Long tripId;
}
