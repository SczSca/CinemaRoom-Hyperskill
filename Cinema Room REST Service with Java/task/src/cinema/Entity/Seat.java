package cinema.Entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Seat {

    private int row;
    private int column;
    private int price;
    private boolean isBooked;
//    private boolean isBooked;
}
