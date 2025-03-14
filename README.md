# spring-hibernate-product 

A modern, scalable warehouse management system built with Spring Boot, featuring product tracking, inventory management, and expiration monitoring.

## Features

- **Product Management**
  - Add products to specific warehouse locations
  - Track product inventory
  - Monitor product expiration dates
  - Automatic alerts for expired products

- **Performance Optimizations**
  - Caching implementation using Caffeine
  - Efficient database queries
  - Optimized data retrieval

- **Technical Stack**
  - Spring Boot 2.7.5
  - PostgreSQL 14
  - Docker & Docker Compose
  - Maven
  - JPA/Hibernate
  - Caffeine Cache

## Project Structure

The project consists of three main modules:

1. **Control Products Module**
   - Manages product inventory
   - Handles product placement
   - Monitors product expiration
   - REST API endpoints for product operations

2. **Shopping List Module**
   - Manages shopping lists
   - Tracks required products
   - Handles shopping list operations

3. **Stats Module**
   - Provides analytics and statistics
   - Tracks warehouse metrics
   - Generates reports

## API Documentation

### Control Products API

#### Endpoints

1. **Add Product**
   ```
   POST /control-products/{productId}
   Query Parameters:
   - placeId: Long (required)
   Response: GoodLocalDto
   ```

2. **Remove Product**
   ```
   DELETE /control-products/{productId}
   Response: 204 No Content
   ```

3. **Check Product Expiration**
   ```
   DELETE /control-products/beep
   Request Body: GoodLocal
   Response: Boolean
   ```

### Shopping List API

*Coming soon*

### Stats API

*Coming soon*

## Getting Started

### Prerequisites

- Java 11 or higher
- Maven
- Docker & Docker Compose
- PostgreSQL 14 (if running locally)

### Running the Application

1. Clone the repository:
   ```bash
   git clone https://github.com/Dandria1802/warehouse-management.git
   cd warehouse-management
   ```

2. Build the project:
   ```bash
   mvn clean install
   ```

3. Start the application using Docker Compose:
   ```bash
   docker-compose up -d
   ```

The application will be available at:
- Control Products API: http://localhost:9090
- Shopping List API: http://localhost:8080

## Development

### Code Quality

The project enforces code quality standards:
- Minimum 20% line coverage
- Minimum 20% branch coverage
- Minimum 20% complexity coverage
- Maximum 1 missed class

### Building

```bash
mvn clean install
```

### Testing

```bash
mvn test
```

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.
