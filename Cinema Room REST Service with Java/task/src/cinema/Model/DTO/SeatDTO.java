package cinema.Model.DTO;

import cinema.Entity.Seat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class SeatDTO {
    private int row;
    private int column;
    private int price;

    public SeatDTO(Seat seat){
        this.row = seat.getRow();
        this.column = seat.getColumn();
        this.price = seat.getPrice();
    }

    public static SeatDTO fromSeat(Seat seat){
        return new SeatDTO(seat);
    }
}
