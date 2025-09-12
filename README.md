# Checkout Service 3.0

A Spring Boot service implementing a market checkout system with support for scanned items, special pricing, and bundle discounts. The service exposes a REST API, has no UI.
The application instead of modular monolith approach uses a layered architecture as it is a rather small service. 
### Table of Contents:

Domain Objects

Pricing Rules

API Endpoints

Running the Service

### Domain Objects
####  1. Checkout

Represents a customer's current checkout session.

| Field          | Type                 | Description                                           |
| -------------- | -------------------- | ----------------------------------------------------- |
| id             | String               | Unique identifier for the checkout session            |
| items          | Map\<String,Integer> | Maps product ID → quantity scanned                    |
| active         | boolean              | true if checkout is active, false if finalized        |
| finalPrice     | BigDecimal           | Total price after special prices and bundle discounts |
| bundleDiscount | BigDecimal           | Total discount applied due to bundle offers           |

####  2. Product

Represents an item in the catalog.

| Field            | Type       | Description                                      |
| ---------------- | ---------- | ------------------------------------------------ |
| id               | String     | Product identifier                               |
| name             | String     | Human-readable name                              |
| normalPrice      | BigDecimal | Price per unit                                   |
| requiredQuantity | Integer    | Minimum quantity for special price (optional)    |
| specialPrice     | BigDecimal | Price for a group of requiredQuantity (optional) |

####  3. ItemDto

Data transfer object for scanning an item.

| Field    | Type    | Description                 |
| -------- | ------- | --------------------------- |
| id       | String  | Product ID to scan          |
| quantity | Integer | Quantity to add (default 1) |

####  4. BundleOffer

Represents a discount applied when two items are bought together.


| Field  | Type | Description |
| ------------- | ------------- | ------------- |
| firstItem  | String  | ID of the first product (must be "lower" than second) |
| secondItem  | String | ID of the second product |
| discount  | BigDecimal  | Discount applied per bundle |

### Pricing Rules

Normal Price: Each product has a per-unit price.

Special Price: Certain products have discounted prices when buying a specific quantity.

Example: Product A costs 40 per unit, but 3 units cost 30 each → 4 units = 3×30 + 40 = 130.

Bundle Discounts: Certain combinations of products grant additional discounts.

Example: Buying A + B together may save 5 per bundle.

### API Endpoints
#### 1. Create Checkout
   POST /checkouts
   Content-Type: application/json


Response:

{
"id": "123e4567-e89b-12d3-a456-426614174000",
"items": {},
"active": true,
"finalPrice": 0,
"bundleDiscount": 0
}

#### 2. Scan Item
   POST /checkouts/{checkoutId}/items
   Content-Type: application/json


Request Body:

{
"id": "A",
"quantity": 4
}


Response:

{
"id": "123e4567-e89b-12d3-a456-426614174000",
"items": { "A": 4 },
"active": true,
"finalPrice": 130,
"bundleDiscount": 0
}

#### 3. Finalize Checkout
   GET /checkouts/{checkoutId}/finalize


Response:

{
"id": "123e4567-e89b-12d3-a456-426614174000",
"items": { "A": 4 },
"active": false,
"finalPrice": 130,
"bundleDiscount": 0
}


### Running the Service

Build the project:

mvn clean install

Run the service:

mvn spring-boot:run


The service will start at http://localhost:8080.