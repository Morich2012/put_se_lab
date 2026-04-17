# Auction system

## Introduction

Specification of functional requirements as part of computerisation of the product sale process based on the auction mechanism.

## Business processes

---
<a id="bc1"></a>
### BC1: Auction sale

**Actors:** [Seller](#ac1), [Buyer](#ac2)

**Description:** Business process describing a sale by the auction mechanism.

**Main scenario:**
1. [Seller](#ac1) offers the product at an auction. ([UC1](#uc1))
2. [Buyer](#ac2) offers a bid for the product that is higher than the currently highest bid. ([BR1](#br1)) ([UC3](#uc3))
3. [Buyer](#ac2) wins the auction. ([BR2](#br2)) ([UC4](#uc4))
4. [Buyer](#ac2) transfers the amount due to the Seller.
5. [Seller](#ac1) transfers the product to the Buyer.

**Alternative scenarios:**

2.A. Buyer's bid has been outbid and [Buyer](#ac2) wants to outbid the current highest bid.
* 2.A.1. Continue at step 2.

3.A. Auction time has elapsed and [Buyer](#ac2) has lost the auction. ([BR2](#br2)) ([UC4](#uc4))
* 3.A.1. End of use case.

---

## Actors

<a id="ac1"></a>
### AC1: Seller

A person offering goods at an auction.

<a id="ac2"></a>
### AC2: Buyer

A person intending to purchase a product at an auction.

---

## User level use cases

### Actors and their goals

[Seller](#ac1):
* [UC1](#uc1): Offering a product at an auction

[Buyer](#ac2):
* [UC2](#uc2): Browsing available auctions
* [UC3](#uc3): Placing a bid
* [UC4](#uc4): Viewing auction result
* [UC5](#uc5): Viewing auction details

---

<a id="uc1"></a>
### UC1: Offering a product at an auction

**Actors:** [Seller](#ac1)

**Main scenario:**
1. [Seller](#ac1) reports to the system the willingness to offer the product up at an auction.
2. System asks for the product data and initial price.
3. [Seller](#ac1) provides product data and the initial price.
4. System verifies data correctness.
5. System informs that the product has been successfully put up for auction.

**Alternative scenarios:**

4.A. Incorrect or incomplete product data has been entered.
* 4.A.1. System informs about incorrectly entered data.
* 4.A.2. Continue at step 2.

---

<a id="uc2"></a>
### UC2: Browsing available auctions

**Actors:** [Buyer](#ac2)

**Main scenario:**
1. [Buyer](#ac2) requests to view the list of currently active auctions.
2. System retrieves and displays the list of active auctions, including basic product information and current highest bid for each. ([BO1](#bo1), [BO2](#bo2))
3. [Buyer](#ac2) browses the list.

**Alternative scenarios:**

2.A. There are no active auctions at the moment.
* 2.A.1. System informs [Buyer](#ac2) that no auctions are currently available.
* 2.A.2. End of use case.

---

<a id="uc3"></a>
### UC3: Placing a bid

**Actors:** [Buyer](#ac2)

**Main scenario:**
1. [Buyer](#ac2) selects an active auction from the list. ([UC2](#uc2))
2. System displays full auction details, including current highest bid and time remaining. ([UC5](#uc5))
3. [Buyer](#ac2) submits a bid amount.
4. System verifies that the bid amount exceeds the current highest bid by at least EUR 1.00. ([BR1](#br1))
5. System records the bid as the new highest bid. ([BO3](#bo3))
6. System informs [Buyer](#ac2) that the bid has been successfully placed.

**Alternative scenarios:**

4.A. The bid amount does not exceed the current highest bid by the required minimum.
* 4.A.1. System informs [Buyer](#ac2) that the bid is too low, indicating the minimum acceptable amount. ([BR1](#br1))
* 4.A.2. Continue at step 3.

4.B. The auction has already ended at the time of placing the bid.
* 4.B.1. System informs [Buyer](#ac2) that the auction is no longer active.
* 4.B.2. End of use case.

---

<a id="uc4"></a>
### UC4: Viewing auction result

**Actors:** [Buyer](#ac2), [Seller](#ac1)

**Main scenario:**
1. The auction time expires. ([BR2](#br2))
2. System determines the outcome of the auction.
3. System identifies the [Buyer](#ac2) with the highest bid as the winner. ([BR2](#br2))
4. System informs the winning [Buyer](#ac2) and the [Seller](#ac1) of the final result, including the winning bid amount.

**Alternative scenarios:**

3.A. No bids were submitted before the auction ended.
* 3.A.1. System informs [Seller](#ac1) that the auction ended with no winner.
* 3.A.2. End of use case.

---

<a id="uc5"></a>
### UC5: Viewing auction details

**Actors:** [Buyer](#ac2)

**Main scenario:**
1. [Buyer](#ac2) selects an auction from the list. ([UC2](#uc2))
2. System retrieves and displays the full details of the selected auction, including product description, current highest bid, time remaining, and bid history. ([BO1](#bo1), [BO2](#bo2), [BO3](#bo3))
3. [Buyer](#ac2) reviews the auction details.

**Alternative scenarios:**

2.A. The selected auction has already ended.
* 2.A.1. System informs [Buyer](#ac2) that the auction is no longer active and displays the final result.
* 2.A.2. End of use case.

---

## Business objects (also known as domain or IT objects)

<a id="bo1"></a>
### BO1: Auction

The auction is a form of concluding a sale and purchase transaction in which the Seller specifies the starting bid of the product, while the Buyers may offer their own purchase offer, each time proposing a bid higher than the currently offered bid. The auction ends after a specified period of time. If at least one purchase offer has been submitted, the product is purchased by the Buyer who offered the highest bid.

<a id="bo2"></a>
### BO2: Product

A physical or digital item to be auctioned.

<a id="bo3"></a>
### BO3: Bid

An offer submitted by a Buyer during an active auction, consisting of a monetary amount that must exceed the current highest bid by at least EUR 1.00. ([BR1](#br1))

---

## Business rules

<a id="br1"></a>
### BR1: Bidding at auction

Bidding at auction requires submitting an amount higher than current by a minimum of EUR 1.00

<a id="br2"></a>
### BR2: Winning an auction

Auction is won by [Buyer](#ac2) who submitted the highest bid before the end of the auction (time expires).

---

## CRUDL Matrix

| Use case                              | Auction (BO1) | Product (BO2) | Bid (BO3) |
| ------------------------------------- | :-----------: | :-----------: | :-------: |
| UC1: Offering a product at an auction |       C       |       C       |           |
| UC2: Browsing available auctions      |       L       |       L       |           |
| UC3: Placing a bid                    |       R       |       R       |     C     |
| UC4: Viewing auction result           |      R/U      |       R       |     R     |
| UC5: Viewing auction details          |       R       |       R       |     L     |
