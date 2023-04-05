# hostaway-ta
Test Automation framework based on Selenide with Allure Report integration

#### Usage: ####
```
mvn test  
mvn allure:serve 
```
-----
### Tasks: ###

Please provide automation tests for
https://kamil-demo.alpinizm.uz/ 
using:
* Selenide (or Selenium), 
* Allure, 
* Java 11, 
* JUnit or TestNG (without Cucumber) 

which will do the following:
1. Check Filters form
    1. entry fields
    2. checkboxes
    3. minimum and maximum values
    4. Amenities checkboxes
    5. “Clear all” is functional
    6. check buttons without clicking "Apply"

    To reach the Filters form on https://kamil-demo.alpinizm.uz/ press the Search button, press the Filter button.
   
2. Check that the 'All listings' page (https://kamil-demo.alpinizm.uz/all-listings) has the same amount of listings as the 'All' label.
-----
#### In the result: ####
* [x] Allure report should be formed.
* [x] Submit the result on GitHub.
-----
#### We expect to see the usage of: ####
* [x] AAA (Arrange-Act-Assert): you should divide your test method into three sections: arrange, act and assert.
* [x] tests should be developed using method chaining (page object in fluent style).
* [x] POM (Page Object Model)
