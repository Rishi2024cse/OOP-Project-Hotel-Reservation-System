# Bookify Backend - Java Spring Boot API

This is the Java Spring Boot backend for the Bookify BnB application, converted from the original Supabase-based architecture.

## Features

- **Authentication & Authorization**: JWT-based authentication with role-based access control
- **Hotel Management**: CRUD operations for hotels with search and filtering
- **Room Management**: Room availability checking and management
- **Booking System**: Complete booking lifecycle management
- **Admin Dashboard**: Administrative endpoints for system management

## Technology Stack

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Security** (JWT Authentication)
- **Spring Data JPA** (Database ORM)
- **PostgreSQL** (Database)
- **Maven** (Build Tool)

## Database Schema

The application uses the same PostgreSQL schema as the original Supabase setup:

- `profiles` - User profiles
- `user_roles` - User role assignments
- `hotels` - Hotel information
- `rooms` - Room details and availability
- `bookings` - Booking records

## API Endpoints

### Authentication
- `POST /api/auth/register` - User registration
- `POST /api/auth/login` - User login
- `POST /api/auth/logout` - User logout

### Hotels
- `GET /api/hotels` - Get all hotels (with optional search/filter)
- `GET /api/hotels/{id}` - Get hotel by ID
- `POST /api/hotels` - Create hotel (Admin only)
- `PUT /api/hotels/{id}` - Update hotel (Admin only)
- `DELETE /api/hotels/{id}` - Delete hotel (Admin only)

### Rooms
- `GET /api/rooms/hotel/{hotelId}` - Get rooms by hotel
- `GET /api/rooms/hotel/{hotelId}/available` - Get available rooms
- `GET /api/rooms/{id}` - Get room by ID
- `POST /api/rooms` - Create room (Admin only)
- `PUT /api/rooms/{id}` - Update room (Admin only)
- `DELETE /api/rooms/{id}` - Delete room (Admin only)

### Bookings
- `GET /api/bookings/my` - Get user's bookings
- `GET /api/bookings/{id}` - Get booking by ID
- `POST /api/bookings` - Create booking
- `PUT /api/bookings/{id}` - Update booking
- `PUT /api/bookings/{id}/status` - Update booking status
- `PUT /api/bookings/{id}/payment-status` - Update payment status
- `GET /api/bookings/check-availability` - Check room availability
- `DELETE /api/bookings/{id}` - Delete booking

### Admin
- `GET /api/admin/stats` - Get system statistics
- `GET /api/admin/bookings` - Get all bookings
- `GET /api/admin/bookings/recent` - Get recent bookings

## Setup Instructions

1. **Prerequisites**
   - Java 17 or higher
   - Maven 3.6+
   - PostgreSQL 12+

2. **Database Setup**
   - Create a PostgreSQL database named `bookify`
   - Run the existing migration SQL from the Supabase setup
   - Update database credentials in `application.yml`

3. **Environment Variables**
   ```
   DB_USERNAME=your_db_username
   DB_PASSWORD=your_db_password
   JWT_SECRET=your_jwt_secret_key
   CORS_ORIGINS=http://localhost:5173,http://localhost:3000
   ```

4. **Run the Application**
   ```bash
   cd backend
   mvn spring-boot:run
   ```

The API will be available at `http://localhost:8080/api`

## Configuration

Key configuration options in `application.yml`:

- **Database**: PostgreSQL connection settings
- **JWT**: Token expiration and secret key
- **CORS**: Allowed origins for frontend integration
- **Security**: Role-based access control settings

## Migration from Supabase

This backend maintains API compatibility with the original frontend while replacing:

- Supabase Auth → JWT Authentication
- Supabase Database → Direct PostgreSQL with JPA
- Supabase Client → REST API calls

The frontend will need minimal changes to work with this backend - mainly updating the API base URL and authentication flow.
