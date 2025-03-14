package org.dandria.warehouse.batch.processor;

import lombok.extern.slf4j.Slf4j;
import org.dandria.warehouse.batch.model.WarehouseItem;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@Component
public class WarehouseItemProcessor implements ItemProcessor<WarehouseItem, WarehouseItem> {

    private static final BigDecimal MINIMUM_PRICE = new BigDecimal("0.01");
    private static final int MINIMUM_STOCK = 0;

    @Override
    public WarehouseItem process(WarehouseItem item) {
        // Validate and transform the item
        if (item.getPrice().compareTo(MINIMUM_PRICE) < 0) {
            log.warn("Item {} has invalid price {}. Setting to minimum price.", item.getId(), item.getPrice());
            item.setPrice(MINIMUM_PRICE);
        }

        if (item.getQuantityInStock() < MINIMUM_STOCK) {
            log.warn("Item {} has negative stock {}. Setting to 0.", item.getId(), item.getQuantityInStock());
            item.setQuantityInStock(MINIMUM_STOCK);
        }

        item.setLastUpdated(LocalDateTime.now());
        
        log.debug("Processed item: {}", item);
        return item;
    }
} 