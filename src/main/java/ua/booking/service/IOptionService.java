package ua.booking.service;

import ua.booking.entity.Option;

public interface IOptionService {
    Option findOptionByName(String optionName);
}
