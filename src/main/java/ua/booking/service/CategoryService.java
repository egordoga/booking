package ua.booking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.booking.entity.Category;
import ua.booking.repo.CategoryRepository;

@Service
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category findCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }
}
