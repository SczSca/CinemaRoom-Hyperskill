package cinema.Repository;


import cinema.Model.DTO.ReturnSeatDTO;
import cinema.Model.DTO.SeatDTO;
import cinema.Model.Request.ReturnRequest;
import cinema.Model.Request.SeatRequest;
import cinema.Model.Request.StatRequest;
import cinema.Model.Response.PurchaseResponse;
import cinema.Model.Response.ReturnResponse;
import cinema.Model.Response.StatsResponse;

import java.util.List;

public interface CinemaRepository {

    int getNumberOfRows();
    int getNumberOfColumns();
    List<SeatDTO> getSeats();
    PurchaseResponse purchaseTicket(SeatRequest seatRequest);
    ReturnSeatDTO returnTicket(ReturnRequest returnRequest);
    StatsResponse getStats();

}
