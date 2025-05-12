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

## **Frontend**

### Folder structure

The frontend is built using Angular and follows a component-based architecture. Below is an overview of the main folders and their responsibilities:

- `config`: Contains global variables (endpointAPI, which defines the base URL for backend communication)
- `dtos`: Stores Data Transfer Object (DTO) interfaces and models that define the structure of data exchanged between the frontend and backend. These help ensure type safety and consistency across the app.
- `pages`: Contains the main components grouped by feature or route. Each subfolder typically includes an Angular component, its HTML template, styles, and logic, making it easy to manage feature-specific UI.
- `services`: Includes injectable Angular services responsible for handling HTTP requests, business logic, and data interaction between the frontend and backend APIs.
- `app.component.ts`: The root module that imports and declares the entire application.

### dtos

This folder contains Data Transfer Object (DTO) interfaces used to define the shape of data exchanged between the frontend and backend. By using interfaces, Angular ensures strong typing, better code completion, and improved maintainability. DTOs act as a contract between both sides of the application, helping to catch inconsistencies early during development.

For example, a DTO representing a location might look like this:

```ts
export interface ILocation {
  id: number;
  country: string;
  city: string;
}
```

This interface can then be used throughout the application to ensure that any variable or response expected to be of type ILocation strictly follows this structure.

### pages

This folder contains the main Angular components that represent individual pages in the application, such as the login page, itinerary page, homepage etc. Each page is implemented as an Angular component and typically consists of an HTML template, a TypeScript class, and an SCSS stylesheet.

Components in this folder were generated using the Angular CLI with the following command:

```bash
   ng generate component pages/component-name
```

This command creates a new folder with all necessary files (.ts, .html, .css, and .spec.ts) under the pages/ directory, and automatically declares the component in the appropriate module.

The application primarily uses Bootstrap to handle styling and layout. Bootstrap is a popular CSS framework that provides a wide range of prebuilt components and utility classes for responsive design and consistent UI. Examples of Bootstrap components used include:

- Buttons (`btn`, `btn-primary`) – to create interactive elements like “Submit” or “Delete”
- Cards (`card`, `card-body`) – to display grouped content in a structured container
- Grids (`row`, `col-md-6`) – to arrange elements in a responsive layout
- Forms (`form-contro`l, `form-group`) – to style input fields and labels

Thanks to Bootstrap, all page layouts are responsive by default. The application adapts well to different screen sizes, making it usable on both desktop and mobile devices.

### Routing

Each page component in the application is typically associated with a specific route, defined in Angular’s routing module. This setup allows users to navigate between different parts of the application by changing the URL, without reloading the entire page. Angular’s RouterModule handles this efficiently using client-side routing.

Routes are defined in a dedicated routing configuration file, usually named `app.routes.ts`. Each route maps a URL path to a corresponding component. Here is an example:

```ts
export const routes: Routes = [
  { path: "", redirectTo: "/login", pathMatch: "full" },
  { path: "login", component: LoginComponent },
  { path: "homepage", component: HomepageComponent },
  { path: "signup", component: SignupComponent },
  { path: "account-page", component: AccountPageComponent },
  { path: "itinerary/:id", component: ItineraryDetailsPageComponent },
];
```

- `login` renders the LoginComponent
- `homepage` renders the HomepageComponent
- `signup` renders the SignupComponent
- `account-page` renders the AccountPageComponent
- `itinerary/:id` renders the ItineraryDetailsPageComponent
- An empty path ('') redirects to the login page

### services

In Angular, services are used to encapsulate and manage business logic, data fetching, and reusable functionality that can be shared across multiple components. Instead of duplicating logic in each component, services provide a centralized way to handle operations such as HTTP requests, state management, and utility functions.

A service in Angular is typically created using the Angular CLI command:

```bash
ng generate service service-name
```

or, more concisely:

```bash
ng g s service-name
```

Angular CLI automatically creates the following two files in the specified folder:

- `service-name.service.ts`: This is the main service file that contains the class definition. Here, you implement the logic such as HTTP requests, data manipulation, or shared functionality.
- `service-name.service.spec.ts`: This is a unit test. It’s used to test the functionality of the service.

The project includes three key services, each responsible for handling specific functionality:

- `User Service`: Responsible for managing user-related operations, such as authentication, registration, and profile management. It typically handles requests for logging in, logging out, and retrieving user information.
- `Itinerary Service`: Deals with the creation, modification, and retrieval of itineraries. This service manages the planning of trips, including the addition of destinations, dates, and other relevant information associated with a user’s journey.
- `Attraction Service`: Focuses on handling information related to attractions or points of interest. This service is responsible for fetching details about tourist spots, including their names, descriptions, locations, and other related data.

In this project, we make HTTP requests to the backend using Angular's HttpClient. Each service makes use of these HTTP calls to communicate with the backend API, sending data and receiving responses based on the application's needs.

For example, in the UserService, several HTTP methods are used to interact with the API:

- `GET Request`: This method is used to retrieve data. In this case, the service may fetch information about the logged-in user. For instance, getLoggedUserDetails makes a POST request to the backend, sending the user credentials and retrieving the user details.
- `POST Request`: This is used to send data to the server for operations like creating or logging in a user. For example, signUpUser sends user signup data to the backend API using a POST request.
- `PUT Request`: This request is used to update existing data on the server. The updateUser method uses a PUT request to update user information.
- `DELETE Request`: This is used to delete data from the backend. The deleteUser method sends a DELETE request to remove the user from the system.

Here is an example of how a service like UserService performs these API calls:

```ts
    signUpUser(credentials: IUserSignup): Observable<IUser> {
        return this.http.post<IUser>(endpointAPI + 'user/signup', credentials);
    }

    // Sends a PUT request to update user information
    updateUser(credentials: IUserSignup): Observable<IUser> {
        return this.http.put<IUser>(endpointAPI + 'user', credentials);
    }

    // Sends a DELETE request to remove a user by their email
    deleteUser(email: string): Observable<IUser> {
        return this.http.delete<IUser>(endpointAPI + 'user/' + email);
    }
```

Key Concepts:

- HttpClient: Angular's built-in service used to make HTTP requests.
- Observable: A stream of data that can be subscribed to, providing asynchronous handling of HTTP responses.
- endpointAPI: A central configuration holding the base API endpoint for all HTTP requests.

This modular approach allows each service to be responsible for a specific part of the application's functionality, making the codebase more maintainable and organized. Each service communicates with the backend via well-defined API calls, ensuring that the frontend can retrieve, update, and delete data as needed.

## Running the Application

To run the application locally, you need to start both the backend (Spring Boot) and the frontend (Angular) projects. Follow the steps below to configure and launch everything correctly:

1. Set Up the Database Connection

The backend uses PostgreSQL as its database. Before starting the Spring Boot server, you need to make sure your local PostgreSQL instance is running and a database is created for the application.

Then, open the application.yaml file in the backend project and update the database connection credentials to match your local setup:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/your_database_name
    username: your_postgres_username
    password: your_postgres_password
```

Make sure that:

- The database exists.
- The credentials are valid.
- PostgreSQL is running on port 5432 (or modify the URL accordingly if different).

2. Run the Backend (Spring Boot)

Once the database is set up, you can run the backend application using IntelliJ:
Open the project in IntelliJ IDEA.
Locate the main class (usually DemoApplication.java or similar).
Right-click the class and choose Run.

Alternatively, use the terminal:

```bash
./mvnw spring-boot:run
```

The backend will start on http://localhost:8080 by default.

3. Set Up and Run the Frontend (Angular)

Now, move to the Angular frontend project:
Open a terminal inside the frontend project folder.
Run the following command to install the required dependencies:

```bash
npm install
```

Once installation is complete, start the development server with:

```bash
ng serve -o
```

The -o flag automatically opens the app in your default browser. Angular runs on http://localhost:4200 by default.
After following these steps, the frontend should be connected to the backend, and you’ll be able to fully use the application locally.
