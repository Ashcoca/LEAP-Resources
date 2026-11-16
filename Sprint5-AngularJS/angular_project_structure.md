## Rules for your structure

Your structure must follow these feature-based conventions:

- **One folder per user-facing feature** under `features/`. A feature is usually one screen or route, or a closely related group of routes.
- **Everything a feature needs lives inside its folder:** components, its routes file, services used only by that feature, and its models.
- **`core/`** holds app-wide singletons that load once: auth, HTTP interceptors, guards, the layout shell and global error handling.
- **`shared/`** holds reusable, feature-agnostic pieces: UI components, pipes, directives and helpers that two or more features use. Nothing in `shared/` should know about a specific feature.
- **Features don't import from inside other features.** If two features need the same thing, decide whether it belongs in `shared/` or `core/`.

Starter skeleton:

```
src/app/
├── core/
├── shared/
├── features/
│   └── <feature-name>/
│       ├── <feature-name>.component.ts
│       ├── <feature-name>.routes.ts
│       └── <feature-name>.service.ts
├── app.component.ts
└── app.routes.ts
```

## Application briefs

Each brief lists what the app must do. Turn these requirements into features, and decide what goes in `core/` and `shared/`.

### App 1: QuickBite (food delivery)

Customers order food from local restaurants and track their delivery.

- Sign up and log in; only logged-in users can order.
- Browse restaurants, filter by cuisine, and search by name.
- View a restaurant's menu and add dishes to a cart.
- Check out with a saved address and payment method.
- Track an active order on a live status screen (preparing, on the way, delivered).
- View past orders and reorder with one click.
- Leave a star rating and review for a restaurant.
- A price display (e.g. $12.50) and a star-rating widget appear on many screens.


### App 2: PageTurn (public library)

Library members find and borrow books; librarians manage the catalog.

- Members log in with their library card number.
- Search the catalog by title, author or ISBN, and view a book's details and availability.
- Place a hold on a book that is checked out.
- "My account" shows current loans, due dates, holds and late fees.
- Renew a loan up to two times.
- Librarians (a separate role) add, edit and remove books.
- Librarians see a report of overdue books.
- Date formatting ("Due in 3 days") is used across several screens.


### App 3: StageDoor (concert ticketing)

Fans buy tickets for concerts and manage them on their phone.

- Home page with featured and upcoming concerts.
- Search concerts by artist, city or date.
- Concert details page with venue, lineup and price tiers.
- Pick seats on an interactive seat map.
- Checkout with a 10-minute countdown timer that holds the seats.
- "My tickets" shows purchased tickets with a QR code for entry.
- Transfer a ticket to a friend by email.
- Follow favorite artists and get notified about new shows.


### App 4: TaskFlow (team task board)

Small teams plan work on Kanban boards.

- Log in and belong to one or more teams.
- A team has several boards; each board has columns (To do, Doing, Done).
- Create, edit and drag task cards between columns.
- Task detail panel with description, assignee, due date, comments and attachments.
- "My tasks" lists every task assigned to me across all boards.
- Team settings: invite members, change roles, rename the team.
- Notifications when someone assigns you a task or comments.
- User avatars appear on cards, comments, settings and notifications.


### App 5: Wanderly (travel booking)

Travelers search for and book flights and hotels.

- Search flights by origin, destination, dates and number of passengers.
- Search hotels by city and dates, with filters for price and rating.
- Compare results and view flight or hotel details.
- Book a flight, a hotel, or both together as a "trip".
- Trips page shows upcoming and past trips with booking references.
- Cancel a booking within the allowed window.
- User profile with saved passport details and preferences.
- Prices show in the user's chosen currency everywhere.


### App 6: PawMatch (pet adoption)

Animal shelters list pets, and families apply to adopt them.

- Browse adoptable pets with filters for species, age, size and location.
- Pet profile page with photos, personality notes and shelter contact.
- Save favorite pets to a shortlist.
- Fill in a multi-step adoption application (household, experience, references).
- Applicants track the status of their applications.
- Shelter staff (a separate role) add pets, update their status and review applications.
- Message thread between an applicant and the shelter about an application.
- A photo gallery component is used on pet profiles and in the shelter's "add pet" form.

