### DevelopmentBookStore

This short and simple Kata should be performed using Test Driven Development (TDD).


## Prerequisites
* Java 8 or higher
* Spring Boot Tools
* Maven

### Problem Description:

There is a series of books about software development that have been read by a lot of developers who want to improve their development skills. Let’s say an editor, in a gesture of immense generosity to mankind (and to increase sales as well), is willing to set up a pricing model where you can get discounts when you buy these books. The available books are :

Clean Code (Robert Martin, 2008) The Clean Coder (Robert Martin, 2011) Clean Architecture (Robert Martin, 2017) Test Driven Development by Example (Kent Beck, 2003) Working Effectively With Legacy Code (Michael C. Feathers, 2004) Rules The rules are described below :


### Rules:
One copy of the five books costs 50 EUR.

If, however, you buy two different books from the series, you get a 5% discount on those two books. If you buy 3 different books, you get a 10% discount. With 4 different books, you get a 20% discount. If you go for the whole hog, and buy all 5, you get a huge 25% discount. Note that if you buy, say, 4 books, of which 3 are different titles, you get a 10% discount on the 3 that form part of a set, but the 4th book still costs 50 EUR. Developers seeking to deliver quality products are queueing up with shopping baskets overflowing with these books. Your mission is to write a piece of code to calculate the price of any conceivable shopping basket.

For example, how much does this basket of books cost?

2 copies of the “Clean Code” book 2 copies of the “Clean Coder” book 2 copies of the “Clean Architecture” book 1 copy of the “Test Driven Development by Example” book 1 copy of the “Working effectively with Legacy Code” book Answer :

(4 * 50 EUR) - 20% [first book, second book, third book, fourth book]

(4 * 50 EUR) - 20% [first book, second book, third book, fifth book]

= 160 EUR + 160 EUR

= 320 EUR.


## Test Scenarios

**Test Get All Books Endpoint**

   Description: Test the API endpoint `/getAllBooks` to retrieve all books from the bookstore.
   
**Test Steps:**

* Send a GET request to `/getAllBooks`.
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
   
   *  Send a POST request to `/calculateTotalPrice` with an empty JSON array as the request body.
   
   *  Verify that the response status is `400 Bad Request`.
   
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

## To execute the project, We can following the below steps:

1. Ensure that you have all the necessary dependencies and configurations set up in your Spring Boot application.

2. Make sure you have the required dependencies in your pom.xml file. You should have dependencies for Spring Web MVC, Spring Boot Starter Test, and any other dependencies required for your project.

3. Create the necessary classes for your application, including the controller, service, model, and exception classes.

4. Implement the business logic in the service class and handle exceptions accordingly.

5. Configure your application properties or application.yaml file with the necessary settings.

6. Create unit tests for your controller and service classes to ensure that they are functioning correctly.

7. Run the application by executing the main() method in the main class of your Spring Boot application.

8. Use tools like Postman to send requests to the defined endpoints and verify the responses.

9. Use the provided test cases to validate the functionality of your application. You can run the tests using a testing framework like JUnit.

10. Review the test results and ensure that all tests pass successfully.

11. Make any necessary adjustments or improvements based on your requirements and retest as needed.