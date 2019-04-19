package ua.booking.service;

import ua.booking.entity.User;

public interface IUserService {
    User saveUser(User user);

    User findUserByName(String name);
}
