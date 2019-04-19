package ua.booking.service;

import ua.booking.entity.Room;

import java.time.LocalDate;
import java.util.List;

public interface IRoomService {
    List<Room> findRoomsByCategoryName(String categoryName);

    Room findRoomByNumber(Integer number);

    String getCost(Room room, LocalDate startDate, LocalDate endDate);

    List<Room> findAllRoomsNoReserved(LocalDate firstDate, LocalDate lastDate);
}
