package booking.hotel.entity;

public class User {  // 멤버변수
    private final String userName;  // id로 사용한다는 것은 재할당하면 오류가 나는 변수에 final을 붙여서 상수로 바꿔준다는 의미
    private final String userPhone;
    private int userAsset; // userAsset은 호텔 예약 시 -재할당 해야하므로 final 붙이지 않는다.

    public User(String userName, String userPhone, int userAsset) { // 생성자
        this.userName = userName;
        this.userPhone = userPhone;
        this.userAsset = userAsset;
    }

    public String getUserName() {   // 고객의 이름 getter
        return userName;
    }

    public String getUserPhone() {  // 고객의 전화번호 getter
        return userPhone;
    }

    public int getUserAsset() {     // 고객의 소지금 getter
        return userAsset;
    }

    public void setUserAsset(int userAsset) {  // 고객의 소지금을 업데이트할 때 사용하는 setter
        this.userAsset = userAsset; // 고객의 정보를 입력하는 시점에서 asset도 결졍되므로 이렇게 쓴다.
    }
}
