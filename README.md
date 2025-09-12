# Spring Hibernate Product Management System

A modern, scalable product management system built with Spring Boot and Hibernate, featuring batch processing, data synchronization, and performance optimization.

## Features

- Efficient batch processing of large datasets
- Multi-threaded data processing
- Optimized database operations
- Performance monitoring and metrics
- Error handling and validation
- Data synchronization patterns

## Technical Stack

- Java 11
- Spring Boot 2.7.5
- Spring Batch
- Hibernate/JPA
- PostgreSQL
- Docker
- Maven

## Project Structure

- `batch-processor`: Handles large-scale data processing
- `control-products`: Manages product data
- `shopping-list`: Handles product lists
- `stats`: Manages statistics and metrics

## Getting Started

### Prerequisites

- Java 11 or higher
- Maven
- Docker and Docker Compose

### Installation

1. Clone the repository:
```bash
git clone https://github.com/YOUR_USERNAME/spring-hibernate-product.git
cd spring-hibernate-product
```

2. Build the project:
```bash
mvn clean install
```

3. Start the database:
```bash
cd batch-processor
docker-compose up -d
```

4. Run the application:
```bash
mvn spring-boot:run
```

### Testing the Batch Processing

1. Generate test data:
```bash
curl -X POST "http://localhost:8080/api/batch/generate-data?numberOfRecords=1000000"
```

2. Start batch processing:
```bash
curl -X POST "http://localhost:8080/api/batch/process"
```

## Monitoring

- Health check: `http://localhost:8080/actuator/health`
- Metrics: `http://localhost:8080/actuator/metrics`
- Prometheus metrics: `http://localhost:8080/actuator/prometheus`

## Module Purposes and Classes

### batch-processor
- Purpose: Generate demo data and process `warehouse_items` using Spring Batch (chunk-oriented, multi-threaded), with metrics via Actuator/Micrometer.
- Classes:
  - `org.dandria.warehouse.batch.BatchProcessorApplication`: Spring Boot entrypoint for the batch app.
  - `config.BatchConfig`: Configures the Spring Batch `Job` and `Step`:
    - Job: `warehouseDataProcessingJob`
    - Step: `warehouseDataStep` using chunk processing with `batch.chunk-size`, paging with `batch.page-size`, parallelism with `batch.max-threads` (via `TaskExecutor`).
  - `controller.BatchController`:
    - `POST /api/batch/generate-data?numberOfRecords&batchSize`: generates random demo rows.
    - `POST /api/batch/process`: launches the batch job.
  - `model.WarehouseItem`: JPA entity mapped to table `warehouse_items` (optimistic locking with `@Version`).
  - `util.DataGenerator`: Creates random `WarehouseItem` records (name/category/price/quantity/lastUpdated), inserts with `JdbcTemplate.batchUpdate` in configurable batches.
  - `reader.WarehouseItemReader`: Provides `JpaPagingItemReader<WarehouseItem>` (ordered by `id`, `page-size` bound to `batch.page-size`).
  - `processor.WarehouseItemProcessor`: Validates and normalizes price and stock; refreshes `lastUpdated`.
  - `writer.WarehouseItemWriter`: JPA writer bound to the app `EntityManagerFactory`.

### control-products
- Purpose: REST service to add/remove goods in local storage and compute beep/expiration markers with caching.
- Classes:
  - `org.dandria.ControlProductApp`: Spring Boot entrypoint; enables caching.
  - `config.CacheConfig`: Caffeine cache config and `CacheManager` (TTL, capacity, max size).
  - `controller.ControlProductController` (`/control-products`):
    - `POST /{productId}?placeId=...` → add good to a place.
    - `DELETE /{productId}` → remove good.
    - `DELETE /beep` (body: `GoodLocal`) → compute beep flag (expiration reached) via service.
  - `service.ControlProductService` / `ControlProductServiceImpl`: Implements add/delete/beep with caching (`@CacheEvict`, `@Cacheable`) and mapping via `model.Mapper`.
  - `repository.GoodRepository`: JPA repo with native queries for `category_id` and `expiration_date` (both cached).
  - `repository.GoodLocalRepository`, `repository.PlaceRepository`: JPA repositories for local goods and places.
  - `model.Good`, `model.GoodLocal`, `model.Place`: Entities for goods, stored local goods, and storage places.
  - `model.Mapper`: Builders/mappers between entities and DTOs (`GoodLocalDto`).
  - `dto.GoodDto`, `dto.GoodLocalDto`: Transport objects.
  - `exception.ErrorHandler`, `ErrorResponse`, `GoodNotFoundException`, `PlaceNotFoundException`: Minimal error mapping to 404.
  - SQL: `schema.sql` creates tables and seeds demo data; `data.sql` drops tables (reset).

### shopping-list
- Purpose: Skeleton app for shopping list features (controllers/services/repos are placeholders).
- Classes:
  - `org.dandria.ShoppingListApp`: Spring Boot entrypoint.
  - `controller.BasicShopListController`, `controller.CustomShopListController`: placeholders.
  - `service.*` and `repository.*`: placeholder interfaces/impls.

### stats
- Purpose: Minimal module for experiments.
- Classes:
  - `org.dandria.Main`: Prints "Hello world!".

## End-to-end Flow

```mermaid
flowchart LR
    subgraph Client
      A1[POST /api/batch/generate-data]\nnumberOfRecords,batchSize
      A2[POST /api/batch/process]
      A3[GET /actuator/metrics or /prometheus]
      A4[control-products APIs]
    end

    subgraph Batch App (batch-processor)
      B1[BatchController]
      B2[DataGenerator]
      B3[warehouseDataProcessingJob]
      B4[warehouseDataStep]
      B5[JpaPagingItemReader]
      B6[WarehouseItemProcessor]
      B7[JpaItemWriter]
    end

    subgraph DB[PostgreSQL]
      D1[(warehouse_items)]
      D2[(goods/places/categories/expiration_date)]
      D3[(BATCH_\nmetadata tables)]
    end

    subgraph Observability
      O1[Spring Boot Actuator]
      O2[Micrometer]
      O3[Prometheus endpoint]
    end

    A1 --> B1 --> B2 --> D1
    A2 --> B1 --> B3 --> B4 --> B5 --> D1
    B5 --> B6 --> B7 --> D1

    B3 -. writes .-> D3
    B4 -. writes .-> D3

    O1 --> O2 --> O3
    B3 --> O1
    B4 --> O1

    A3 --> O3

    A4 -->|add/delete/beep| CP[ControlProductController]
    CP --> SVC[ControlProductService] --> REPO[Good/Place Repos]
    REPO --> D2
```

## License

This project is licensed under the MIT License - see the LICENSE file for details.
