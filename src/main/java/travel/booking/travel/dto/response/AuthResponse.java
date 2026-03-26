package travel.booking.travel.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AuthResponse {

    private String token;
    private String type;
    private Long id;
    private String name;
    private String email;
    private List<String> roles;
}
