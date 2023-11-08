package booking.hotel;

import booking.hotel.entity.Reservation;
import booking.hotel.entity.Room;
import booking.hotel.presentation.*;
import booking.hotel.service.HotelService;

import java.util.List;
import java.util.Scanner;

public class HotelApplication {

    public static void main(String[] args) {

        // Interface
        SystemMessageLog systemMessageLog = new SystemMessageLog();
        ExitLog exitLog = new ExitLog();

        SimpleUserInfoLog simpleLogPresentation = new SimpleUserInfoLog();

        FailResultPrintLog failResult = new FailResultPrintLog();
        SuccessfulResultLog successfulResultLog = new SuccessfulResultLog();

        NotExistLog notExistLog = new NotExistLog();
        NotMatchLog notMatchLog = new NotMatchLog();

        RequestInputLog requestInputLog = new RequestInputLog();
        TableMenuSelectLog tableMenuSelectLog = new TableMenuSelectLog();
        GetServiceResponseLog getServiceResponseLog = new GetServiceResponseLog();


        // Service
        HotelService hotelService = new HotelService();

        // Scanner
        Scanner input = new Scanner(System.in);

        // Program
        returnToStartMenu : while(true){
            boolean isAdminMode = false;
            boolean isInStartMenu = true;

            // 시작메뉴 + (관리자메뉴)
            while (isInStartMenu){
                tableMenuSelectLog.showSelectableStartMenu();
                if(isAdminMode){
                    tableMenuSelectLog.showSelectableHiddenMenu();
                }
                requestInputLog.showRequestMenuNumberMessage();
                String startMenuSelectCommand = input.nextLine();
                switch (startMenuSelectCommand){
                    case "1":   // 메인 메뉴. (호텔입장하기)
                        isInStartMenu = false;
                        break;
                    case "2":   // 시스템 종료.
                        systemMessageLog.showSystemExitMessage();
                        System.exit(0);
                        break;
                    case "3":  // 관리자모드1. 모든 예약내역 보여주기
                    case "4":  // 관리자모드2. 호텔 보유자산 보여주기

                        // 해당 커멘드는 관리자 모드에서만 동작하도록 만들기
                        if(isAdminMode){
                            isAdminMode = false;
                            switch (startMenuSelectCommand){
                                case "3":
                                    List<Reservation> reservationList = hotelService.getHotelReservationList();
                                    if(reservationList.size() == 0){
                                        notExistLog.showNotExistReservationMessage();
                                    }else{
                                        getServiceResponseLog.showHotelAllReservationList(reservationList);
                                    }
                                    continue;
                                case "4":
                                    int hotelCurrentAsset = hotelService.getHotelAsset();
                                    getServiceResponseLog.showHotelAsset(hotelCurrentAsset);
                                    continue;
                            }
                        }
                    default:
                        // 이외 커멘드가 들어오면, 커멘드로 관리자모드 진입여부를 판단하여, 다음사이클에서의 액션을 결정.
                        isAdminMode = hotelService.validateAdminPassword(startMenuSelectCommand);
                        if(!isAdminMode){
                            notMatchLog.showNotMatchCommandMessage();
                        }
                }
            }

            // 호텔 입장
            simpleLogPresentation.showSimpleRequestUserInfoMessage();

            // 유저 정보를 입력 : 이름, 휴대전화번호, 자산
            requestInputLog.showRequestUsernameMessage();
            String username = input.nextLine();
            if(username.equals("")){
                notMatchLog.showNotMatchStringFormatMessage();
                continue;
            }

            requestInputLog.showRequestPhoneNumberMessage();
            String userPhone = input.nextLine();
            boolean isValidPhoneNumber = hotelService.validatePhoneNumber(userPhone);
            if(!isValidPhoneNumber) {
                notMatchLog.showNotMatchPhoneNumberFormatMessage();
                continue;
            }

            int userAsset;
            try {
                requestInputLog.showRequestUserAssetMessage();
                userAsset = Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("잘못된 형식의 숫자입니다.");
                continue;
            }

            // 유저가 기존 DB에 있는지 여부를 판단하여, 업데이트 할 지, 새로 생성 할 지를 결정한다.
            boolean hasUserInDB = hotelService.validateUserDataInDB(username, userPhone);
            if(hasUserInDB){
                hotelService.putUserAsset(username, userPhone, userAsset);
            }else{
                hotelService.postNewUser(username, userPhone, userAsset);
            }

            // 메인메뉴 진입.
            while (true){
                tableMenuSelectLog.showSelectableMainMenu();
                requestInputLog.showRequestMenuNumberMessage();
                String selectInput = input.nextLine();
                switch (selectInput){
                    case "1":  // 예약
                        // 정규식을 이용한 날짜 포맷 검증 및 예약 범위 검증
                        requestInputLog.showRequestDateTimeMessage();
                        String reservationRequestDate = input.nextLine();

                        boolean isValidDate = hotelService.validateDateFormat(reservationRequestDate);

                        if(!isValidDate){
                            notMatchLog.showNotMatchDateTimeFormatMessage();
                            continue;
                        }

                        // 예약 가능한 방정보를 받아와 있는 경우에만 예약을 진행하기.
                        List<Room> roomList = hotelService.getBookableRoomList(reservationRequestDate);
                        if(roomList.size() == 0){
                            notExistLog.showNotExistEmptyRoomMessage();
                        } else {
                            getServiceResponseLog.showGetHotelRoomListMessage(roomList);
                            requestInputLog.showRequestRoomNumberMessage();
                            int selectedRoomNumber;

                            try {
                                selectedRoomNumber = Integer.parseInt(input.nextLine());
                            } catch(NumberFormatException e){
                                continue;
                            }
                            // 예약 요청 시, 소지금 여부를 체크하여 케이스에 맞는 액션을 취합니다.
                            String reservationResponse = hotelService.requestReservation(selectedRoomNumber, username, userPhone, reservationRequestDate);
                            switch (reservationResponse){
                                case "잔액부족":
                                    notExistLog.showNotExistRemainMessage();
                                    continue;
                                case "예약실패":
                                    failResult.showReservationFailMessage();
                                    continue;
                                default:  // 예약성공
                                    successfulResultLog.showSuccessReservationMessage(reservationResponse);
                                    break;
                            }
                            continue;
                        }
                    case "2":  // 예약번호 조회 : 유저이름, 유저전화번호를 이용
                        requestInputLog.showRequestUsernameMessage();
                        String searchUserName = input.nextLine();
                        if(searchUserName.equals("")){
                            notMatchLog.showNotMatchStringFormatMessage();
                            continue;
                        }

                        requestInputLog.showRequestPhoneNumberMessage();
                        String searchUserPhone = input.nextLine();
                        isValidPhoneNumber = hotelService.validatePhoneNumber(searchUserPhone);

                        if(!isValidPhoneNumber) {
                            notMatchLog.showNotMatchPhoneNumberFormatMessage();
                            continue;
                        }

                        // 예약번호 리스트를 가져와 유무에 따라 각기 다른 화면을 보여주기
                        List<String> reservationIdList = hotelService.getReservationIdList(searchUserName, searchUserPhone);
                        if(reservationIdList.size() == 0){
                            notExistLog.showNotExistReservationIdMessage();
                        } else {
                            getServiceResponseLog.showAllUserReservationIdList(reservationIdList);
                        }
                        continue;
                    case "3":  // 예약내역 조회 : 예약번호를 이용
                        // 예약번호를 입력
                        requestInputLog.showRequestReservationIdMessage();
                        String reservationId = input.nextLine();

                        // 예약번호에 따라 내역이 있는지 유무를 판별하여, 그에 맞는 결과를 보여줍니다.
                        String reservationApiResponse = hotelService.getReservationContent(reservationId);
                        if(reservationApiResponse.equals("")){
                            notExistLog.showNotExistReservationMessage();
                        } else {
                            getServiceResponseLog.showUserReservationInfoMessage(reservationApiResponse);
                        }
                        continue;
                    case "4":  // 예약 취소 : 예약번호를 이용
                        // 예약번호를 입력받아
                        requestInputLog.showRequestReservationIdMessage();
                        reservationId = input.nextLine();

                        // 검증한다.
                        reservationApiResponse = hotelService.getReservationContent(reservationId);
                        if(reservationApiResponse.equals("")){
                            notExistLog.showNotExistReservationMessage();
                            continue;
                        }else{
                            getServiceResponseLog.showUserReservationInfoMessage(reservationApiResponse);
                            requestInputLog.showRequestCancelCommandMessage();
                        }

                        // 삭제여부를 재확인한다.
                        String cancelConfirmCommand = input.nextLine();
                        switch (cancelConfirmCommand){
                            case "Y":  // 삭제
                                boolean cancelReservationResponse =  hotelService.deleteReservationById(reservationId);
                                if (cancelReservationResponse){
                                    successfulResultLog.showSuccessCancelReservationMessage();
                                } else {
                                    failResult.showCancelReservationFailMessage();                                }
                                break;
                            case "N": // 삭제 취소
                                failResult.showCancelReservationFailMessage();
                                break;
                            default:
                                notMatchLog.showNotMatchCommandMessage();
                        }
                        continue;
                    case "5":  // 호텔 나가기
                        exitLog.showApplicationExitMainMenuMessage();
                        continue returnToStartMenu;
                    default:   // 이외 커멘드 입력 시 처리
                        notMatchLog.showNotMatchMenuNumberMessage();
                }
            }
        }

    }

}