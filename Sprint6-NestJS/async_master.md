# Async Showdown: Fix the QuickBite App

A 20-minute team competition on Promises, `async/await`, `Promise.all` and `Promise.allSettled`.


## The story

You have just joined **QuickBite**, a food delivery startup. The previous developer left in a hurry and the code is a mess. The app works (mostly), but it is slow, hard to read, and one failing service takes the whole screen down.

## Rules

1. Do not edit anything in the "FAKE QUICKBITE SERVER" section.
2. The output must stay the same as the original unless the round says otherwise.
3. The facilitator may ask **any** team member to explain the fix, so make sure the whole group understands it.



## Starter code: `quickbite.js`

Run it with `node quickbite.js`. At the bottom of the file, uncomment the round you are working on.

```js
// =====================================================
//  FAKE QUICKBITE SERVER  (DO NOT EDIT THIS SECTION)
// =====================================================
const delay = (ms) => new Promise((resolve) => setTimeout(resolve, ms));

// Old-style callback API (written years ago)
function getUser(userId, callback) {
  setTimeout(() => {
    if (userId !== 101) {
      callback(new Error("User not found"));
      return;
    }
    callback(null, { id: 101, name: "Priya", lastOrderId: 5001 });
  }, 500);
}

// Newer APIs: these already return promises
function getOrder(orderId) {
  return delay(500).then(() => ({
    id: orderId, item: "Paneer Biryani", restaurantId: 77, riderId: 9,
  }));
}
function getRestaurant(restaurantId) {
  return delay(500).then(() => ({ id: restaurantId, name: "Spice Villa" }));
}
function getRider(riderId) {
  return delay(500).then(() => ({ id: riderId, name: "Arjun", etaMinutes: 12 }));
}

function getNearbyRestaurants() {
  return delay(1000).then(() => ["Spice Villa", "Dosa Corner", "Pizza Hub"]);
}
function getOffers() {
  return delay(1000).then(() => ["50% off up to Rs.100", "Free delivery"]);
}
function getCart() {
  return delay(1000).then(() => ({ items: 2, total: 380 }));
}

function getOrderHistory() {
  return delay(400).then(() => ({ count: 12 }));
}
function getRecommendations() {
  return delay(600).then(() => {
    throw new Error("Recommendation service is down");
  });
}
function getWalletBalance() {
  return delay(500).then(() => ({ balance: 450 }));
}

function notifyRider(name) {
  return delay(300).then(() => console.log(`Notified ${name}`));
}

// =====================================================
//  ROUND 1: Untangle the pyramid
// =====================================================
function trackOrder(userId) {
  getUser(userId, function (err, user) {
    if (err) {
      console.log("Could not track order: " + err.message);
    } else {
      getOrder(user.lastOrderId).then(function (order) {
        getRestaurant(order.restaurantId).then(function (restaurant) {
          getRider(order.riderId).then(function (rider) {
            console.log(
              `Hi ${user.name}! Your ${order.item} from ${restaurant.name} ` +
              `is on the way with ${rider.name}. ETA: ${rider.etaMinutes} mins`
            );
          }).catch(function (err) {
            console.log("Could not track order: " + err.message);
          });
        }).catch(function (err) {
          console.log("Could not track order: " + err.message);
        });
      }).catch(function (err) {
        console.log("Could not track order: " + err.message);
      });
    }
  });
}

// =====================================================
//  ROUND 2: The slow home page
// =====================================================
async function loadHomePage() {
  console.time("Home page loaded in");

  const restaurants = await getNearbyRestaurants();
  const offers = await getOffers();
  const cart = await getCart();

  console.log("Restaurants:", restaurants.join(", "));
  console.log("Offers:", offers.join(" | "));
  console.log(`Cart: ${cart.items} items, Rs.${cart.total}`);

  console.timeEnd("Home page loaded in");
}

// =====================================================
//  ROUND 3: One failure kills everything
// =====================================================
async function loadProfilePage() {
  try {
    const [history, recommendations, wallet] = await Promise.all([
      getOrderHistory(),
      getRecommendations(),
      getWalletBalance(),
    ]);
    console.log(`Orders: ${history.count} past orders`);
    console.log(`Recommended: ${recommendations.join(", ")}`);
    console.log(`Wallet: Rs.${wallet.balance}`);
  } catch (err) {
    console.log("Something went wrong: " + err.message);
  }
}

// =====================================================
//  TIE-BREAKER: The lying success message
// =====================================================
async function notifyAllRiders() {
  const riders = ["Arjun", "Meera", "Kabir"];

  riders.forEach(async (rider) => {
    await notifyRider(rider);
  });

  console.log("All riders notified");
}

// =====================================================
//  RUN: uncomment the round you are working on
// =====================================================
// trackOrder(101);
// trackOrder(999);
// loadHomePage();
// loadProfilePage();
// notifyAllRiders();
```

---

## Round 1: Untangle the pyramid (6 min)

**Screen:** Order tracking. To show the tracking message, the app needs the user, then the user's last order, then the restaurant and rider for that order.

**Problem:** `trackOrder` mixes an old callback API with nested `.then()` calls, and the same error handling is copy-pasted four times.

**Your tasks**

1. Write `getUserAsync(userId)` that wraps the callback-based `getUser` and returns a Promise. Do not change `getUser` itself.
2. Rewrite `trackOrder` as an `async` function using `await`. No nesting, no `.then()`.
3. Handle errors in **one** place.

**Check yourself**

- `trackOrder(101)` prints:
  `Hi Priya! Your Paneer Biryani from Spice Villa is on the way with Arjun. ETA: 12 mins`
- `trackOrder(999)` prints:
  `Could not track order: User not found`

---

## Round 2: The slow home page (4 min)

**Screen:** Home page. It shows nearby restaurants, current offers and the cart.

**Problem:** Users complain the home page takes 3 seconds to load. Each API takes 1 second, and none of them needs the result of another.

**Your tasks**

1. Make `loadHomePage` finish in about 1 second instead of 3.
2. The printed output must stay exactly the same (apart from the time).

**Check yourself**

- `Home page loaded in` shows roughly 1000 ms, not 3000 ms.

**Think about it:** in Round 1 you awaited one call after another. Why is that wrong here but right there?

---

## Round 3: One failure kills everything (5 min)

**Screen:** Profile page. It shows order history, recommendations and wallet balance.

**Problem:** The recommendation service is down today. Because of that one failure the user sees only `Something went wrong`, even though order history and wallet loaded fine.

**Your tasks**

1. Still load all three in parallel.
2. Show every section that loaded successfully.
3. For a section that failed, print a friendly line with the reason instead of crashing the page.
4. Your code must keep working if a different service fails tomorrow. Do not hard-code "recommendations failed".

**Check yourself:** the output should be

```
Orders: 12 past orders
Recommendations unavailable (Recommendation service is down)
Wallet: Rs.450
```

---

## Tie-breaker: The lying success message (3 min)

**Screen:** Admin panel. A button notifies all riders about a surge in orders.

**Problem:** Run `notifyAllRiders()`. It prints `All riders notified` **before** any rider is actually notified.

**Your tasks**

1. Explain in one sentence why this happens.
2. Fix it so `All riders notified` is printed only after all three riders are notified.
