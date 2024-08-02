# Financial Data Analysis API
This project features an API for financial data analysis, focusing on market indicator analysis and prediction. Built with Spring Boot, it adheres to the MVC (Model-View-Controller) pattern, offering endpoints for retrieving financial data, conducting analyses, and predicting based on historical data.

# Technologies Used
- **Spring Boot**: Framework for Java-based enterprise applications.
- **Spring MVC**: Module for implementing the web layer.
- **Spring Data JPA**: Simplifies database interaction.
- **MySQL**: The relational database management system used for data storage.
- **Machine Learning Model**: Integrated for stock predictions.

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
   - The API will be available at http://localhost:8080.

# Contributing
Contributions are welcome! Please open an issue or submit a pull request for enhancements or bug fixes.

# License
This project is licensed under the MIT License.
