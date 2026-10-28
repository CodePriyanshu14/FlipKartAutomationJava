Feature: Flipkart Product Search and Cart

  @smoke
  @regression
  Scenario Outline: Search for a product
    Given I load test data for "<TestCaseId>"
    And I am on the Flipkart home page
    When I search for the configured product
    Then search results should be displayed

    Examples:
      | TestCaseId    |
      | TC_Search_001 |
      | TC_Search_002 |
      | TC_Search_003 |


  @regression
  Scenario Outline: Search and filter products
    Given I load test data for "<TestCaseId>"
    And I am on the Flipkart home page
    When I search for the configured product
    And I apply the configured product filters
    Then search results should be displayed

    Examples:
      | TestCaseId    |
      | TC_Filter_001 |
      | TC_Filter_002 |

  @regression
Scenario Outline: Proceed from cart to checkout
    Given I load test data for "<TestCaseId>"
    And I am on the Flipkart home page
    When I login with valid credentials
    And I search for the configured product
    And I apply the configured product filters
    And I open the configured product
    And I add the product to cart
    And I open the cart
    Then the cart should be displayed
    When I place the order
    Then the checkout page should be displayed
    When I continue from checkout
    Then the payment page should be displayed

    Examples:
      | TestCaseId  |
      | TC_Cart_001 |
      | TC_Cart_002 |      

 @regression
Scenario Outline: Complete purchase journey up to payment
    Given I load test data for "<TestCaseId>"
    And I am on the Flipkart home page
    When I login with valid credentials
    And I search for the configured product
    And I apply the configured product filters
    And I open the configured product
    And I add the product to cart
    And I open the cart
    Then the cart should be displayed
    When I place the order
    Then the checkout page should be displayed
    When I continue from checkout
    Then the payment page should be displayed
    When I select the configured payment method
    Then the selected payment method should be displayed

    Examples:
      | TestCaseId  |
      | TC_Cart_001 |