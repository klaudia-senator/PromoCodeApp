# PromoCode Application

## Description

PromoCode Application is an application for managing promotional codes and products. It allows for creating promo codes, products, simulating purchases, and calculating prices with discounts. It uses the Spring Framework and the H2 database.

## How it Works

1. **Products**: Add and manage products with name, description, and price.
2. **Promo Codes**: Create unique promo codes with expiration date, discount, currency, and usage limit.
3. **Purchase Simulation**: Calculate discounted prices based on promo codes and products.
4. **Purchases**: Store transaction information, including regular price, discount, and purchase date.

## How to Run

1. **Clone the repository**:
    ```bash
    git clone REPOSITORY_URL](https://github.com/klaudia-senator/PromoCodeApp.git
    ```
2. **Navigate to the project directory**:
    ```bash
    cd promocode-app
    ```
3. **Build the application**:
    ```bash
    mvn clean install
    ```
4. **Run the application**:
    ```bash
    mvn spring-boot:run
    ```

The application will be available at `http://localhost:9090`.

## API Endpoints
- **1.Add a product**:
    ```http
    POST /products
    Content-Type: application/json
    {
        "name": "Product Name",
        "description": "Product Description",
        "price": 100.00,
        "currency": "USD"
    }
  ```
- **2.List all products**:
    ```http
    GET /products
  ```
- **3.Update a product**:
    ```http
    PUT /products/{id}
    Content-Type: application/json
    {
        "name": "Updated Product Name",
        "description": "Updated Product Description",
        "price": 150.00,
        "currency": "USD"
    }
  ```
- **4.Add a promo code**:
    ```http
    POST /promocodes
    Content-Type: application/json

    {
        "code": "PROMO123",
        "expirationDate": "2023-12-31",
        "discount": 10.00,
        "currency": "USD",
        "maxUsage": 100
    }
    ```
- **5.List all promo codes**:
    ```http
    GET /promocodes
  ```
- **6.Get promo code details**:
    ```http
    GET /promocodes/{code}
    ```
- **7.Calculate discounted price**:
    ```http
    GET /promocodes/discount?price={price}&code={code}
    ```
- **8.Simulate a purchase**:
    ```http
    POST /purchases/simulate
    Content-Type: application/json
    Query params:
    {
        "productId": 1,
        "promoCode": "PROMO123"
    }
    ```
## Author

Klaudia Senator
