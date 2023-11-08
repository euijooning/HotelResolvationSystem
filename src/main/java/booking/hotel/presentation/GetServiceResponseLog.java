package booking.hotel.presentation;

import booking.hotel.entity.Reservation;
import booking.hotel.entity.Room;

import java.util.List;

// 서비스로부터의 응답을 출력하는 클래스
public class GetServiceResponseLog {

    // 호텔의 모든 예약 내역을 출력
    public void showHotelAllReservationList(List<Reservation> reservations) {
        for(Reservation reservation: reservations) {
            System.out.println(reservation.getReservationId()
                    + " / " + reservation.getRoom().getRoomNumber()
                    + " / " + reservation.getUsername()
                    + " / " + reservation.getPhoneNumber()
                    + " / " + reservation.getReservationDate());
        }
    }

    // 호텔의 보유금을 출력
    public void showHotelAsset(int hotelAsset) {
        System.out.println("호텔의 현재 보유금 : " + hotelAsset + " 원");
    }


    // 사용자의 모든 예약 번호 목록을 출력
    public void showAllUserReservationIdList(List<String> reservationIdList) {
        for(String reservationId : reservationIdList){
            System.out.println(reservationId);
        }
    }

    // 사용자의 예약 정보 메시지 출력
    public void showUserReservationInfoMessage(String reservationInfo){
        System.out.println(reservationInfo);
    }

    // 호텔의 객실 목록을 출력
    public void showGetHotelRoomListMessage(List<Room> roomList) {
        for(Room room : roomList){
            System.out.println(room.getRoomNumber()
                    + " / " + room.getSize()+"평 "
                    + " / " + room.getPrice() + "원");
        }
    }
}
