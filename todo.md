Thème

Travel Booking API

Entités
User
Role
Booking
Trip
Flight
Hotel
Destination
Payment
Passenger
Review
Relations
1 à n
User 1 → n Booking
Destination 1 → n Trip
Trip 1 → n Flight
Trip 1 → n Hotel
Booking 1 → n Payment
n à n
Booking n ↔ n Passenger
User n ↔ n Role
User n ↔ n Trip via Review possible logique métier
Endpoints CRUD
User
POST /api/users
GET /api/users
GET /api/users/{id}
PUT /api/users/{id}
DELETE /api/users/{id}
Trip
POST /api/trips
GET /api/trips
GET /api/trips/{id}
PUT /api/trips/{id}
DELETE /api/trips/{id}
Booking
POST /api/bookings
GET /api/bookings
GET /api/bookings/{id}
PUT /api/bookings/{id}
DELETE /api/bookings/{id}
Flight
POST /api/flights
GET /api/flights
GET /api/flights/{id}
PUT /api/flights/{id}
DELETE /api/flights/{id}
Hotel
POST /api/hotels
GET /api/hotels
GET /api/hotels/{id}
PUT /api/hotels/{id}
DELETE /api/hotels/{id}
Endpoints complexes (agrégation)
1. Détail complet d’une réservation

GET /api/bookings/{id}/details

Retour

booking
user
passengers
trip
flights
hotels
payments
liens HATEOAS
2. Dashboard client

GET /api/users/{id}/dashboard

Retour

user
totalBookings
totalSpent
upcomingTrips
completedTrips
reviews
liens HATEOAS
3. Vue complète d’un voyage

GET /api/trips/{id}/full

Retour

trip
destination
flights
hotels
bookingsCount
averageRating
availableSeats
4. Statistiques de destination

GET /api/destinations/{id}/stats

Retour

destination
numberOfTrips
numberOfBookings
averageTripPrice
topHotels
topFlights
5. Historique financier

GET /api/users/{id}/payments/summary

Retour

user
totalPaid
pendingPayments
refundedPayments
bookings associés
Minimum demandé : 2 endpoints d’agrégation

Tu peux retenir :

GET /api/bookings/{id}/details
GET /api/users/{id}/dashboard
Authentification / sécurité
JWT
POST /api/auth/register
POST /api/auth/login
Role
ADMIN
CUSTOMER
AGENT
Exemple d’autorisations
ADMIN : gestion complète
CUSTOMER : ses réservations, ses paiements, ses avis
AGENT : gestion trips/flights/hotels
Filtres
Trip

GET /api/trips?destination=Paris&minPrice=100&maxPrice=900&startDate=2026-06-01

Booking

GET /api/bookings?status=CONFIRMED&userId=1

Hotel

GET /api/hotels?city=Rome&stars=4

Flight

GET /api/flights?departure=Paris&arrival=Rome

HATEOAS

Exemple retour GET /api/bookings/{id}/details

{
  "id": 15,
  "status": "CONFIRMED",
  "totalPrice": 1850,
  "_links": {
    "self": { "href": "/api/bookings/15/details" },
    "user": { "href": "/api/users/3" },
    "trip": { "href": "/api/trips/8/full" },
    "payments": { "href": "/api/bookings/15/payments" },
    "cancel": { "href": "/api/bookings/15" }
  }
}
Base de données

H2 Database

Config
base en mémoire pour test/dev
console H2 activée
jeu de données initial via data.sql
Stack technique
Spring Boot
Spring Data JPA
Spring Security
JWT
H2 Database
Spring HATEOAS
Lombok
Validation
Modèle conseillé
User
id
name
email
password
Role
id
name
Booking
id
bookingDate
status
totalPrice
Trip
id
title
startDate
endDate
price
Flight
id
airline
departureCity
arrivalCity
departureTime
Hotel
id
name
city
stars
pricePerNight
Destination
id
name
country
Payment
id
amount
method
status
paymentDate
Passenger
id
firstName
lastName
passportNumber
Review
id
rating
comment
Version finale courte à rendre

Projet : Travel Booking API

Contraintes respectées :

5+ entités
relations 1:n et n:n
CRUD
JWT
Role
Filtres
HATEOAS
H2
2+ endpoints d’agrégation
Endpoints d’agrégation retenus
GET /api/bookings/{id}/details
GET /api/users/{id}/dashboard