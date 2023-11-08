package booking.hotel.service;

import booking.hotel.entity.Reservation;
import booking.hotel.entity.Room;
import booking.hotel.entity.User;
import booking.hotel.repository.HotelRepository;
import booking.hotel.repository.ReservationRepository;
import booking.hotel.repository.UserRepository;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
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
            if (room.getRoomNumber() == roomNumber) {  // 그 중에 방 번호와 일치하는 room list가 있다면
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

        List<Room> availableList = new ArrayList<>(hotelRepository.getRoomList());

        for(Room room : hotelRepository.getRoomList()) {
            for (Reservation reservation : reservationRepository.getReservationList()) {
                String reservationDate = reservation.getReservationDate().substring(0, 10);
                if (reservationDate.equals(date) && reservation.getRoom().getRoomNumber() == room.getRoomNumber()) {
                    availableList.remove(room);
                    break;
                }
            }
        }
        return availableList;
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


    // 사용자의 소지금을 업데이트하는 메서드
    public void putUserAsset(String userName, String userPhone, int userAsset) {
        for (User user : userRepository.getUserList()) {
            // 이름, 전화번호가 모두 일치하는 유저리스트가 있다면
            if (userName.equals(user.getUserName()) && userPhone.equals(user.getUserPhone())) {
                // 유저 목록의 소지금을 변경하는 매서드를 실행.
                userRepository.setAsset(user, userAsset);
                return;
            }
        }
    }


    // 호텔 자산 (보유금)을 업데이트하는 메서드
    public void putHotelAsset(int userAsset){
        hotelRepository.setAsset(userAsset);
    }


    // 4. DELETE Service
    // 예약 번호를 기반으로 예약을 삭제하는 메서드
    public boolean deleteReservationById(String reservationId){
        for (Reservation reservation : reservationRepository.getReservationList()) {
            if(reservation.getReservationId().equals(reservationId)){
                int updateHotelAsset = getHotelAsset() - reservation.getRoom().getPrice();
                putHotelAsset(updateHotelAsset);
                reservationRepository.deleteReservation(reservation);
                return true;
            }
        }
        return false;
    }


    // 5. Validation Service

    // 사용자 이름과 전화번호를 기반으로 데이터의 유효성을 검사하는 메서드
    public boolean validateUserDataInDB(String userName, String userPhone) {
        for (User user : userRepository.getUserList()) {
            if (userName.equals(user.getUserName()) && userPhone.equals(user.getUserPhone())) {
                return true;
            }
        }
        return false;
    }

    // 날짜 형식의 유효성을 검사하는 메서드
    public boolean validateDateFormat(String date) {
        try {
            SimpleDateFormat dateFormatParser = new SimpleDateFormat("yyyy-MM-dd");
            dateFormatParser.setLenient(false);
            dateFormatParser.parse(date);
            // 현재 날짜와 비교하여 7일 이내 여부를 확인
            return LocalDate.now().compareTo(LocalDate.parse(date)) <= 0 && LocalDate.now().plusDays(7).isAfter(LocalDate.parse(date));
        } catch (Exception e) {
            return false;
        }
    }

    // 전화번호 형식의 유효성을 검사하는 메서드
    public boolean validatePhoneNumber(String phoneNumber){
        return Pattern.matches("^01(?:0|1|[6-9])-\\d{4}-\\d{4}$", phoneNumber);
    }

    // 관리자 비밀번호의 유효성을 검사하는 메서드
    public boolean validateAdminPassword(String password){
        return hotelRepository.getAdminPassword().equals(password);
    }
}