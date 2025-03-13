package org.dandria.service;

import org.dandria.dto.GoodLocalDto;
import org.dandria.model.GoodLocal;

public interface ControlProductService {
    /**
     * Adds a product to the warehouse inventory at a specific location
     * @param productId The ID of the product to add
     * @param placeId The ID of the storage location
     * @return DTO containing the added product information
     */
    GoodLocalDto addGood(Long productId, Long placeId);

    /**
     * Removes a product from the warehouse inventory
     * @param productId The ID of the product to remove
     */
    void deleteGood(Long productId);

    /**
     * Checks if a product has exceeded its storage duration
     * @param goodLocal The product to check
     * @return true if the product has exceeded its storage duration, false otherwise
     */
    Boolean getBeep(GoodLocal goodLocal);
}
