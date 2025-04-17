# Crypto Wallet Manager

## How to run

### Dependencies

From project root directory run:
```shell
docker-compose up -d
```

### Environment
CoinCap API key needs to be set as `COIN_CAP_API_KEY` environment variable or directly in the `application.yml`

### Application
From the project root directory run:
```shell
mvn clean install
```
```shell
mvn spring-boot:run
```

### Usage
Application API available in [Swagger](http://localhost:8080/swagger-ui/index.html)

