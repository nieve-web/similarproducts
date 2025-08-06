# Similar Products Service
Microservice that provides similar product information following Hexagonal Architecture principles and **fully compliant** with the API contract specified in `backendDevTest/similarProducts.yaml`.

## 📌 Features

- **Hexagonal Architecture** implementation
- **Reactive Programming** with Spring WebFlux
- **Precise error handling** with custom exceptions
- **97% code coverage** with 24 unit tests

## 🚀 Quick Start

### Prerequisites
- Java 17+
- Docker (for running mocks)

### Configuration
- Set `server.port=5000` in application.properties (matches OpenAPI contract)

### Running
- `mvn spring-boot:run`  

## Architecture
- `src/main/java/com/nunegal/similarproducts/`
    - `domain/` - Core business models
    - `application/` - Use cases and ports
    - `driving/` - REST controllers
    - `driven/` - External API clients
- `src/test/` - 24 unit tests

## API Compliance
Implements the OpenAPI specification:
- Endpoint: `GET /product/{productId}/similar`
- Responses:
    - 200 - List of similar products
    - 404 - Product Not found  

## Key Configuration
- WebClient configured with base URL: `http://localhost:3001/product`  

## ✅ Test Results
Below is a sample of the test execution summary:

![Test Results](docs/test-results.png)
