package travel.booking.travel.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DestinationRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String country;
}
