package ua.booking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.booking.entity.Option;
import ua.booking.repo.OptionRepository;

@Service
public class OptionService implements IOptionService {

    private final OptionRepository optionRepository;

    @Autowired
    public OptionService(OptionRepository optionRepository) {
        this.optionRepository = optionRepository;
    }

    @Override
    public Option findOptionByName(String optionName) {
        return optionRepository.findByName(optionName);
    }
}
