package org.dandria.warehouse.batch.reader;

import lombok.RequiredArgsConstructor;
import org.dandria.warehouse.batch.model.WarehouseItem;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

@Configuration
@RequiredArgsConstructor
public class WarehouseItemReader {

    private final EntityManagerFactory entityManagerFactory;

    @Value("${batch.page-size:500}")
    private int pageSize;

    @Bean
    public JpaPagingItemReader<WarehouseItem> itemReader() {
        return new JpaPagingItemReaderBuilder<WarehouseItem>()
                .name("warehouseItemReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(pageSize)
                .queryString("SELECT w FROM WarehouseItem w ORDER BY w.id")
                .build();
    }
} 