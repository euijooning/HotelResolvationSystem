package booking.hotel.entity;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Reservation {
    private final String reservationId;
    // 예약 번호
    private Room room;                 // 객실 번호
    private String username;            // 고객 이름
    private String phoneNumber;           // 고객 전화번호
    private String reservationDate;       // 예약 날짜

    public Reservation(Room room, String username, String phoneNumber, String reservationDate) {
        this.reservationId = UUID.randomUUID().toString().substring(0, 10);
        this.room = room;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.reservationDate = ZonedDateTime.now(ZoneId.of("+9")).format(DateTimeFormatter.ofPattern(reservationDate+"'T'HH:mm:ssz"));
    }

    public String getReservationId() {
        return reservationId;
    }

    public Room getRoom() {
        return room;
    }

    public String getUsername() {
        return username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getReservationDate() {
        return reservationDate;
    }
}
