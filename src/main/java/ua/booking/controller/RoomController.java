package ua.booking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.booking.assembler.RoomResourcesAssembler;
import ua.booking.entity.Category;
import ua.booking.entity.Option;
import ua.booking.entity.Room;
import ua.booking.exeption.ArgsWrongException;
import ua.booking.service.ICategoryService;
import ua.booking.service.IOptionService;
import ua.booking.service.IRoomService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/v1/rooms", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
public class RoomController {

    private final IRoomService roomService;
    private final IOptionService optionService;
    private final RoomResourcesAssembler assembler;
    private final ICategoryService categoryService;

    @Autowired
    public RoomController(IRoomService roomService, IOptionService optionService, RoomResourcesAssembler assembler, ICategoryService categoryService) {
        this.roomService = roomService;
        this.optionService = optionService;
        this.assembler = assembler;
        this.categoryService = categoryService;
    }

    @GetMapping("/free/{startDate}/{endDate}")
    public ResponseEntity<Resources<Resource<Room>>> getFreeRooms(@PathVariable String startDate,
                                                                  @PathVariable String endDate) throws /*EntityNotFoundException,*/ ArgsWrongException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MM-yy");
        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);
        if (start.isBefore(LocalDate.now()) || start.compareTo(end) > 0) {
            throw new ArgsWrongException("Date wrong");
        }
        List<Room> freeRooms = roomService.findAllRoomsNoReserved(start, end);
        List<Resource<Room>> rooms = freeRooms.stream().map(assembler::toResource).collect(Collectors.toList());
        Resources<Resource<Room>> resources = new Resources<>(rooms, linkTo(methodOn(RoomController.class)
                .getFreeRooms(startDate, endDate)).withSelfRel());
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @GetMapping("/{categoryName}")
    public ResponseEntity<Resources<Resource<Room>>> getRoomsByCategoryName(@PathVariable String categoryName) {
        Category category = categoryService.findCategoryByName(categoryName);
        if (category == null) {
            throw new ArgsWrongException("There is no category with this name");
        }
        List<Resource<Room>> rooms = roomService.findRoomsByCategoryName(categoryName).stream().map(assembler::toResource)
                .collect(Collectors.toList());
        Resources<Resource<Room>> resources = new Resources<>(rooms, linkTo(methodOn(RoomController.class)
                .getRoomsByCategoryName(categoryName)).withSelfRel());
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @GetMapping("/cost/{roomNumber}/{startDate}/{endDate}")
    public ResponseEntity<String> getCost(@PathVariable Integer roomNumber, @PathVariable String startDate,  // не понятно в каком виде надо вернуть сумму, поэтому верну простой строкой
                                          @PathVariable String endDate,
                                          @RequestParam(name = "breakfast", required = false) boolean breakfast,
                                          @RequestParam(name = "cleaning", required = false) boolean cleaning,
                                          @RequestParam(name = "minibar", required = false) boolean minibar,
                                          @RequestParam(name = "ironing", required = false) boolean ironing) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MM-yy");
        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);

        if (start.isBefore(LocalDate.now()) || start.compareTo(end) > 0) {
            throw new ArgsWrongException("Date wrong");
        }

        Room room = roomService.findRoomByNumber(roomNumber);
        if (room == null) {
            throw new ArgsWrongException("There is no room with this number");
        }

        if (breakfast) {
            Option option = optionService.findOptionByName("breakfast");
            room.getOptions().add(option);
        }
        if (cleaning) {
            Option option = optionService.findOptionByName("cleaning");
            room.getOptions().add(option);
        }
        if (minibar) {
            Option option = optionService.findOptionByName("minibar");
            room.getOptions().add(option);
        }
        if (ironing) {
            Option option = optionService.findOptionByName("ironing");
            room.getOptions().add(option);
        }

        String response = roomService.getCost(room, start, end);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
