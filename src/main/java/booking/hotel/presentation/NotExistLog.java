package booking.hotel.presentation;

// 존재하지 않는 예약 또는 객실에 관한 메시지를 출력하는 클래스
public class NotExistLog {

    public void showNotExistReservationIdMessage() {
        System.out.println("예약번호가 존재하지 않습니다.");
    }
    public void showNotExistReservationMessage(){
        System.out.println("예약내역이 존재하지 않습니다.");
    }
    public void showNotExistEmptyRoomMessage(){
        System.out.println("현재 비어있는 객실이 없습니다.");
    }
    public void showNotExistRemainMessage(){
        System.out.println("금액이 부족하여, 예약에 실패했습니다.");
    }
}
