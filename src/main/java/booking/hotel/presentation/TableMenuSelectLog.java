package booking.hotel.presentation;

public class TableMenuSelectLog {

    // 메인 메뉴 옵션을 출력하는 메서드
    public void showSelectableMainMenu(){
        System.out.println("1. 예약");
        System.out.println("2. 예약번호 조회");
        System.out.println("3. 예약내역 조회");
        System.out.println("4. 예약 취소");
        System.out.println("5. 호텔 나가기");
    }

    // 시작 메뉴 옵션을 출력하는 메서드
    public void showSelectableStartMenu(){
        System.out.println("1. 호텔 입장하기");
        System.out.println("2. 시스템 종료");
    }

    // 숨겨진 메뉴 옵션을 출력하는 메서드
    public void showSelectableHiddenMenu(){
        System.out.println("3. 호텔의 모든 예약내역 조회");
        System.out.println("4. 호텔보유금 내역 조회");
    }

}