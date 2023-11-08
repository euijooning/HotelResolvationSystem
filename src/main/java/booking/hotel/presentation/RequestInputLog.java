package booking.hotel.presentation;

public class RequestInputLog {

    // 사용자에게 이름을 입력하라는 메시지를 출력하는 메서드
    public void showRequestUsernameMessage(){
        System.out.print("이름 : ");
    }

    // 사용자에게 전화번호를 입력하라는 메시지를 출력하는 메서드
    public void showRequestPhoneNumberMessage(){
        System.out.print("전화번호 : ");
    }

    // 사용자에게 소지금을 입력하라는 메시지를 출력하는 메서드
    public void showRequestUserAssetMessage(){
        System.out.print("소지금 : ");
    }

    // 사용자에게 예약번호를 입력하라는 메시지를 출력하는 메서드
    public void showRequestReservationIdMessage() {
        System.out.print("예약번호를 입력해주세요 : ");
    }

    // 사용자에게 메뉴 번호를 입력하라는 메시지를 출력하는 메서드
    public void showRequestMenuNumberMessage(){
        System.out.print("원하시는 메뉴 번호를 입력해 주세요 : ");
    }

    // 사용자에게 날짜를 입력하라는 메시지를 출력하는 메서드
    public void showRequestDateTimeMessage() {
        System.out.print("오늘부터 일주일 내에 원하는 날짜를 형식에 맞게 입력해주세요.(0000-00-00) : ");
    }

    // 사용자에게 객실 번호를 입력하라는 메시지를 출력하는 메서드
    public void showRequestRoomNumberMessage() {
        System.out.print("예약을 원하시는 객실 번호를 입력해주세요. : ");
    }

    // 사용자에게 예약 취소 여부를 확인하는 메시지를 출력하는 메서드
    public void showRequestCancelCommandMessage(){
        System.out.print("정말로 예약을 취소하시겠습니까? (Y/N) > ");
    }

}