package ca.lambton.workout_trackapp.controller;

import ca.lambton.workout_trackapp.entity.Workout;
import ca.lambton.workout_trackapp.service.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;  // Import BindingResult
import jakarta.validation.Valid;  // Import @Valid
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.time.LocalDate;

@Controller
public class WorkoutController {
    private static int pageHits = 0;


    @Autowired
    private WorkoutService workoutService;


    @GetMapping("/")
    public String home(Model model) {
        pageHits++; // Increment the page hits count each time the homepage is accessed
        model.addAttribute("pageHits", pageHits); // Add pageHits to the model
        return "index"; // This will render the home page (index.html)
    }


    @GetMapping("/form")
    public String addWorkout(Model model) {
        model.addAttribute("workout", new Workout());
        return "form";  // Return the form view
    }

    @PostMapping("/save-workout")
    public String saveWorkout(@Valid @ModelAttribute Workout workout, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("workout", workout);  // Pass the workout object with validation errors
            return "form";
        }

        // Ensure that the workout date is not in the future
        if (workout.getDate().isAfter(LocalDate.now())) {
            bindingResult.rejectValue("date", "error.workout", "The workout date cannot be in the future.");
            model.addAttribute("workout", workout);
            return "form";  // Return the form with the validation error
        }

        // Save the workout if validation passes
        workoutService.saveWorkout(workout);
        return "redirect:/workouts";  // Redirect to the workout list after saving
    }



    // Show the list of workouts
    @GetMapping("/workouts")
    public String showWorkouts(Model model) {
        model.addAttribute("workouts", workoutService.getAllWorkouts());
        return "list";  // Return to the list view
    }

    @GetMapping("/edit-workout/{id}")
    public String editWorkout(@PathVariable("id") Long id, Model model) {
        Workout workout = workoutService.getWorkoutById(id);  // Get the workout by id
        if (workout == null) {
            return "redirect:/workouts";  // Handle null or non-existent workout
        }

        // Log the date to ensure it's passed correctly
        System.out.println("Workout date: " + workout.getDate());

        model.addAttribute("workout", workout);  // Add the workout to the model
        return "editform";  // Return the name of the view
    }

    @PostMapping("/update-workout")
    public String updateWorkout(@ModelAttribute Workout workout) {
        // Save the updated workout data
        workoutService.saveWorkout(workout);

        // Redirect back to the list of workouts after saving
        return "redirect:/workouts";
    }

    @GetMapping("/delete-workout/{id}")
    public String deleteWorkout(@PathVariable("id") Long id) {
        // Delete the workout by id
        workoutService.deleteWorkoutById(id);

        // Redirect back to the workouts list page
        return "redirect:/workouts";
    }

    @GetMapping("/progress")
    public String showProgress(Model model) {
        // Add progress stats to the model
        model.addAttribute("progressStats", workoutService.calculateProgress());
        return "progress"; // Return to the progress tracking view
    }

    @GetMapping("/export")
    public ResponseEntity<Resource> exportWorkouts() throws IOException {
        // Generate the CSV file for export
        String filePath = workoutService.exportWorkoutsToCsv();
        FileSystemResource resource = new FileSystemResource(filePath);

        // Return the file as a downloadable response
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @PostMapping("/import")
    public String importWorkouts(@RequestParam("file") MultipartFile file) {
        try {
            // Import workouts from the CSV file
            workoutService.importWorkoutsFromCSV(file);
        } catch (IOException e) {
            e.printStackTrace(); // Handle file import errors
        }

        // Redirect back to the workouts list after import
        return "redirect:/workouts";
    }
}
