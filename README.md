# Chess-Backend

## PostgreSQL

To start this project create a [PostgreSQL](https://www.postgresql.org/) database from the [Backup.sql](DatabaseQueries/Backup.sql) file.
You will need to edit the usernames in the file appropriately for your database server.

## Frontend

The front end of this project can be found [here](https://github.com/jnewmaninpa/Chess-Frontend).

## Required Environment Variables

To run this code the following environment variables must be set:
- CHESS_BACKEND_SERVER_PORT     (server.port)
- CHESS_DATABASE_URL            (spring.datasource.url)
- CHESS_DATABASE_NAME           (spring.datasource.name)
- CHESS_DATABASE_USERNAME       (spring.datasource.username)
- CHESS_DATABASE_PASSWORD       (spring.datasource.password)
- CHESS_FRONTEND_URL            (frontend.url)

Alternatively the [application.properties](src/main/resources/application.properties) file can be edited directly.
