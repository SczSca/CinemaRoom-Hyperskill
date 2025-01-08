package cinema.Service;


import cinema.Exception.SeatException;
import cinema.Model.DTO.ReturnSeatDTO;
import cinema.Model.DTO.SeatDTO;
import cinema.Model.Request.ReturnRequest;
import cinema.Model.Request.SeatRequest;
import cinema.Model.Request.StatRequest;
import cinema.Model.Response.PurchaseResponse;
import cinema.Model.Response.ReturnResponse;
import cinema.Model.Response.SeatsResponse;
import cinema.Model.Response.StatsResponse;
import cinema.Repository.LocalCinemaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CinemaService {
    private final LocalCinemaRepository localCinemaRepository;

    @Value("${PASSWORD}")
    private String password;

    public ResponseEntity<SeatsResponse> getSeats() {
        int totalRows = localCinemaRepository.getNumberOfRows();
        int totalColumns = localCinemaRepository.getNumberOfColumns();
        List<SeatDTO> seats = localCinemaRepository.getSeats();
        SeatsResponse seatsResponse = SeatsResponse.builder()
                .rows(totalRows)
                .columns(totalColumns)
                .seats(seats)
                .build();

        return new ResponseEntity<SeatsResponse>(seatsResponse, HttpStatus.OK);
    }

    public ResponseEntity<PurchaseResponse> purchaseTicket(SeatRequest seatRequest) {

        return new ResponseEntity<PurchaseResponse>(localCinemaRepository.purchaseTicket(seatRequest), HttpStatus.OK);
    }

    public ResponseEntity<ReturnResponse> returnTicket(ReturnRequest returnRequest) {
        ReturnSeatDTO returnSeatDTO = localCinemaRepository.returnTicket(returnRequest);

        if(!dateValidation(returnSeatDTO.getExpirationToken())){
            throw new SeatException("Wrong token!", HttpStatus.BAD_REQUEST);
        }
        var returnResponse = ReturnResponse.builder()
                .ticket(returnSeatDTO.getSeat())
                .build();
        return new ResponseEntity<>(returnResponse, HttpStatus.OK);
    }
//
    public ResponseEntity<StatsResponse> getStats(String passwordParam) {
        System.out.println(password);
        if(passwordParam == null || !passwordParam.equals(password)){
            throw new SeatException("The password is wrong!", HttpStatus.UNAUTHORIZED);
        }
        System.out.println(passwordParam);

        StatsResponse statsResponse = localCinemaRepository.getStats();
        return new ResponseEntity<StatsResponse>(statsResponse, HttpStatus.OK);
    }

    private boolean dateValidation(Instant expirationDate){
        return Instant.now().isBefore(expirationDate);
    }
}
