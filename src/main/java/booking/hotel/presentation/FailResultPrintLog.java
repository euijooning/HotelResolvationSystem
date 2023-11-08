package booking.hotel.presentation;

// 예약 또는 예약 취소 실패 시 출력하는 메시지를 표시하는 클래스
public class FailResultPrintLog {

    // 예약 실패 시 출력되는 메시지
    public void showReservationFailMessage(){
        System.out.println("입력하신 방번호를 다시 확인해주세요. 예약에 실패하였습니다.");
    }

    // 예약 취소 실패 시 출력되는 메시지
    public void showCancelReservationFailMessage(){
        System.out.println("예약 취소에 실패하였습니다.");
    }
}
