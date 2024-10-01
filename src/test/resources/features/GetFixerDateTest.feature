Feature: Test GET fixer/date endpoint
  Scenario Outline: Execute GET with different data
    Given The fixer date endpoint is set to "<endpoint>"
    And The apiKey is set to "<apiKey>"
    And The date "<date>" in past is set
    And I'm setting my base currency as "<baseCurrency>"
    And I want to see the following symbols "<symbols>"
    And The request body is "<body>"
    When I prepare request specification
    And I execute get request "<iterations>" time
    Then I expect the <code> response code
    And I expect the correct message for response code <code>

  Examples:
    | date       | baseCurrency | symbols | endpoint | apiKey                           | code | body | iterations |
    | 1998-12-31 | EUR          | USD,GBP | /fixer/  | bCYL147Cp5BcYZ2H4ge5DA0l9VUhFMaq | 200  |      | one        |
    | 1999-01-01 | EUR          | USD,GBP | /fixer/  | bCYL147Cp5BcYZ2H4ge5DA0l9VUhFMaq | 200  |      | one        |
    | 2025-01-30 | EUR          | USD,GBP | /fixer/  | bCYL147Cp5BcYZ2H4ge5DA0l9VUhFMaq | 200  |      | one        |
    |            | EUR          | USD,GBP | /fixer/  | bCYL147Cp5BcYZ2H4ge5DA0l9VUhFMaq | 404  |      | one        |
    | 1999-01-01 | nonExisting  | USD,GBP | /fixer/  | bCYL147Cp5BcYZ2H4ge5DA0l9VUhFMaq | 200  |      | one        |
    | 1999-01-01 |              | USD,GBP | /fixer/  | bCYL147Cp5BcYZ2H4ge5DA0l9VUhFMaq | 200  |      | one        |
    | 1999-01-01 | EUR          | nonExist| /fixer/  | bCYL147Cp5BcYZ2H4ge5DA0l9VUhFMaq | 200  |      | one        |
    | 1999-01-01 | EUR          |         | /fixer/  | bCYL147Cp5BcYZ2H4ge5DA0l9VUhFMaq | 200  |      | one        |
    | 1999-01-01 | EUR          | USD,GBP |          | bCYL147Cp5BcYZ2H4ge5DA0l9VUhFMaq | 404  |      | one        |
    | 1999-01-01 | EUR          | USD,GBP | /fake/   | bCYL147Cp5BcYZ2H4ge5DA0l9VUhFMaq | 404  |      | one        |
    | 1999-01-01 | EUR          | USD,GBP | /fixer/  |                                  | 401  |      | one        |
    | 1999-01-01 | EUR          | USD,GBP | /fixer/  | fake                             | 401  |      | one        |
    | 1999-01-01 | EUR          | USD,GBP | /fixer/  | bCYL147Cp5BcYZ2H4ge5DA0l9VUhFMaq | 400  | fake | one        |
#    | 2018-09-09 | EUR          | USD,GBP | /fixer/  | bCYL147Cp5BcYZ2H4ge5DA0l9VUhFMaq | 429  |      | max        |
#    |            |              |         | exchangerates_data/symbols | bCYL147Cp5BcYZ2H4ge5DA0l9VUhFMaq | 403  |      | one |
