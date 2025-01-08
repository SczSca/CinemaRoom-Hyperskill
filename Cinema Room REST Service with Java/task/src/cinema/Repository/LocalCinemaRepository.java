package cinema.Repository;

import cinema.Entity.PurchasedSeat;
import cinema.Entity.Seat;
import cinema.Exception.SeatException;
import cinema.Model.DTO.ReturnSeatDTO;
import cinema.Model.DTO.SeatDTO;
import cinema.Model.Request.ReturnRequest;
import cinema.Model.Request.SeatRequest;
import cinema.Model.Request.StatRequest;
import cinema.Model.Response.PurchaseResponse;
import cinema.Model.Response.ReturnResponse;
import cinema.Model.Response.StatsResponse;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.*;

import static cinema.Model.DTO.SeatDTO.fromSeat;
import static java.util.UUID.randomUUID;


@Repository
public class LocalCinemaRepository implements CinemaRepository{
    private final int numberOfRows = 9;
    private final int numberOfColumns = 9;


    private final HashMap<Integer,HashMap<Integer, Seat>> seatsMap = new HashMap<>();
    private final HashMap<String, PurchasedSeat> purchasesMap = new HashMap<>();

    @Override
    public int getNumberOfRows() {
        return numberOfRows;
    }

    @Override
    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    @Override
    public List<SeatDTO> getSeats(){
        List<SeatDTO> seats = new ArrayList<>(numberOfRows * numberOfColumns);

        seatsMap.forEach((row, columns) -> {
            seats.addAll(columns.values().stream()
                    .map(SeatDTO::fromSeat) // Map Entity1 to Entity1DTO
                    .toList());

        });
        return seats;
    }

    public Seat getSeat(SeatRequest seatRequest){
        int row = seatRequest.getRow();
        int columns = seatRequest.getColumn();

        if(row <= 0 || row > numberOfRows || columns <= 0 || columns > numberOfColumns){
            throw new SeatException("The number of a row or a column is out of bounds!", HttpStatus.BAD_REQUEST);
        }

        try{
            return seatsMap.get(row).get(columns);
        }catch(NullPointerException e) {
        System.err.println("NullPointerException: " + e.getMessage());
        throw new IllegalArgumentException("Invalid data: null values detected", e);
        }


    }

    @Override
    public PurchaseResponse purchaseTicket(SeatRequest seatRequest){
        int row = seatRequest.getRow();
        int column = seatRequest.getColumn();

        Seat seat = getSeat(seatRequest);

        if(seat.isBooked()){
            throw new SeatException("The ticket has been already purchased!", HttpStatus.BAD_REQUEST);
        }

        seat.setBooked(true);
        HashMap<Integer, Seat> rowMap = seatsMap.get(row);
        rowMap.put(column, seat);

        String token = String.valueOf(randomUUID());
        Instant expirationToken = Instant.now().plusSeconds(60);
        var purchasedTicket = PurchasedSeat.builder()
                .seat(seat)
                .token(token)
                .expirationToken(expirationToken)
                .build();

        purchasesMap.put(token,purchasedTicket);

        return PurchaseResponse.builder()
                .token(token)
                .ticket(fromSeat(seat))
                .build();



    }

    @Override
    public ReturnSeatDTO returnTicket(ReturnRequest returnRequest){
        try{
            PurchasedSeat purchasedSeat = purchasesMap.get(returnRequest.getToken());

            //change seat status to not booked
            Seat seat = purchasedSeat.getSeat();
            seat.setBooked(false);

            HashMap<Integer, Seat> rowMap = seatsMap.get(seat.getRow());
            rowMap.put(seat.getColumn(),seat);

            //remove purchase from the map
            purchasesMap.remove(returnRequest.getToken());



            return ReturnSeatDTO.builder()
                    .seat(fromSeat(seat))
                    .expirationToken(purchasedSeat.getExpirationToken())
                    .build();

        }catch(NullPointerException e) {
            System.err.println("NullPointerException: " + e.getMessage());
            throw new SeatException("Wrong token!", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public StatsResponse getStats(){

        try{
            int purchasedTickets = purchasesMap.size();
            int currentIncome = purchasesMap.values().stream()
                    .mapToInt(purchasedSeat -> purchasedSeat.getSeat().getPrice())
                    .sum();
            int currentAvailable = numberOfRows * numberOfColumns - purchasedTickets;

            return StatsResponse.builder()
                    .income(currentIncome)
                    .available(currentAvailable)
                    .purchased(purchasedTickets)
                    .build();
        }catch(NullPointerException e){
            System.err.println("NullPointerException: " + e.getMessage());
            throw new SeatException("No stats available!", HttpStatus.BAD_REQUEST);
        }
    }

    @PostConstruct
    private void generateSeats(){

        int min = 8;
        int max = 11;
        for( int i = 1; i <= numberOfRows; i++){
            HashMap<Integer, Seat> row = new HashMap<>();
            for(int j = 1; j <= numberOfColumns; j++){
                int price = i < 5 ? 10 : 8;
                row.put(j,Seat.builder().row(i).column(j).price(price).isBooked(false).build());
            }
            seatsMap.put(i,row);
        }
    }

}
