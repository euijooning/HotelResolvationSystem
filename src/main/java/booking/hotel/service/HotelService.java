package booking.hotel.service;

import booking.hotel.entity.Reservation;
import booking.hotel.entity.Room;
import booking.hotel.entity.User;
import booking.hotel.repository.HotelRepository;
import booking.hotel.repository.ReservationRepository;
import booking.hotel.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HotelService {

    // 각 객체들을 생성
    HotelRepository hotelRepository = new HotelRepository();
    ReservationRepository reservationRepository = new ReservationRepository();
    UserRepository userRepository = new UserRepository();

    // 1. GET Service
    // 호텔 예약 목록을 반환하는 메서드
    public List<Reservation> getHotelReservationList(){ // 예약 목록 가져오기
        return reservationRepository.getReservationList();
    }

    // 호텔 자산 (보유금)을 반환하는 메서드
    public int getHotelAsset(){
        return hotelRepository.getAsset();
    }

    // 객실 예약을 요청하고 결과를 반환하는 메서드
    public String requestReservation(int roomNumber, String username, String phoneNumber, String date) {
        for (Room room : getBookableRoomList(date)) {  // ReservationList - list에서 해당 date에 예약 가능한 방 정보를 모두 불러온다.
            if (room.getRoomNo() == roomNumber) {  // 그 중에 roomNo와 일치하는 room list가 있다면
                for (User user : userRepository.getUserList()) {
                    if (user.getUserName().equals(username) && user.getUserPhone().equals(phoneNumber)) {
                        if (user.getUserAsset() >= room.getPrice()) {
                            // ReservationRepository에서 id를 새로 생성해주고 Db에 저장해준다.
                            // id를 받아와야 함
                            // -> reservationRapository에서 reservation list를 for문으로 돌리고, if문으로 이름, 번호 같은 애 뽑아서 같은 것의 id값을 찾는다.
                            int updateUserAsset = user.getUserAsset() - room.getPrice();
                            int updateHotelAsset = getHotelAsset() + room.getPrice();
                            user.setUserAsset(updateUserAsset);
                            Reservation reservation = reservationRepository.createReservation(room, username, phoneNumber, date);
                            putHotelAsset(updateHotelAsset);
                            return reservation.getReservationId();  // 찾은 것을 (reservation.getId)리턴한다.
                        } else {
                            return "잔액부족";
                        }
                    }
                }
            }
        }
        return "예약실패";
    }




    // 예약 가능한 객실 목록을 반환하는 메서드
    public List<Room> getBookableRoomList(String date){
        List<Room> list = new ArrayList<>();

        for(int i=0;i<hotelRepository.getRoomList().size();i++){
            list.add(hotelRepository.getRoomList().get(i));
        }

        for(Room room : hotelRepository.getRoomList()) {
            for (Reservation reservation : reservationRepository.getReservationList()) {
                String reservationDate = reservation.getReservationDate().substring(0, 10);
                if (reservationDate.equals(date) && reservation.getRoom().getRoomNo()==room.getRoomNo()) {
                    list.remove(room);
                    break;
                }
            }
        }
        return list;
    }

    // 예약 번호를 기반으로 예약 정보를 반환하는 메서드
    public String getReservationContent(String reservationId) {
        StringBuilder reservationContent = new StringBuilder();

        for (Reservation reservation : reservationRepository.getReservationList()) {
            if (reservation.getReservationId().equals(reservationId)) {

                reservationContent.append(reservation.getReservationId()).append(" / ").append(reservation.getReservationDate()).append(" / ").append(reservation.getRoom().getSize()).append("평");
            }
        }
        return reservationContent.toString();
    }

    // 사용자 이름과 전화번호로 예약 번호 목록을 반환하는 메서드
    public List<String> getReservationIdList(String userName, String phoneNumber){
        List<Reservation> reservations = reservationRepository.getReservationList();
        return reservations.stream().filter(reservation -> reservation.getUsername().equals(userName)
                        && reservation.getPhoneNumber().equals(phoneNumber))
                .map(Reservation::getReservationId)
                .collect(Collectors.toList());
    }



    // 2. POST Service
    public void postNewUser(String username, String phoneNumber, int userAsset) {
        userRepository.createUser(username, phoneNumber, userAsset);
    }



    private void putHotelAsset(int userAsset) {
        hotelRepository.setAsset(userAsset);
    }

}
