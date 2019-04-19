package ua.booking.service;

import ua.booking.entity.Category;

public interface ICategoryService {
    Category findCategoryByName(String name);
}
