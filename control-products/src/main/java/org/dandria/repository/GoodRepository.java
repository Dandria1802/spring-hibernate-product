package org.dandria.repository;

import org.dandria.model.Good;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodRepository extends JpaRepository<Good, Long> {

    @Cacheable(value = "categoryIds", key = "#goodId")
    @Query(value = "select category_id " +
            "from goods " +
            "where id = :goodId", nativeQuery = true)
    Long findCategoryId(Long goodId);

    @Cacheable(value = "expirationDates", key = "#categoryId + '-' + #placeId")
    @Query(value = "select number_days_storage " +
            "from expiration_date " +
            "where category_id = :categoryId and place_id = :placeId", nativeQuery = true)
    Integer findExpirationDate(Long categoryId, Long placeId);
}
