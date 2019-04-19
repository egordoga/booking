package ua.booking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ua.booking.dto.BookingDto;
import ua.booking.entity.Booking;
import ua.booking.entity.Room;
import ua.booking.entity.User;
import ua.booking.service.BookingService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BookingControllerTest {

    private List<Booking> bookings;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingService bookingService;

    @Before
    public void init() {
        bookings = new ArrayList<>();
        User user = new User("Tom", "tom@tom.com");
        Booking booking = new Booking(LocalDate.of(2019, 5, 20),
                LocalDate.of(2019, 5, 25), user, new Room());
        booking.setUser(user);
        bookings.add(booking);
        bookings.add(new Booking());
    }

    @Test
    public void getBookingsForUser() throws Exception {
        when(bookingService.findBookingsByUserName(anyString())).thenReturn(bookings);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/bookings/username/name")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("links")));
    }

    @Test
    public void getAllBookings() throws Exception {
        when(bookingService.findAllBookings()).thenReturn(bookings);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/bookings/username/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("links")));
    }

    @Test
    public void doBooking() throws Exception {
        BookingDto dto = new BookingDto("Cat", 16, "25-05-20", "30-05-20");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(dto);
        when(bookingService.doBooking(any(Booking.class))).thenReturn("OK");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/bookings/order")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("OK")));
    }
}