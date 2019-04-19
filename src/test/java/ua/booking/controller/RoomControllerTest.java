package ua.booking.controller;

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
import ua.booking.entity.Room;
import ua.booking.service.RoomService;

import java.time.LocalDate;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RoomControllerTest {

    private Room room;


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoomService roomService;

    @Before
    public void init() {
        room = new Room();
        room.setNumber(15);
    }

    @Test
    public void getFreeRooms() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/rooms/free/{startDate}/{endDate}", "25-05-20", "30-05-20")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("links")));
    }

    @Test
    public void getRoomsByCategoryName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/rooms/luxury")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("links")));
    }

    @Test
    public void getCost() throws Exception {
        when(roomService.getCost(any(Room.class), any(LocalDate.class), any(LocalDate.class))).thenReturn("OK");
        when(roomService.findRoomByNumber(15)).thenReturn(room);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/rooms/cost/{roomNumber}/{startDate}/{endDate}",
                15, "25-05-20", "30-05-20")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("OK")));
    }
}