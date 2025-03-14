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

## License

This project is licensed under the MIT License - see the LICENSE file for details.
