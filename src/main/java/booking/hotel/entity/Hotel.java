package booking.hotel.entity;

import java.util.ArrayList;
import java.util.List;

public class Hotel {
    private final int INITIAL_ROOM_COUNT = 15;
    private final int INIITAL_HOTEL_ASSSET = 100_000;

    // 관리자 비밀번호
    private final String ADMIN_PASSWORD = "adminpassword";

    // 호텔의 객실 목록
    private List<Room> roomList = new ArrayList<>();

    // 호텔의 자산 (보유금)
    private int hotelAsset;

    // Hotel 클래스의 생성자
    public Hotel(){
        // 호텔을 초기화하고 객실 목록을 생성하는 생성자
        for (int i = 1; i <= INITIAL_ROOM_COUNT; i++) {
            // 초기에 15개의 객실을 생성하고, 객실의 크기와 가격을 지정
            if (i < 6) this.roomList.add(new Room(i, 10, 80000));
            else if (i < 11) this.roomList.add(new Room(i, 15, 120000));
            else this.roomList.add(new Room(i, 18, 150000));
        }

        // 호텔 자산을 초기화
        this.hotelAsset = INIITAL_HOTEL_ASSSET;
    }

    // 객실 목록을 반환하는 메서드
    public List<Room> getRoomList() {
        return roomList;
    }

    // 호텔의 현재 자산 (보유금)을 반환하는 메서드
    public int getHotelAsset() {
        return hotelAsset;
    }

    // 관리자 비밀번호를 반환하는 메서드
    public String adminPassword() {
        return ADMIN_PASSWORD;
    }

    // 호텔의 자산 (보유금)을 업데이트하는 메서드
    public void setAsset(int updatePayload){
        this.hotelAsset = updatePayload;
    }
}

