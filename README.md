### DevelopmentBookStore

The DevelopmentBookStore is a software application for managing and selling books related to software development.

## Prerequisites
* Java 8 or higher
* Spring Boot Tools
* Maven

### Problem Description:

There is a series of books about software development that have been read by a lot of developers who want to improve their development skills. Let’s say an editor, in a gesture of immense generosity to mankind (and to increase sales as well), is willing to set up a pricing model where you can get discounts when you buy these books. 

*The available books are :*
 - Clean Code (Robert Martin, 2008)
 - The Clean Coder (Robert Martin, 2011)
 - Clean Architecture (Robert Martin, 2017)
 - Test Driven Development by Example (Kent Beck, 2003) 
 - Working Effectively With Legacy Code (Michael C. Feathers, 2004).


### Rules:
One copy of the five books costs 50 EUR.

If, however, you buy two different books from the series, you get a 5% discount on those two books. If you buy 3 different books, you get a 10% discount. With 4 different books, you get a 20% discount. If you go for the whole hog, and buy all 5, you get a huge 25% discount. Note that if you buy, say, 4 books, of which 3 are different titles, you get a 10% discount on the 3 that form part of a set, but the 4th book still costs 50 EUR. Developers seeking to deliver quality products are queueing up with shopping baskets overflowing with these books. Your mission is to write a piece of code to calculate the price of any conceivable shopping basket.

For example, how much does this basket of books cost?

2 copies of the “Clean Code” book 2 copies of the “Clean Coder” book 2 copies of the “Clean Architecture” book 1 copy of the “Test Driven Development by Example” book 1 copy of the “Working effectively with Legacy Code” book Answer :

(4 * 50 EUR) - 20% [first book, second book, third book, fourth book]

(4 * 50 EUR) - 20% [first book, second book, third book, fifth book]

= 160 EUR + 160 EUR

= 320 EUR.

## To facilitate the execution of the project, one can adhere to the subsequent steps:

1. **Create a New Spring Boot Project:**


  * Use Spring Initializer to create a new Spring Boot project.
  * Choose the required dependencies: Spring Web, Spring Boot DevTools, Lombok, and H2 Database (for testing).

2. We have to add required dependencies in pom.xml file. Have dependencies like Spring Web MVC, Spring Boot Starter Test, and any other dependencies required for bookstore project.

3. Create the necessary classes for bookstore application, including the controller, service, model, and exception classes.

4. Implement the business logic in the service class and handle exceptions accordingly.

5. Write unit tests for the BookService and BookController classes using JUnit and Mockito.

6. **Enable Swagger Documentation:**

 * Add the necessary dependencies for Swagger in your pom.xml file.
 * Configure Swagger in your application using @EnableSwagger2 annotation.
 * Open a web browser and navigate to 'http://localhost:8080/swagger-ui/index.html' .Swagger UI will display the list of available APIs.

7. **Run the Application:**

  * Run the Spring Boot application using your IDE or in command line using this command **'mvn spring-boot:run'**.
  * Check the console for any errors or warnings during startup.


## Test Scenarios

**Test Get All Books Endpoint**

   Description: Test the API endpoint `/books` to retrieve all books from the bookstore.
   
**Test Steps:**

* Send a GET request to `/development-bookstore/v1/books`.
* Verify the response status is `200 OK`.
* Verify the response content type is `application/json`.
* Verify the response body contains an array of book names.

 **Test Calculate Total Price**

   Description: Test the calculation of the total price for a list of selected books.

**Test Steps:**
   
   * Prepare a list of selected books.
   
   * Mock the `bookService.calculateTotalPrice()` method to throw a `BookValidationException`.
   
   * Assert that invoking `bookService.calculateTotalPrice()` with the list of selected books throws a `BookValidationException`.

**Test Empty Cart Exception**
  
   Description: Test the scenario when the cart is empty and the `calculateTotalPrice()` method throws an `EmptyCartException`.

**Test Steps:**

   *  Mock the `bookService.calculateTotalPrice()` method to throw an `EmptyCartException`.
   
   *  Send a POST request to `/development-bookstore/v1/calculate-price` with an empty JSON array as the request body.
   
   * Verify that the response body contains the error message "The cart is empty".
   


**Test Price Calculation with Discounts**

   Description: Test the price calculation for different scenarios involving various book combinations and discounts.

**Test Cases:**

   - All different books with no discount.
   - All same books with no discount.
   - Two different books with a 5% discount.
   - Three different books with a 10% discount.
   - Four different books with a 20% discount.
   - Four different books with a 10% discount.
   - Shopping basket with multiple books.
   - Invalid year in the book's release date.

**Test Steps:**
   - Prepare the list of selected books based on each scenario.
   - Mock the `bookService.calculateTotalPrice()` method to return the expected total price.
   - Assert that invoking `bookService.calculateTotalPrice()` with the list of selected books returns the expected total price or throws a `BookValidationException` for an invalid year.

## Running the Tests

To run the test cases, execute the following command:

        mvn test

