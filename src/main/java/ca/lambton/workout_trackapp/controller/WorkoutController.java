package ca.lambton.workout_trackapp.controller;


import ca.lambton.workout_trackapp.entity.Workout;
import ca.lambton.workout_trackapp.service.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

import jakarta.validation.Valid;

@Controller
public class WorkoutController {

    @Autowired
    private WorkoutService workoutService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/form")
    public String addWorkout() {
        return "form";
    }

    @PostMapping("/save-workout")
    public String saveWorkout(@RequestParam String workoutName,
                              @RequestParam int duration,
                              @RequestParam String intensity,
                              @RequestParam String date) {
        LocalDate workoutDate = LocalDate.parse(date);  // Convert string to LocalDate
        Workout workout = new Workout(workoutName, duration, intensity, workoutDate);
        workoutService.saveWorkout(workout);
        return "redirect:/workouts";
    }

    @GetMapping("/workouts")
    public String showWorkouts(Model model) {
        model.addAttribute("workouts", workoutService.getAllWorkouts());
        return "list";
    }
}