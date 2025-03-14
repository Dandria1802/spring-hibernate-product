package org.dandria.warehouse.batch.util;

import lombok.RequiredArgsConstructor;
import org.dandria.warehouse.batch.model.WarehouseItem;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class DataGenerator {

    private final JdbcTemplate jdbcTemplate;
    private final Random random = new Random();

    private static final String[] CATEGORIES = {
            "Electronics", "Clothing", "Books", "Home & Garden",
            "Sports", "Toys", "Food", "Beauty", "Automotive", "Tools"
    };

    private static final String[] ITEM_PREFIXES = {
            "Premium", "Basic", "Ultra", "Super", "Mega",
            "Pro", "Elite", "Standard", "Classic", "Deluxe"
    };

    private static final String[] ITEM_TYPES = {
            "Widget", "Gadget", "Tool", "Device", "Kit",
            "Set", "Pack", "Bundle", "Collection", "System"
    };

    @Transactional
    public void generateTestData(int numberOfRecords, int batchSize) {
        List<WarehouseItem> batch = new ArrayList<>(batchSize);
        
        for (int i = 0; i < numberOfRecords; i++) {
            batch.add(createRandomItem());
            
            if (batch.size() >= batchSize) {
                saveBatch(batch);
                batch.clear();
            }
        }
        
        if (!batch.isEmpty()) {
            saveBatch(batch);
        }
    }

    private WarehouseItem createRandomItem() {
        String name = ITEM_PREFIXES[random.nextInt(ITEM_PREFIXES.length)] + " " +
                     ITEM_TYPES[random.nextInt(ITEM_TYPES.length)];
        
        return new WarehouseItem(
                null,
                name,
                CATEGORIES[random.nextInt(CATEGORIES.length)],
                BigDecimal.valueOf(random.nextDouble() * 1000).setScale(2, BigDecimal.ROUND_HALF_UP),
                random.nextInt(1000),
                LocalDateTime.now(),
                0L
        );
    }

    private void saveBatch(List<WarehouseItem> items) {
        String sql = "INSERT INTO warehouse_items (name, category, price, quantity_in_stock, last_updated, version) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

        List<Object[]> batchArgs = new ArrayList<>();
        
        for (WarehouseItem item : items) {
            batchArgs.add(new Object[]{
                    item.getName(),
                    item.getCategory(),
                    item.getPrice(),
                    item.getQuantityInStock(),
                    item.getLastUpdated(),
                    item.getVersion()
            });
        }

        jdbcTemplate.batchUpdate(sql, batchArgs);
    }
} 