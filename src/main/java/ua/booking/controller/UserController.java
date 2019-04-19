package ua.booking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.booking.entity.User;
import ua.booking.service.IUserService;

@RestController
@RequestMapping(value = "/api/v1/users", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
public class UserController {


    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user_new")
    public ResponseEntity<String> addUser(@RequestBody User userJson) {
        User user = new User(userJson.getName(), userJson.getEmail());
        User userSave = userService.saveUser(user);
        if (userSave != null) {
            return new ResponseEntity<>("User created is successful", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("User not created", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
