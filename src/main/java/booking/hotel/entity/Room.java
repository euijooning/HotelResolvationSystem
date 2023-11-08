package booking.hotel.entity;

public class Room {

    // 맴버변수 : 객실번호, 방크기, 객실비용
    private final int roomNumber;
    private final int size;
    private final int price;

    // 생성자 : 값을 받아서 맴버변수에 저장
    public Room(int roomNumber, int size, int price){
        this.roomNumber = roomNumber;
        this.size = size;
        this.price = price;
    }

    // getter 매소드 : 저장하고 있는 객실정보를 돌려준다?
    public int getRoomNo(){
        return roomNumber;
    }

    public int getSize(){
        return size;
    }

    public int getPrice(){
        return price;
    }
}