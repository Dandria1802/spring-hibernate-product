package org.dandria.controller;

import lombok.AllArgsConstructor;
import org.dandria.dto.GoodLocalDto;
import org.dandria.model.GoodLocal;
import org.dandria.service.ControlProductService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/control-products")
public class ControlProductController {
    private final ControlProductService service;

    /**
     * Adds a product to the warehouse inventory
     * @param productId The ID of the product to add
     * @param placeId The ID of the storage location
     * @return DTO containing the added product information
     */
    @PostMapping("/{productId}")
    public GoodLocalDto addGood(@PathVariable Long productId, @RequestParam Long placeId) {
        return service.addGood(productId, placeId);
    }

    /**
     * Removes a product from the warehouse inventory
     * @param productId The ID of the product to remove
     */
    @DeleteMapping("/{productId}")
    public void deleteGood(@PathVariable Long productId) {
        service.deleteGood(productId);
    }

    /**
     * Checks if a product has exceeded its storage duration
     * @param goodLocal The product to check
     * @return true if the product has exceeded its storage duration, false otherwise
     */
    @DeleteMapping("/beep")
    public Boolean getBeep(@Valid @RequestBody GoodLocal goodLocal) {
        return service.getBeep(goodLocal);
    }
}
