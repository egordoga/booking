package ua.booking.assembler;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;
import ua.booking.entity.Room;

@Component
public class RoomResourcesAssembler implements ResourceAssembler<Room, Resource<Room>> {

    @Override
    public Resource<Room> toResource(Room room) {
        return new Resource<>(room);
    }
}
