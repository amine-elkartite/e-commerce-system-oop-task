# E-Commerce Checkout System

**Fawry Rise Journey Challenge | Java SE · Object-Oriented Programming · Service-Oriented Design**

A console-based e-commerce application built with **Java** to demonstrate practical object-oriented programming, separation of concerns, business-rule validation, and a structured checkout workflow.

The application manages an in-memory product catalog, supports shopping-cart operations, calculates shipping based on package weight, and processes purchases against a customer's available balance. It also introduces interface-based service contracts and domain-specific exception handling.

> **Project scope:** This is a Java SE learning and challenge project. It is **not** a Spring Boot application, a REST API, or a database-backed production storefront.

## Contents

- [Key Features](#key-features)
- [Technology Stack](#technology-stack)
- [Architecture](#architecture)
- [How Checkout Works](#how-checkout-works)
- [Sample Product Catalog](#sample-product-catalog)
- [Getting Started](#getting-started)
- [Error Handling](#error-handling)
- [Design Principles](#design-principles)
- [Current Limitations](#current-limitations)
- [Potential Enhancements](#potential-enhancements)
- [Acknowledgment](#acknowledgment)

## Key Features

### Product and Inventory Management

- Display the products available in the catalog, including price, stock, and shipping/expiration attributes.
- Verify that requested quantities are available before adding products to the cart.
- Reject expired products using their configured expiration dates.
- Reduce inventory quantities following a successful checkout.

### Shopping Cart

- Add products and specified quantities to an individual customer's cart.
- Reject duplicate products and insufficient-stock requests.
- Calculate the cart subtotal using product prices and quantities.
- Calculate the combined weight of items eligible for shipping.

### Shipping and Checkout

- Distinguish physical, shippable products from non-shippable items.
- Produce a shipment summary with item quantities and weights.
- Calculate shipping fees from the total shippable weight.
- Verify cart ownership, non-empty cart contents, and sufficient customer funds.
- Print an itemized checkout summary with subtotal, shipping, final amount, and remaining balance.

## Technology Stack

| Area | Technology / Approach |
| --- | --- |
| Language | Java 17+ |
| Application type | Command-line interface (CLI) |
| Programming model | Object-oriented Java |
| Collections | Java Collections Framework |
| Data processing | Stream API and `Optional` |
| Application structure | Entities, service interfaces, service implementations, utilities |
| Error handling | Custom runtime exceptions and `java.util.logging` |
| Data storage | In-memory sample catalog; no external database |
| Dependencies | Java standard library only |

## Architecture

The project separates domain objects, business operations, console interactions, and exception handling into dedicated packages.

```text
src/
├── Controller.java                    # CLI entry point and user menu
├── entity/
│   ├── Cart.java                      # Customer cart and selected products
│   ├── Customer.java                  # Customer details and balance
│   └── Product.java                   # Product properties and inventory
├── service/
│   ├── CartService.java
│   ├── CheckoutService.java
│   ├── CustomerService.java
│   ├── ProductService.java
│   ├── ShippingService.java
│   └── impl/
│       ├── CartServiceImpl.java
│       ├── CheckoutServiceImpl.java
│       ├── CustomerServiceImpl.java
│       └── ProductServiceImpl.java
├── exception/
│   ├── CartEmptyException.java
│   ├── CustomerNotMatchException.java
│   ├── DuplicateProductException.java
│   ├── InsufficientBalanceException.java
│   ├── InsufficientQuantityException.java
│   ├── ProductExpiredException.java
│   ├── ProductNotFoundException.java
│   └── handler/
│       └── GlobalExceptionHandler.java
└── util/
    ├── InputUtil.java                # Console menu and user input
    └── ProductInitializer.java       # Predefined in-memory products
```

**Responsibility boundaries:**

- **Entities** represent products, customers, and carts.
- **Service interfaces** define contracts for business operations.
- **Service implementations** contain inventory, cart, customer, and checkout logic.
- **Utilities** handle user interaction and sample-data initialization.
- **Custom exceptions** express domain-specific failures; the exception handler logs them centrally.

## How Checkout Works

1. **Initialize:** Load the sample catalog and create a customer and shopping cart.
2. **Select products:** The user views inventory and adds items through the console menu.
3. **Validate items:** The cart service checks product existence, stock availability, expiration, and duplicates.
4. **Prepare the order:** The checkout service verifies that the cart belongs to the customer and is not empty.
5. **Calculate totals:** Compute the subtotal, identify shippable items, and determine shipping fees.
6. **Verify funds:** Reject the transaction when the customer balance cannot cover the total.
7. **Complete checkout:** Update inventory, deduct the purchase amount, and print the receipt.

**Shipping calculation**

```text
Shippable weight (kg) = Σ(item weight in grams × quantity) / 1000
Shipping fee          = round(shippable weight in kg × 30)
Order total           = cart subtotal + shipping fee
Remaining balance     = customer balance − order total
```

The current `Controller` calls checkout **when the user selects Exit**. A dedicated checkout command is a recommended future improvement.

## Sample Product Catalog

The application ships with the following seeded test data:

| Product | Unit price | Initial stock | Expires | Requires shipping | Unit weight |
| --- | ---: | ---: | :---: | :---: | ---: |
| Cheese | 100.00 | 8 | Yes | Yes | 400 g |
| TV | 350.00 | 7 | No | Yes | 1,250 g |
| Biscuits | 75.00 | 6 | Yes | Yes | 200 g |
| ScratchCard | 50.00 | 5 | No | No | — |

> **Important:** The sample expiration date for Cheese and Biscuits is **October 1, 2026**. These products are considered expired after that date unless `ProductInitializer.java` is updated. Prices are sample values; no currency is defined in the project.

## Getting Started

### Prerequisites

- JDK **17 or newer**
- A terminal, or a Java IDE such as IntelliJ IDEA, Eclipse, or VS Code

No database, framework, or third-party library is required.

### Compile and Run (macOS / Linux)

From the project root directory:

```bash
mkdir -p out
javac -d out $(find src -name "*.java")
java -cp out Controller
```

### Run from an IDE

1. Open the project folder as a Java project.
2. Configure a JDK 17+ project SDK.
3. Run the `main` method in `src/Controller.java`.

### Console Menu

```text
MAIN MENU
1. View Inventory
2. Add Item to Cart
3. View Cart
4. Exit
```

On exit, the application attempts to check out the current cart, then prints a receipt or logs an error if validation fails.

## Error Handling

The project defines domain-specific runtime exceptions to make business failures explicit:

| Exception | Scenario |
| --- | --- |
| `ProductNotFoundException` | A requested product is not found |
| `InsufficientQuantityException` | Available inventory is below the requested quantity |
| `ProductExpiredException` | An expired item is selected |
| `DuplicateProductException` | An item already exists in the cart |
| `CartEmptyException` | Checkout or cart viewing is attempted with no items |
| `InsufficientBalanceException` | The customer cannot cover the final charge |
| `CustomerNotMatchException` | Cart ownership does not match the customer |

`GlobalExceptionHandler` routes these exceptions to Java's built-in logging API using appropriate log levels. The handler operates within the CLI application; it is not an HTTP exception handler.

## Design Principles

The implementation **demonstrates selected SOLID practices**, rather than claiming complete SOLID compliance:

- **Single Responsibility Principle (SRP):** Product, customer, cart, and service responsibilities are split among focused classes.
- **Interface Segregation Principle (ISP):** Operations are defined through relatively small service contracts, allowing consumers to depend on capabilities relevant to them.
- **Dependency Inversion Principle (DIP):** Cart and checkout implementations receive key service collaborators through interfaces and constructor parameters. Some concrete dependencies are still instantiated internally and could be further decoupled.

The project also uses `Stream` operations for calculations and filtering, `Optional` for catalog lookups, and dedicated exception types for clearer control flow.

## Current Limitations

This challenge implementation is deliberately lightweight. Important constraints include:

- The catalog and customer state exist **in memory** and reset between runs.
- There is **no REST API, graphical storefront, payment gateway, or external shipping integration**.
- Checkout is initiated on **Exit**, not via a separate cart action.
- Some edge cases, including unknown product input and invalid/non-positive quantities, need stronger validation.
- The project does not currently include automated tests or a Maven/Gradle build configuration.
- Monetary values use `double`; production financial calculations should generally use `BigDecimal`.

## Potential Enhancements

1. **Testing:** Introduce JUnit 5, Mockito, and integration tests for cart validation and checkout behavior.
2. **Input validation:** Reject null products and non-positive quantities with clear user-facing messages.
3. **Checkout workflow:** Add an explicit checkout command and stronger transaction-like safeguards.
4. **Build tooling:** Add Maven or Gradle for reproducible builds and automated quality checks.
5. **Persistence:** Replace in-memory data with a relational database and repository layer.
6. **Financial accuracy:** Replace floating-point monetary values with `BigDecimal`.
7. **API evolution:** Optionally expose the business logic through a Spring Boot REST API.
8. **Delivery pipeline:** Add continuous integration, automated tests, and static analysis.

## Acknowledgment

This project was developed as part of the **Fawry Rise Journey Challenge**. Appreciation goes to **Fawry** for providing a practical opportunity to apply core Java and object-oriented programming concepts to a realistic e-commerce scenario.

---

*Built as a hands-on exercise in Java, clean separation of responsibilities, and business-rule-driven application design.*
