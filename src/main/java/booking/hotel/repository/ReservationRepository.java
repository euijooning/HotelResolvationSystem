package booking.hotel.repository;

import booking.hotel.entity.Reservation;
import booking.hotel.entity.Room;

import java.util.ArrayList;
import java.util.List;

public class ReservationRepository {

    List<Reservation> reservationList = new ArrayList<>(); // 예약 리스트

    public List<Reservation> getReservationList() { // 예약 리스트 조회(getReservationList)
        return reservationList;
    }

    // 예약을 생성해서 예약리스트에 넣기.(createResolvation)
    public Reservation createReservation(Room room, String username, String phoneNumber, String reservationDate) {
        // 생성자 생성과 동시에  reservationList에 넘갔다.
        Reservation reservation = new Reservation(room, username, phoneNumber, reservationDate);
        reservationList.add(reservation);
        return reservation;
    }

    // 예약 삭제.(deleteReservation)
    public void deleteReservation(Reservation reservation){
        reservationList.remove(reservation);
    }
}