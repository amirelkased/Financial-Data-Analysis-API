# Financial Data Analysis API
This project features an API for financial data analysis, focusing on market indicator analysis and prediction. Built with Spring Boot, it adheres to the MVC (Model-View-Controller) pattern, offering endpoints for retrieving financial data, conducting analyses, and predicting based on historical data.

# Technologies Used
- **Spring Boot**: Framework for Java-based enterprise applications.
- **Spring MVC**: Module for implementing the web layer.
- **Spring Data JPA**: Simplifies database interaction.
- **MySQL**: The relational database management system used for data storage.
- **Machine Learning Model**: Integrated for stock predictions.

# Endpoints

#### API Overview
**Title:** OpenAPI definition  
**Version:** v0  
**Base URL:** `http://localhost:8088/api/v1`

#### Endpoints

##### 1. Get Stock Details
- **Endpoint:** `/stocks/votes/{id}`
- **Method:** `GET`
- **Description:** Retrieve stock details.
- **Parameters:**
  - `id` (path, required, integer): ID of the stock.
- **Responses:**
  - `200 OK`: Returns the stock vote response model.

##### 2. Vote Stock
- **Endpoint:** `/stocks/votes/{id}`
- **Method:** `POST`
- **Description:** Vote for a stock.
- **Parameters:**
  - `id` (path, required, integer): ID of the stock.
  - `value` (query, required, integer): Vote value.
- **Responses:**
  - `200 OK`: Acknowledges the vote.

##### 3. Get Stock Prediction
- **Endpoint:** `/stocks/predictions`
- **Method:** `POST`
- **Description:** Get stock prediction.
- **Request Body:** `StockPredictionBody` (required)
- **Responses:**
  - `200 OK`: Returns an array of stock prediction response models.

##### 4. Register User
- **Endpoint:** `/auth/register`
- **Method:** `POST`
- **Description:** Register a new user.
- **Request Body:** `RegistrationRequest` (required)
- **Responses:**
  - `200 OK`: User registered successfully.

##### 5. Logout
- **Endpoint:** `/auth/logout`
- **Method:** `POST`
- **Description:** Logout the user.
- **Parameters:**
  - `Authorization` (header, required, string): Authorization token.
- **Responses:**
  - `200 OK`: User logged out successfully.

##### 6. Authenticate User
- **Endpoint:** `/auth/authenticate`
- **Method:** `POST`
- **Description:** Authenticate a user.
- **Request Body:** `AuthenticationRequest` (required)
- **Responses:**
  - `200 OK`: Returns authentication response.

##### 7. Get All Stocks
- **Endpoint:** `/admin/stocks`
- **Method:** `GET`
- **Description:** Retrieve all stocks.
- **Responses:**
  - `200 OK`: Returns an array of stock DTOs.

##### 8. Save Stocks
- **Endpoint:** `/admin/stocks`
- **Method:** `POST`
- **Description:** Save stocks.
- **Request Body:** Array of strings (required)
- **Responses:**
  - `200 OK`: Returns an array of stock DTOs.

##### 9. Delete Stock
- **Endpoint:** `/admin/stocks/{id}`
- **Method:** `DELETE`
- **Description:** Delete a stock by ID.
- **Parameters:**
  - `id` (path, required, integer): ID of the stock.
- **Responses:**
  - `200 OK`: Stock deleted successfully.

##### 10. Get Market Indications
- **Endpoint:** `/market-indication/`
- **Method:** `GET`
- **Description:** Retrieve market indications.
- **Responses:**
  - `200 OK`: Returns market indication response data.

##### 11. Confirm Account Activation
- **Endpoint:** `/auth/activate-account`
- **Method:** `GET`
- **Description:** Confirm account activation.
- **Parameters:**
  - `token` (query, required, string): Activation token.
- **Responses:**
  - `200 OK`: Account activated successfully.

### Components
#### Schemas
- **StockPredictionBody:**
  - `stockId` (integer): ID of the stock.
  - `noOfDay` (integer): Number of days for prediction.

- **StockPredictionResponseModel:**
  - `date` (string): Date of prediction.
  - `opening_prediction` (integer): Opening price prediction.
  - `closing_prediction` (integer): Closing price prediction.

- **RegistrationRequest:**
  - `firstname` (string): User's first name.
  - `lastname` (string): User's last name.
  - `email` (string): User's email.
  - `password` (string): User's password (8-255 characters).

- **AuthenticationRequest:**
  - `email` (string): User's email.
  - `password` (string): User's password (8-25 characters).

- **AuthenticationResponse:**
  - `token` (string): Authentication token.

- **StockDto:**
  - `id` (integer): Stock ID.
  - `name` (string): Stock name.

- **StockVoteResponseModel:**
  - `name` (string): Stock name.
  - `open` (integer): Opening price.
  - `close` (integer): Closing price.
  - `votes` (integer): Number of votes.

- **RankItem:**
  - `name` (string): Item name.
  - `rank` (integer): Item rank.

- **ResponseData:**
  - `profits` (array): Array of rank items representing profits.
  - `losses` (array): Array of rank items representing losses.

# Getting Started
1. Clone the repository:
   
   ```bash
   git clone https://github.com/amirelkased/Financial-Data-Analysis-API.git
   cd Financial-Data-Analysis-API
   ```

2. Set up the database:
   - Ensure MySQL is installed and running.
   - Create a new database.
   - Update `application.properties` with your database configuration.

3. Build and run the application:
   
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

4. Access the API:
   - The API will be available at `http://localhost:8088`.

# Contributing
Contributions are welcome! Please open an issue or submit a pull request for enhancements or bug fixes.

# License
This project is licensed under the MIT License.
