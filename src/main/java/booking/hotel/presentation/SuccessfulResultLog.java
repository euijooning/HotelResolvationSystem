package booking.hotel.presentation;

public class SuccessfulResultLog {

    // 예약 성공 시 출력할 메시지를 나타내는 메서드
    public void showSuccessReservationMessage(String reservationId){
        System.out.println("예약에 성공했습니다.");
        System.out.println("예약번호는 " + reservationId + " 입니다.");
    }

    // 예약 취소 성공 시 출력할 메시지를 나타내는 메서드
    public void showSuccessCancelReservationMessage(){
        System.out.println("예약이 성공적으로 취소되었습니다.");
    }
}
