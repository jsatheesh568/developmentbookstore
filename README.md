# developmentbookstore

This short and simple Kata should be performed using Test Driven Development (TDD).

There is a series of books about software development that have been read by a lot of developers who want to improve their development skills. Let’s say an editor, in a gesture of immense generosity to mankind (and to increase sales as well), is willing to set up a pricing model where you can get discounts when you buy these books. The available books are :

Clean Code (Robert Martin, 2008) The Clean Coder (Robert Martin, 2011) Clean Architecture (Robert Martin, 2017) Test Driven Development by Example (Kent Beck, 2003) Working Effectively With Legacy Code (Michael C. Feathers, 2004) Rules The rules are described below :

One copy of the five books costs 50 EUR.

If, however, you buy two different books from the series, you get a 5% discount on those two books. If you buy 3 different books, you get a 10% discount. With 4 different books, you get a 20% discount. If you go for the whole hog, and buy all 5, you get a huge 25% discount. Note that if you buy, say, 4 books, of which 3 are different titles, you get a 10% discount on the 3 that form part of a set, but the 4th book still costs 50 EUR. Developers seeking to deliver quality products are queueing up with shopping baskets overflowing with these books. Your mission is to write a piece of code to calculate the price of any conceivable shopping basket.

For example, how much does this basket of books cost?

2 copies of the “Clean Code” book 2 copies of the “Clean Coder” book 2 copies of the “Clean Architecture” book 1 copy of the “Test Driven Development by Example” book 1 copy of the “Working effectively with Legacy Code” book Answer :

(4 * 50 EUR) - 20% [first book, second book, third book, fourth book]

(4 * 50 EUR) - 20% [first book, second book, third book, fifth book]

= 160 EUR + 160 EUR

= 320 EUR.

To execute the project, you can follow these steps:

Ensure that you have all the necessary dependencies and configurations set up in your Spring Boot application.

Make sure you have the required dependencies in your pom.xml file. You should have dependencies for Spring Web MVC, Spring Boot Starter Test, and any other dependencies required for your project.

Create the necessary classes for your application, including the controller, service, model, and exception classes.

Implement the business logic in the service class and handle exceptions accordingly.

Configure your application properties or application.yaml file with the necessary settings.

Create unit tests for your controller and service classes to ensure that they are functioning correctly.

Run the application by executing the main() method in the main class of your Spring Boot application.

Use tools like Postman to send requests to the defined endpoints and verify the responses.

Use the provided test cases to validate the functionality of your application. You can run the tests using a testing framework like JUnit.

Review the test results and ensure that all tests pass successfully.

Make any necessary adjustments or improvements based on your requirements and retest as needed.