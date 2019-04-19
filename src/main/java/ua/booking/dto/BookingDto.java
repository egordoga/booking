package ua.booking.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

/**
 * Класс создан для того чтобы не писать сложный ObjectMapper
 * для сериализации/десериализации даты. Так проще и понятнее ИМХО
 */

@Data
@NoArgsConstructor
public class BookingDto {
    private String username;
    private Integer roomNumber;
    private String startDate;
    private String endDate;
    private ArrayList<String> options = new ArrayList<>();

    public BookingDto(String username, Integer roomNumber, String startDate, String endDate) {
        this.username = username;
        this.roomNumber = roomNumber;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
