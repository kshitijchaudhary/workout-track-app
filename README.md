# Workout Tracking Application

## Overview
This Spring MVC application allows users to track their workouts, monitor progress, and manage their fitness journey. It provides features for adding, editing, and deleting workouts, as well as viewing progress statistics and exporting/importing workout data.

## Features
1. Add new workouts with details like name, duration, intensity, date, and category.
2. View a list of all workouts.
3. Edit and delete existing workouts.
4. Track progress with statistics on total workouts, duration, and most frequent workout type.
5. Export workouts to CSV format.
6. Import workouts from CSV files.
7. Responsive design for various screen sizes.

## Screenshots

### Home Page
![Home Page](screenshots/homepage.png)

### Add Workout Form
![Add Workout Form](screenshots/add-page.png)

### Workout List
![Workout List](screenshots/workout-list.png)

### Progress Tracking
![Progress Tracking](screenshots/progress-tracking.png)

### Export
![Export](screenshots/export-workouts.png)

### Import
![Import](screenshots/importing-csv-file.png)


## Project Structure

### Main Application
- `ca.lambton.workOutTrackApplication`: Main Spring Boot application class.

### Controllers
- `ca.lambton.workout_trackapp.controller.WorkoutController`: Handles all workout-related HTTP requests.

### Services
- `ca.lambton.workout_trackapp.service.WorkoutService`: Contains business logic for workout operations.

### Repositories
- `ca.lambton.workout_trackapp.repository.WorkoutRepository`: JPA repository for database operations.

### Entities
- `ca.lambton.workout_trackapp.entity.Workout`: JPA entity representing a workout.

### Views (Thymeleaf Templates)
- `index.html`: Home page with navigation and import functionality.
- `form.html`: Form for adding new workouts.
- `list.html`: Displays list of all workouts.
- `editform.html`: Form for editing existing workouts.
- `progress.html`: Shows progress statistics.
- `export.html`: Confirmation page for successful export.

### Static Resources
- `css/styles.css`: Contains all CSS styles for the application.

### Configuration
- `application.properties`: Contains database and other configuration settings.

## Technologies Used
- Spring Boot 3.1.6
- Spring MVC
- Spring Data JPA
- Thymeleaf
- H2 Database
- HTML/CSS
- JUnit 5 for testing

## Setup and Running
1. Ensure you have Java 17 and Maven installed.
2. Clone the repository.
3. Navigate to the project directory.
4. Run `mvn spring-boot:run` to start the application.
5. Open a web browser and go to `http://localhost:8080`.

## Testing
The application includes unit tests for the controller, service, and repository layers.
Run tests using: `mvn test`

## Additional Libraries Used
1. OpenCSV: Used for exporting and importing workout data in CSV format.
    - Reason: Simplifies reading and writing CSV files, which is crucial for the import/export functionality.

2. Lombok: Used to reduce boilerplate code in Java classes.
    - Reason: Enhances code readability and reduces the amount of repetitive code needed for getters, setters, etc.

## Notes
- The application uses an in-memory H2 database, which resets on application restart.
- The page hit counter is reset when the server restarts.

## Future Enhancements
- Implement user authentication and authorization.
- Add more detailed progress tracking and visualization features.
- Integrate with external fitness APIs for additional data.


# Requirements Implementation

1. Spring MVC Application:
    - Implemented throughout the project, main class: `ca.lambton.workOutTrackApplication`

2. Form Page:
    - File: `src/main/resources/templates/form.html`
    - Contains fields for name, duration, intensity, date, and category (more than 3 fields)

3. Server-side Validation:
    - File: `ca.lambton.workout_trackapp.entity.Workout`
    - Uses Jakarta Validation annotations for each field

4. Form Data Persistence:
    - File: `ca.lambton.workout_trackapp.service.WorkoutService`
    - Method: `saveWorkout()`

5. List Page with Thymeleaf:
    - File: `src/main/resources/templates/list.html`
    - Displays all workouts using Thymeleaf

6. GET Params Filtering:
    - Not implemented in the current version

7. Page Hits API:
    - File: `ca.lambton.workout_trackapp.controller.WorkoutController`
    - Method: `home()` increments and returns page hits
    - Asynchronous call every 3 seconds: Not implemented in the current version

8. Dependency Injection:
    - `WorkoutService` injected in `WorkoutController`
    - `WorkoutRepository` injected in `WorkoutService`

9. Aesthetically Pleasing Website:
    - CSS file: `src/main/resources/static/css/styles.css`

10. Unit Tests:
    - Files:
        - `ca.lambton.workout_trackapp.controller.WorkoutControllerTest`
        - `ca.lambton.workout_trackapp.repository.WorkoutRepositoryTest`
        - `ca.lambton.workout_trackapp.service.WorkoutServiceTest`

## Bonus Library

1. OpenCSV:
    - Used in `WorkoutService` for exporting and importing workouts
    - Chosen for its simplicity in handling CSV operations

## Screenshots

### Project Setup
![Project Setup](screenshots/creating-maven-app.png)

### Running spring application
![Running spring application](screenshots/running-app.png)

### Running Tests
![Running Tests](screenshots/running-tests.png)

### Code Validation
![Code Validation](screenshots/validation-form1.png)
![Code Validation](screenshots/validation-form-no-future-date.png)
![Code Validation](screenshots/validation-form2.png)
![Code Validation](screenshots/page-hit-counter.png)


### Code Snippets
![Workout Entity](screenshots/workout-entity.png)
![Workout Controller](screenshots/workout-controller.png)

![Workout Repository](screenshots/workout-repository.png)

![Workout Service](screenshots/workout-service.png)

![Workout Main App](screenshots/workout-main-app.png)

![Index Page](screenshots/index.png)

![List Page](screenshots/list.png)

![Form](screenshots/form.png)

![Edit Form](screenshots/edit-form.png)

![Export](screenshots/export.png)

![Progress](screenshots/progress.png)

![Workout Controller Test](screenshots/workout-controller-test.png)

![Workout Repository Test](screenshots/workout-repository-test.png)

![Workout Service Test](screenshots/workout-service-test.png)

![POM.xml](screenshots/pom.xml.png)



