# Bookify BnB

A modern hotel booking platform built with React, TypeScript frontend and Java Spring Boot backend.

## Architecture

- **Frontend**: React + TypeScript + Vite + TailwindCSS + shadcn/ui
- **Backend**: Java Spring Boot + PostgreSQL + JWT Authentication
- **Database**: PostgreSQL with the same schema as the original Supabase setup

## Project Structure

```
├── src/                    # React frontend source code
├── backend/               # Java Spring Boot backend
├── supabase/             # Database migrations (PostgreSQL)
└── public/               # Static assets
```

## Setup Instructions

### Prerequisites

- **Node.js 18+** and npm
- **Java 17+**
- **Maven 3.6+**
- **PostgreSQL 12+**

### Database Setup

1. Create a PostgreSQL database named `bookify`
2. Run the migration SQL from `supabase/migrations/` to set up the schema
3. The database will include tables for hotels, rooms, bookings, profiles, and user roles

### Backend Setup (Java Spring Boot)

1. Navigate to the backend directory:
   ```sh
   cd backend
   ```

2. Configure environment variables in `application.yml` or set them as environment variables:
   ```
   DB_USERNAME=your_db_username
   DB_PASSWORD=your_db_password
   JWT_SECRET=your_jwt_secret_key
   CORS_ORIGINS=http://localhost:5173,http://localhost:3000
   ```

3. Start the backend server:
   ```sh
   mvn spring-boot:run
   ```
   Or on Windows, run `start.bat`

   The API will be available at `http://localhost:8080/api`

### Frontend Setup (React)

1. Install dependencies:
   ```sh
   npm install
   ```

2. Create a `.env` file based on `.env.example`:
   ```
   VITE_API_BASE_URL=http://localhost:8080/api
   ```

3. Start the development server:
   ```sh
   npm run dev
   ```

   The frontend will be available at `http://localhost:5173`

## Features

- **User Authentication**: JWT-based authentication with role-based access control
- **Hotel Management**: Browse hotels with search and filtering capabilities
- **Room Booking**: Check availability and make reservations
- **Admin Dashboard**: Administrative interface for managing hotels, rooms, and bookings
- **Responsive Design**: Modern UI built with TailwindCSS and shadcn/ui components

## API Endpoints

The Java backend provides RESTful APIs:

- **Authentication**: `/api/auth/*` - Registration, login, logout
- **Hotels**: `/api/hotels/*` - Hotel CRUD operations
- **Rooms**: `/api/rooms/*` - Room management and availability
- **Bookings**: `/api/bookings/*` - Booking lifecycle management
- **Admin**: `/api/admin/*` - Administrative endpoints

## Migration from Supabase

This project has been converted from a Supabase-based architecture to a Java Spring Boot backend:

- **Authentication**: Supabase Auth → JWT Authentication
- **Database**: Supabase Database → Direct PostgreSQL with JPA
- **API**: Supabase Client → REST API calls

The frontend maintains the same user experience while now communicating with the Java backend.

## Development

For detailed backend documentation, see `backend/README.md`.

The project uses:
- **Frontend**: Vite + React + TypeScript + TailwindCSS
- **Backend**: Spring Boot + Spring Security + Spring Data JPA
- **Database**: PostgreSQL with the same schema as the original Supabase setup
