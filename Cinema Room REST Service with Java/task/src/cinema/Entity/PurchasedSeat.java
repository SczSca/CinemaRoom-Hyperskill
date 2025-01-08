package cinema.Entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Builder
public class PurchasedSeat {
    private String token;
    private Seat seat;
    private Instant expirationToken;

}
