# Itinerease - PoC of using Spring Boot, Angular and Bootstrap

## 📌 Introduction

**Itinerease** is a web application developed to help users create and manage travel itineraries. This application allows users to register, log in, create new itineraries, add and remove tourist attractions and edit their account information. The primary goal of this project is to demonstrate the use of **Spring Boot**, **Angular** and **Bootstrap** in building a full-stack application.

### Frameworks and Technologies Used

- **Spring Boot**: This backend framework simplifies the creation of Java-based web applications. It provides an easy way to set up a RESTful API, connect to a database and manage application logic.
- **Angular**: The frontend of the application is built with Angular, a popular framework for building dynamic, single-page web applications.
- **Bootstrap**: Bootstrap is a powerful CSS framework that helps to design responsive, mobile-first web pages.

### Key Features of the Application

- **User Authentication**: Users can sign up, log in, and manage their accounts.
- **Itinerary Management**: Users can create new itineraries, view their existing itineraries, update details and delete itineraries when no longer needed.
- **Tourist Attractions**: For each itinerary, users can add attractions, update details or remove them as part of itinerary management.

This project aims to showcase how to integrate these frameworks to create a seamless experience for managing travel plans.

## 🛠️ Technologies Used

- **Frontend**: Angular (HTML, SCSS, TypeScript, Bootstrap)
- **Backend**: Spring Boot (Java)
- **Database**: PostgreSQL

## **Backend**

### Folder structure

The backend is built using Spring Boot and follows a modular, layered architecture. Below is an overview of the main folders and their responsibilities:

- `controller`: REST controllers handling HTTP requests for each entity
- `service`: Interfaces for database access using Spring Data JPA
- `repository`: Logic and service laye
- `model`: Contains domain models
- `config` : Contains Web configuration classe (CORS)
- `exceptions`: Containts Global Exception Handler

### Entity Definition

`Model` contains the following objects: Accomodation, Attraction, Itineraty, ItineraryAttraction, ItineraryInsert, Location, Transport and User. All domain entities in the backend are defined using **JPA (Jakarta Persistence API)** annotations for database mapping, **Bean Validation** annotations for input constraints and **Lombok** annotations to eliminate boilerplate code such as getters, setters, and constructors.

Key annotations used:

- `@Entity`: Marks the class as a JPA entity.
- `@AllArgsConstructor`, `@NoArgsConstructor`: Generate constructors with and without parameters.
- `@Table`: Specifies the table name and schema in the database.

```java
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "user", schema = "public")
public class User {...}
```

- `@Getter`, `@Setter`: Automatically generate getter and setter methods for each field.
- `@Id`: Marks the primary key field.
- `@GeneratedValue` : Defines how the primary key is generated.
- `@Column`: Maps the field to a specific column in the database.
- `@NotEmpty`, `@Length`, `@Pattern`, `@Email`: Ensure that data respects validation rules before being persisted.

```java
    @Length(max = 255, message = "Try again! Last name is too long")
    @NotEmpty(message = "Try again! Last name cannot be empty")
    @Pattern(regexp = "\\b[A-Z].*?\\b", message = "Try again! Last name must start with an uppercase letter")
    @Column(name = "last_name")
    @Getter
    @Setter
    private String last_name;
```

### Repository

The `repository` package defines interfaces responsible for interacting with the database. Each domain entity has its corresponding repository interface, which extends **`JpaRepository`**, allowing access to built-in CRUD operations without the need for boilerplate code.

Repositories are typically annotated with `@RepositoryRestResource` to expose RESTful endpoints automatically. In addition to the standard methods provided by `JpaRepository`, custom queries can be defined using the `@Query` annotation, and modifying queries are supported with `@Modifying` and `@Transactional`.

These interfaces serve as the main entry point for all database operations, keeping data access logic cleanly separated from business logic.

For example, the `AccommodationRepository` provides:

- A method to find an accommodation by its name: `Optional<Accommodation> findByName(String name)`
- A custom update query using `@Modifying` and `@Transactional`:

  ```java
    @RepositoryRestResource
    public interface AccommodationRepository extends JpaRepository<Accommodation, Integer>{
        Optional<Accommodation> findByName(String name);

        @Transactional
        @Modifying
        @Query("update Accommodation a set a.name = ?2, a.address = ?3, a.price = ?4 where a.id = ?1")
        void update(int id, String name, String address, float price);
  }
  ```

### Service

The `service` package contains classes responsible for implementing the core business logic of the application. Each domain entity (e.g. `User`, `Transport`, `Attraction`, `Accommodation`, etc.) has its corresponding service class that provides methods for creating, retrieving, updating, and deleting data.

All service classes are annotated with `@Service`, marking them as Spring-managed components. Dependencies are injected using `@Autowired`, either on fields or through constructors. Additionally, **Lombok** annotations like `@AllArgsConstructor` are used to reduce boilerplate code by automatically generating constructors.

Example responsibilities of service classes include:

- Validating input data before passing it to the repository layer.
- Handling entity-specific business rules.
- Coordinating transactions and exception handling.
- Delegating database operations to the appropriate repository.

This layer promotes a clear **separation of concerns**, keeping the controller logic lean and delegating persistence logic to the repository layer.

The `TransportService` class, for example, handles all operations related to the `Transport` entity:

- `create(Transport transport)`: Saves a new transport.
- `getAll()`: Retrieves all transport records from the database.
- `getById(int transportId)`: Fetches a transport by its ID.
- `update(int transportId, Transport updatedTransport)`: Updates the details of an existing transport using a custom repository method.
- `delete(int transportId)`: Deletes a transport by its ID.
- `getTransportByDetails(Transport transport)`: Returns a transport if it matches a specific object, otherwise throws a runtime exception.

```java
    @Service
    @AllArgsConstructor
    public class TransportService {
        @Autowired
        private final TransportRepository _transportRepository;
    ...
    }
```

### Controller

The controller package exposes the application's REST API endpoints. Each controller is responsible for handling HTTP requests and mapping them to appropriate service methods. The controller classes are annotated with `@RestController` to indicate that they handle RESTful requests and `@RequestMapping` to define base paths for the API.

In this case, all routes are prefixed with /api to clearly delineate them as part of the application’s API.

Annotations used in controllers:

- `@RestController`: This annotation is used to declare a class as a controller that handles HTTP requests and automatically serializes the response to JSON.
- `@RequestMapping`: This annotation is used to define the base path for the controller. For example, `@RequestMapping("/api")` means that all routes in this controller will start with /api.
- `@GetMapping`: Handles GET requests and is used to retrieve resources from the server.
- `@PostMapping`: Handles POST requests to create new resources.
- `@PutMapping`: Handles PUT requests to update an existing resource.
- `@DeleteMapping`: Handles DELETE requests to remove a resource.
- `@PathVariable`: Binds a method parameter to a path variable in the URL.
- `@RequestBody`: Binds the request body to a method parameter.
- `@Autowired`: Automatically injects service dependencies into the controller.

For example, The `ItineraryController` handles all routes related to itineraries in the system. It interacts with multiple services like ItineraryService, LocationService, UserService, AccommodationService and TransportService.

- `DELETE /api/itinerary/{id}` – Deletes an itinerary by its ID. This method deletes an itinerary by its ID using the ItineraryService. If the operation is successful, it responds with no content (HTTP 204).

  ```java
      @DeleteMapping("/itinerary/{id}")
      public void delete(@PathVariable Long id) {
          itineraryService.delete(Math.toIntExact(id));
      }
  ```

- `GET /api/itinerary` – Retrieves all itineraries. This method returns a list of all itineraries stored in the system.

  ```java
      @GetMapping("/itinerary")
      public List<Itinerary> getAll() {
          return itineraryService.getAll();
      }
  ```

- `GET /api/itinerary/{id}` – Retrieves a single itinerary by its ID. This method fetches an itinerary by its ID. If the itinerary is not found, it returns a 404 Not Found response.

  ```java
      @GetMapping("/itinerary/{id}")
      public ResponseEntity<Object> getItineraryById(@PathVariable int id) {
          try {
              Optional<Itinerary> itineraryOptional = itineraryService.getById(id);
              if (itineraryOptional.isPresent()) {
                  return ResponseEntity.ok(itineraryOptional.get());
              } else {
                  throw new EntityNotFoundException("Itinerary not found");
              }
          } catch (Exception e) {
              return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
          }
      }
  ```

And other methods.

### Config

The WebConfig class is a Spring `@Configuration` class that defines the Cross-Origin Resource Sharing (CORS) policy for the backend API. This is especially important when the frontend and backend are hosted on different origins—for example, when an Angular application runs on http://localhost:4200 and communicates with a Spring Boot API running on another port.

Key annotations:

- `@Configuration`: Marks the class as a source of bean definitions for the application context.
- `@EnableWebMvc`: Enables full Spring MVC configuration, allowing for advanced customization of web behaviors.
