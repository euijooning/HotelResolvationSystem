package booking.hotel.repository;

import booking.hotel.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private ArrayList<User> userList = new ArrayList<>();

    public void createUser(String username, String phoneNumber, int userAsset){ // 고객의 정보를 받아서 고객 리스트에 추가하는 메서드
        userList.add(new User(username, phoneNumber, userAsset));
    }

    public List<User> getUserList() { // 모든 유저 목록을 반환하는 메서드
        return this.userList; // 이걸로 service로 반환하면 거기서 유저 리스트들을 사용할 방법을 정의한다.
    }

    public void setAsset(User user, int userAsset){ // 유저 리스트에 있는 해당 유저 정보를 업데이트 하는 메서드
        user.setUserAsset(userAsset);
    }

}
