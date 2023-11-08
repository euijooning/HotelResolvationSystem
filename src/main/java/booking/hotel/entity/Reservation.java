package booking.hotel.entity;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Reservation {
    private final String reservationId;
    // 예약 번호
    private Room room;                 // 객실 번호
    private String userName;            // 고객 이름
    private String userPhone;           // 고객 전화번호
    private String reservationDate;       // 예약 날짜

    public Reservation(Room room, String userName, String userPhone, String reservationDate) {
        this.reservationId = UUID.randomUUID().toString();
        this.room = room;
        this.userName = userName;
        this.userPhone = userPhone;
        this.reservationDate = ZonedDateTime.now(ZoneId.of("+9")).format(DateTimeFormatter.ofPattern(reservationDate+"'T'HH:mm:ssz"));
    }

    public String getReservationId() {
        return reservationId;
    }

    public Room getRoom() {
        return room;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public String getReservationDate() {
        return reservationDate;
    }
}
