package org.banta.citronix.service;

import org.banta.citronix.domain.Farm;
import org.banta.citronix.repository.FarmRepository;
import org.springframework.stereotype.Service;

@Service
public class FarmService {
    private FarmRepository farmRepository;

    public FarmService(FarmRepository farmRepository) {
        this.farmRepository = farmRepository;
    }

    public Farm save(Farm farm) {
        return farmRepository.save(farm);
    }

    public Farm update(Farm farm) {
        return farmRepository.save(farm);
    }

    public void delete(Farm farm) {
        farmRepository.delete(farm);
    }
}
