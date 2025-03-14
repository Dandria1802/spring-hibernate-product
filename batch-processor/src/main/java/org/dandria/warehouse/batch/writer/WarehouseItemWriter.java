package org.dandria.warehouse.batch.writer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dandria.warehouse.batch.model.WarehouseItem;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class WarehouseItemWriter {

    private final EntityManagerFactory entityManagerFactory;

    @Bean
    public JpaItemWriter<WarehouseItem> itemWriter() {
        JpaItemWriter<WarehouseItem> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        
        return writer;
    }
} 