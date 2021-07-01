# Google Work Sample Submission

My submission for the Google Youtube Challenge using Java 11, Junit 5.4 and Maven.

NOTE: **This project was completed in Java in about a day, so only code inside of the [Java](java) folder will work.**

The instructions given to me for this challenge will also be visible in the Java folder.



## Running and Testing from the Commandline
To build:
```shell script
mvn compile
```

To build & run:
```shell script
mvn exec:java
```
You can close the app by typing `EXIT` as a command.

#### Running all the tests
To run all the tests use the below code. You will have to compile your code before running 
the tests.
```shell script
mvn compile
```
```shell script
mvn test
```
If you haven't changed anything in the code, all tests should pass:

To run tests for a single Part:
```shell script
mvn test -Dtest=Part1Test
mvn test -Dtest=Part2Test
mvn test -Dtest=Part3Test
mvn test -Dtest=Part4Test
```
