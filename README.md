# CLINoteapp

This project demonstrates the use of Hibernate ORM with Java to manage many-to-many relationships between `Employee` and `Project` entities.

## Prerequisites

- Java 22
- Maven
- PostgreSQL

## Setup

1. Clone the repository:
    ```sh
    git clone https://github.com/your-username/LearnHibernate.git
    cd LearnHibernate
    ```

2. Configure the PostgreSQL database in `hibernate.cfg.xml`:
    ```xml
    <property name="hibernate.connection.url">jdbc:postgresql://localhost:5432/Demo</property>
    <property name="hibernate.connection.username">your-username</property>
    <property name="hibernate.connection.password">your-password</property>
    ```

3. Build the project using Maven:
    ```sh
    mvn clean install
    ```

## Running the Application

To run the application, execute the `Main` class:
```sh
mvn exec:java -Dexec.mainClass="org.example.relationships.many2many.Main"
```

## Project Structure

- `src/main/java/org/example/relationships/many2many/Employee.java`: Defines the `Employee` entity.
- `src/main/java/org/example/relationships/many2many/Project.java`: Defines the `Project` entity.
- `src/main/java/org/example/relationships/many2many/Main.java`: Contains the main method to run the application.

## Entities

### Employee

```java
@Entity
public class Employee {
    @Id
    private int id;
    private String name;

    @ManyToMany
    @JoinTable(
        name = "employee_project",
        joinColumns = @JoinColumn(name = "employee_id"),
        inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    private List<Project> projects;

    // Getters and setters
}
```

### Project

```java
@Entity
public class Project {
    @Id
    private int id;
    private String name;

    @ManyToMany(mappedBy = "projects")
    private List<Employee> employees;

    // Getters and setters
}
```

## License

This project is licensed under the MIT License.
```
