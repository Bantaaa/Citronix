package org.banta.citronix.web.rest;

import org.banta.citronix.domain.Farm;
import org.banta.citronix.service.FarmService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/farms")
public class FarmController {

    private final FarmService farmService;

    public FarmController(FarmService farmService) {
        this.farmService = farmService;
    }

    @PostMapping("/save")
    public Farm save(@RequestBody Farm farm) {
        return farmService.save(farm);
    }
}
