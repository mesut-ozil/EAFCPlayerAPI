# FUTBIN Clone 

A Spring Boot REST API for an FC 27 football player database, inspired by FUTBIN.

The project provides a backend for storing, searching, filtering and managing player data, with PostgreSQL used for persistent storage and optimised player-name searching.

The long-term goal is to extend the application with AI-powered player recommendations, allowing users to describe the type of player they are looking for in natural language.

⸻

## Features

* Player database using PostgreSQL
* RESTful API built with Spring Boot
* Player search by name
* Advanced player filtering
* Pagination
* Player, team and league relationships
* CSV player-data import
* Request validation
* Structured exception handling
* Duplicate-player detection
* PostgreSQL trigram search
* GIN index for optimised partial-name searches
* Unit and API testing

⸻

## Tech Stack

Backend

* Java 21
* Spring Boot
* Spring Web
* Spring Data JPA
* Bean Validation
* Lombok
* Maven

Database

* PostgreSQL
* PostgreSQL pg_trgm extension
* GIN indexes

Development

* IntelliJ IDEA
* Git
* GitHub

⸻

## Architecture

                    ┌─────────────────┐
                    │     Client      │
                    └────────┬────────┘
                             │
                             ▼
                    ┌─────────────────┐
                    │   Spring Boot   │
                    │    REST API     │
                    └────────┬────────┘
                             │
              ┌──────────────┼──────────────┐
              ▼              ▼              ▼
        ┌───────────┐  ┌───────────┐  ┌────────────┐
        │  Player   │  │   Team    │  │   League   │
        │ Repository│  │ Repository│  │ Repository │
        └───────────┘  └───────────┘  └────────────┘
              │              │              │
              └──────────────┼──────────────┘
                             ▼
                    ┌─────────────────┐
                    │   PostgreSQL    │
                    └─────────────────┘

⸻

## Player Data

The database stores information including:

|Category |	Data|
|---|---|
|Identity|First Name, Last Name, Display Name|
|Rating	|Overall rating|
|Position|Player Position|
|Club|Team|
|League	|League|
|Nation	|Nationality|
|Gender	|Male, Female|
|Preferred Foot|Left, Right|
|Skill Moves|1–5|
|Weak Foot|1–5|
|Stats|Shooting, Dribbling, Pace|
|More Stats| Passing, Defending, Physical|
|Price|Player price|

Player data can be imported from CSV into PostgreSQL.

⸻

## Player Search

The API supports searching for players by name.
PostgreSQL’s pg_trgm extension is used to improve partial-text searching.
For example:

`GET /players/search?name=ess`

can find players whose names contain the searched text.

A GIN trigram index is used to improve query performance for these searches.
This allows the database to handle partial-name searches more efficiently than a standard sequential scan.

⸻

## Player Filtering

Players can be filtered using multiple attributes.
For example:

Position = RW, 
Rating >= 85,
Pace >= 90,
Skill Moves >= 4

Filters can also be combined to create more specific searches.
The API uses pagination to prevent unnecessarily large responses.

⸻

## REST API

Get Players:

`GET /players`

Returns a paginated list of players.

Get Player:

`GET /players/{id}`

Returns a specific player by ID.

Search Players:

`GET /players/search?name={name}`

Searches for players by name.

Filter Players:

`GET /players/filter...`

Filters players using supported player attributes.

Add Player:

`POST /players`

Adds a new player to the database.

⸻

## Validation & Error Handling

The API includes validation and structured exception handling.

Current error cases include:

* Player not found
* Duplicate player
* Invalid filter parameter
* Invalid request data

Errors are returned using a consistent response structure containing
a status, message and timestamp

⸻

## Testing
 
The project includes tests covering key API functionality, including:

* Player retrieval
* Player searching
* Player filtering
* Validation
* Exception handling
* Player creation

⸻

## Roadmap

Completed

* Spring Boot REST API
* PostgreSQL database
* Player entity
* Team entity
* League entity
* CSV player-data import
* Player search
* PostgreSQL trigram search
* GIN search index
* Player filtering
* Pagination
* DTOs
* Request validation
* Exception handling
* Duplicate-player detection
* API tests
* Git/GitHub

## Planned

* AI-powered player recommendations
* Natural-language player search
* Similar-player recommendations
* Live FC 27 player prices
* Price history
* Redis caching
* Rate limiting
* React/Next.js frontend
* Player comparison
* Player statistics visualisations
* Authentication and authorisation
* Dockerisation
* CI/CD
* Cloud deployment

⸻

## Future AI Integration

AI has not yet been implemented.
The planned AI functionality will allow users to describe what they are looking for using natural language.

For example:

"Find me a fast Premier League winger with 4 star skills."

The AI could eventually convert this into structured requirements such as:

Position = LW/RW
League = Premier League
Pace = High
Skill Moves >= 4

These requirements could then be passed to the existing player filtering system.

This approach would allow the AI to work alongside the existing backend and database, rather than replacing the database’s filtering and search functionality.

⸻

## Project Goals

The project is being developed to gain practical experience with:

* Java and Spring Boot
* REST API development
* PostgreSQL
* Relational database design
* JPA and Hibernate
* Database indexing and query optimisation
* Search functionality
* API validation
* Automated testing
* External API integration
* AI API integration
* Docker and CI/CD

The eventual goal is to combine these technologies into a full-stack football player discovery and recommendation platform.
