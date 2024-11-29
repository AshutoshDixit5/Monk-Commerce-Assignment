# Monk-Commerce-Assignment

Coupons Management API for E-commerce Platform
Introduction
This project implements a RESTful API for managing and applying different kinds of discount coupons for an e-commerce platform. It is designed to handle a variety of coupon types with the flexibility to add new types in the future.

Features
1.	Coupon Types:
•	Cart-wise: Discounts based on the total amount in the cart.
•	Product-wise: Discounts on specific products
•	BxGy: "Buy X, Get Y" deals with repetition limits.
2.	API Endpoints for coupon management (CRUD operations) and application.
3.	Error Handling: Careful validation and error responses.
4.	Extensible Design: Easily add new coupon types.

Use Cases
Implemented Use Cases
1. Cart-wise Coupons
	Example: 10% off on carts over Rs. 100.
	Condition: Cart total exceeds a certain limit.
	Response: 10% discount applied on the cart total.
2. Product-wise Coupons
	Example: 20% off on Product A.
	Condition: The cart contains Product A
	Response: 20% discount applied to Product A.
3. BxGy Coupons
	Example: Buy 2 of Product X, get 1 of Product Y for free.
	Conditions:
		Verify the cart has the necessary quantity of “buy” items.
		Apply the coupon up to its repetition limit.
	Response: Eligible "get" products are added for free.

Unimplemented Use Cases
1.	Coupon Stacking: Support for using multiple coupons at once.
2.	Tiered Discounts: Variable discount rates based on cart tiers.
3.	Customer-Specific Coupons: Coupons that are tailored to a user’s past purchases.
4.	Geography-Specific Coupons: Coupons valid only in certain regions.

API Endpoints
Coupon Management
1.	POST /coupons: Create a new coupon.
2.	GET /coupons: Get all coupons.
3.	GET /coupons/{id}: Get  a specific coupon by ID.
4.	PUT /coupons/{id}: Update a coupon by ID.
5.	DELETE /coupons/{id}: Delete a coupon by ID.
Coupon Application
1.	POST /applicable-coupons: Fetch applicable coupons for a cart and calculate discounts.
2.	POST /apply-coupon/{id}: Apply a specific coupon to the cart and update prices.

Database Schema
Coupons Table
Field	Type	Description
id	Integer	Primary key.
type	String	Coupon type (cart-wise, product-wise, bxgy).
details	JSON	Stores coupon-specific details.
expiration_date	Date	Expiry date of the coupon.
repetition_limit	Integer	Max times the coupon can be applied (for BxGy).

Coupon_Products Table (for BxGy)
Field	Type	Description
id	Integer	Primary key.
coupon_id	Integer	Foreign key to coupons.
product_id	Integer	Product ID eligible for the coupon.
type	Enum	buy or get product type.

Assumptions
1.	Cart prices are pre-fetched and provided in the request.
2.	Only valid and in-stock products are included in the cart.
3.	A single coupon can be applied per request.

Limitations
1.	No support for coupons based on customer location or demographics.
2.	No logic for coupon stacking.
3.	Expired coupons are not archived but outright rejected.

Sample Payloads
Creating a Coupon
POST /coupons
{
  "type": "cart-wise",
  "details": {
    "threshold": 100,
    "discount": 10
  },
  "expiration_date": "2024-12-31"
}
Applying a Coupon
POST /apply-coupon/{id}
{
  "cart": {
    "items": [
      {"product_id": 1, "quantity": 6, "price": 50},
      {"product_id": 2, "quantity": 3, "price": 30},
      {"product_id": 3, "quantity": 2, "price": 25}
    ]
  }
}
Unit Tests
1.	Validate coupon creation and retrieval.
2.	Test edge cases:
•	Cart with no eligible coupons.
•	Exceeding repetition limit for BxGy.
•	Expired coupons.

How to Run
1.	Clone the repository.
2.	Install dependencies: mvn clean install.
3.	Run the application: mvn spring-boot:run.
4.	Access API documentation at http://localhost:8080/swagger-ui.html.

Future Improvements
1.	Add user-based and location-based coupon constraints.
2.	Implement coupon stacking and tiered discounts.
3.	Introduce advanced analytics for coupon usage trends.

