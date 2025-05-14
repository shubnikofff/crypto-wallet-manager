# Crypto Wallet Manager

## Task description
You need to create a **program** that helps people keep track of their **“crypto money”** (also known as token), like BTC or ETH, in a special **wallet**. 
This wallet stores information about different types of cryptocurrencies, how much of each person owns, and how much it costs right now.

### What the Program Should Do:
1. **Get the Latest Prices**:
   - You will need to keep the user tokens prices updated, and for that you must **get the latest price** of each token using the **pricing API (CoinCap)**. This API has endpoints to tell the token price.
   - Do it recurrently, but not too often, and this time must be configurable.
   - Check the prices at the same time for 3 tokens at once, using threads to be as quick as possible, but respecting the limit of 3 threads maximum.
2. **Save the Information**:
   - When you get the new price for each crypto, save that information into a **database**
   - The database will save the information about which crypto someone has, the quantity they have and its price.
3. **Create new wallet**:
   - By sending a request with his email, which should be unique, the user must be able to create an empty wallet and receive its saved data. If there is already a wallet created with this e-mail let the user know.
4. **Add asset to the wallet**:
   - The user must be able to add an assets (token) to the wallet, by passing symbol, price and quantity. The application should check on the **pricing API** that token’s price and not allowing it to be saved if the price is not found.
5. **Show Wallet Information**:
   - The program should have an **API** that shows the wallet information, with all tokens (symbol, price, quantity, value) and the **total**, which is **the wallet total value in USD**:
   ```json5
   {
      id: "123",
      total: 158000.00,
      assets: [
         {
            symbol: "BTC",
            quantity: 1.5,
            price: 100000.00,
            value: 150000.00
         },
         {
            symbol: "ETH",
            quantity: 2,
            price: 4000,
            value: 8000
         }
      ]
   }
   ```

### Wallet evaluation:
- The system must have a functionality for the user to evaluate a wallet evolution today or in a specific
date in the past
- **Input and Output**:
  - **Input**: You will receive the list of tokens containing symbol, quantity and its value. Like in the
  example below:
   ```json5
   {
      assets: [
         {
            symbol: "BTC",
            quantity: 0.5,
            value: 35000
         },
         {
            symbol: "ETH",
            quantity: 4.25,
            value: 15310.71
         }
      ]
   }
   ```
  The example above means this wallet have **0.5 BTC** (Bitcoin) with **a value of 35000.008 USD (meaning 1 BTC
  costs 70000)** and **4.25 ETH** (Ethereum) with **15310.71 USD value (a price of 3602.52 per ETH)**. The application
  must calculate the appreciation or depreciation of each token, specifying the best and worst assets and their performance.
  For this example, comparing with 07/01/2025, BTC is up 35.3561% and ETH is up 2.70575%, so that wallet
  would worth around 63097.33 and show the total this worth in **USD**.
   
   - **Output**: After checking the new prices from CoinCap, you should send back a result that looks something like this:
    ```json5
   {
      total: 63097.33,
      best_asset: "BTC",
      best_performance: 35.35,
      worst_asset: "ETH",
      worst_performance: 2.7
   }
   ```
  This tells the person:
    - **total**: How much all this wallet would be worth in USD on that given time.
    - **best_asset**: Which crypto made the most money.
    - **best_performance**: How much that crypto went up in price (as a percentage).
    - **worst_asset**: Which crypto lost the most money.
    - **worst_performance**: How much that crypto went down in price (as a percentage).





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

