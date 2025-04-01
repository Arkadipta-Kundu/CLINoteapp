# Notable CLI Application

## Overview
Notable CLI is a command-line interface application for managing notes. Users can register, log in, create, view, update, and delete notes. The application uses Hibernate for ORM and PostgreSQL as the database.

## Features
- User Registration
- User Login
- Create Note
- View Notes
- Update Note
- Delete Note

## Prerequisites
- Java 24
- Maven
- PostgreSQL

## Setup

1. **Clone the repository:**
   ```sh
   git clone https://github.com/Arkadipta-Kundu/CLINoteapp.git
   cd notable-cli
   ```

2. **Configure the database:**
   Update the `hibernate.cfg.xml` file with your PostgreSQL database credentials.
   ```xml
   <property name="hibernate.connection.url">jdbc:postgresql://localhost:5432/postgres</property>
   <property name="hibernate.connection.username">postgres</property>
   <property name="hibernate.connection.password">0000</property>
   ```

3. **Build the project:**
   ```sh
   mvn clean install
   ```

4. **Run the application:**
   ```sh
   mvn exec:java -Dexec.mainClass="org.arkadipta.Main"
   ```

## Usage

1. **Register:**
   - Enter your name, username, and password to register.

2. **Login:**
   - Enter your username and password to log in.

3. **Create Note:**
   - Enter the title and content of the note.

4. **View Notes:**
   - View all notes associated with the logged-in user.

5. **Update Note:**
   - Enter the note ID, new title, and new content to update a note.

6. **Delete Note:**
   - Enter the note ID to delete a note.

## Dependencies
- Hibernate Core 6.6.8.Final
- PostgreSQL JDBC 42.7.5

## License
This project is licensed under the MIT License.
