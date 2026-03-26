package travel.booking.travel.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class BookingRequest {

    private Long userId;

    private Long tripId;

    private Double totalPrice;

    private List<Long> passengerIds;
}
