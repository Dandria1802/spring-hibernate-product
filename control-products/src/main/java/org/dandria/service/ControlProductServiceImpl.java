package org.dandria.service;

import lombok.AllArgsConstructor;
import org.dandria.dto.GoodLocalDto;
import org.dandria.model.GoodLocal;
import org.dandria.model.Mapper;
import org.dandria.repository.GoodLocalRepository;
import org.dandria.repository.GoodRepository;
import org.dandria.repository.PlaceRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class ControlProductServiceImpl implements ControlProductService {
    private final GoodLocalRepository localRepository;
    private final PlaceRepository placeRepository;
    private final GoodRepository repository;

    /**
     * Adds a product to the warehouse inventory
     */
    @Override
    @CacheEvict(value = "goods", allEntries = true)
    public GoodLocalDto addGood(Long productId, Long placeId) {
        //TODO implement input validation for place and product existence
        GoodLocal goodLocal = Mapper.makeGoodLocalDto(productId, placeId);
        GoodLocal save = localRepository.save(goodLocal);
        return Mapper.toGoodLocalDto(save);
    }

    /**
     * Removes a product from the warehouse inventory
     */
    @Override
    @CacheEvict(value = "goods", allEntries = true)
    public void deleteGood(Long productId) {
        localRepository.deleteById(productId);
    }

    /**
     * Checks if a product has exceeded its storage duration
     */
    @Override
    @Cacheable(value = "goods", key = "#goodLocal.id")
    public Boolean getBeep(GoodLocal goodLocal) {
        LocalDateTime dateTimePurchase = goodLocal.getDateTime();
        Long categoryId = repository.findCategoryId(goodLocal.getGoodId());
        Integer expirationDate = repository.findExpirationDate(categoryId, goodLocal.getPlaceId());
        if (dateTimePurchase.plusDays(expirationDate).equals(LocalDateTime.now().withNano(0))) {
            goodLocal.setBeep(true);
        }
        return goodLocal.getBeep();
    }
}
