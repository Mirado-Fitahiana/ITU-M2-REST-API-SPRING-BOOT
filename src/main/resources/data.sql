-- Roles
INSERT INTO roles (id, name) VALUES (1, 'ADMIN');
INSERT INTO roles (id, name) VALUES (2, 'CUSTOMER');
INSERT INTO roles (id, name) VALUES (3, 'AGENT');

-- Users (password = "password" BCrypt encoded)
INSERT INTO users (id, name, email, password, enabled) VALUES
(1, 'Admin User',    'admin@travel.com',    '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', true),
(2, 'Customer User', 'customer@travel.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', true),
(3, 'Agent User',    'agent@travel.com',    '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', true);

-- User roles
INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO user_roles (user_id, role_id) VALUES (2, 2);
INSERT INTO user_roles (user_id, role_id) VALUES (3, 3);

-- Destinations
INSERT INTO destinations (id, name, country) VALUES (1, 'Paris', 'France');
INSERT INTO destinations (id, name, country) VALUES (2, 'Rome', 'Italy');
INSERT INTO destinations (id, name, country) VALUES (3, 'Tokyo', 'Japan');

-- Trips
INSERT INTO trips (id, title, start_date, end_date, price, destination_id) VALUES
(1, 'Paris Romantic Getaway',   '2026-06-01', '2026-06-08', 1200.00, 1),
(2, 'Rome Historical Tour',     '2026-07-10', '2026-07-18', 980.00,  2),
(3, 'Tokyo Adventure',          '2026-08-05', '2026-08-15', 1850.00, 3),
(4, 'Paris Art & Culture',      '2026-09-12', '2026-09-19', 1100.00, 1);

-- Flights
INSERT INTO flights (id, airline, departure_city, arrival_city, departure_time, available_seats, trip_id) VALUES
(1, 'Air France',     'New York',  'Paris',  '2026-06-01 08:00:00', 50, 1),
(2, 'Air France',     'Paris',     'New York','2026-06-08 14:00:00', 50, 1),
(3, 'Alitalia',       'London',    'Rome',   '2026-07-10 09:30:00', 40, 2),
(4, 'Alitalia',       'Rome',      'London', '2026-07-18 17:00:00', 40, 2),
(5, 'Japan Airlines', 'Los Angeles','Tokyo', '2026-08-05 11:00:00', 60, 3),
(6, 'Japan Airlines', 'Tokyo',     'Los Angeles','2026-08-15 15:00:00', 60, 3),
(7, 'Air France',     'Chicago',   'Paris',  '2026-09-12 07:00:00', 45, 4);

-- Hotels
INSERT INTO hotels (id, name, city, stars, price_per_night, trip_id) VALUES
(1, 'Hotel Le Marais',       'Paris',  4, 180.00, 1),
(2, 'Hotel Colosseum View',  'Rome',   4, 150.00, 2),
(3, 'Shinjuku Grand Hotel',  'Tokyo',  5, 250.00, 3),
(4, 'Hotel Louvre Palace',   'Paris',  5, 320.00, 4);

-- Passengers
INSERT INTO passengers (id, first_name, last_name, passport_number) VALUES
(1, 'John',  'Doe',   'P12345678'),
(2, 'Jane',  'Smith', 'P87654321'),
(3, 'Alice', 'Brown', 'P11223344');

-- Bookings
INSERT INTO bookings (id, booking_date, status, total_price, user_id, trip_id) VALUES
(1, '2026-03-01', 'CONFIRMED', 1200.00, 2, 1),
(2, '2026-03-10', 'PENDING',   980.00,  2, 2),
(3, '2026-03-15', 'CONFIRMED', 1850.00, 1, 3);

-- Booking passengers
INSERT INTO booking_passengers (booking_id, passenger_id) VALUES (1, 1);
INSERT INTO booking_passengers (booking_id, passenger_id) VALUES (1, 2);
INSERT INTO booking_passengers (booking_id, passenger_id) VALUES (2, 3);
INSERT INTO booking_passengers (booking_id, passenger_id) VALUES (3, 1);

-- Payments
INSERT INTO payments (id, amount, method, status, payment_date, booking_id) VALUES
(1, 1200.00, 'CREDIT_CARD',   'COMPLETED', '2026-03-01', 1),
(2, 980.00,  'PAYPAL',        'PENDING',   '2026-03-10', 2),
(3, 1850.00, 'BANK_TRANSFER', 'COMPLETED', '2026-03-15', 3);

-- Reviews
INSERT INTO reviews (id, rating, comment, user_id, trip_id) VALUES
(1, 5, 'Absolutely wonderful trip to Paris!',    2, 1),
(2, 4, 'Rome was amazing, history everywhere.',  1, 3);
