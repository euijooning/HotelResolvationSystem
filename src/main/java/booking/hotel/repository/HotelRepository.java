package booking.hotel.repository;

import booking.hotel.entity.Hotel;
import booking.hotel.entity.Room;

import java.util.List;

public class HotelRepository {
    Hotel hotel = new Hotel(); // 호텔 객체 생성

    public int getAsset(){
        return hotel.getHotelAsset();
    }

    public List<Room> getRoomList(){
        return hotel.getRoomList();
    }

    public String getAdminPassword(){
        return hotel.adminPassword();
    }

    public void setAsset(int price){
        hotel.setAsset(price);
    }
}
