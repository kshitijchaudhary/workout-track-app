package ca.lambton.workout_trackapp.service;

import ca.lambton.workout_trackapp.entity.Workout;
import ca.lambton.workout_trackapp.repository.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WorkoutService {

    @Autowired
    private WorkoutRepository workoutRepository;

    // Save or update workout
    public Workout saveWorkout(Workout workout) {
        return workoutRepository.save(workout);
    }

    // Get all workouts
    public List<Workout> getAllWorkouts() {
        return workoutRepository.findAll();
    }

    // Get workout by ID
    public Optional<Workout> getWorkoutById(Long id) {
        return workoutRepository.findById(id);
    }

    // Delete workout by ID
    public void deleteWorkoutById(Long id) {
        workoutRepository.deleteById(id);
    }

    // Progress Tracking: Calculate stats for progress
    public Map<String, Object> calculateProgress() {
        List<Workout> workouts = getAllWorkouts();

        // Total workout time
        int totalDuration = workouts.stream()
                .mapToInt(Workout::getDuration)
                .sum();

        // Most frequent workout type
        String mostFrequentType = workouts.stream()
                .collect(Collectors.groupingBy(Workout::getCategory, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("None");

        // Prepare stats
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalDuration", totalDuration);
        stats.put("mostFrequentType", mostFrequentType);
        stats.put("totalWorkouts", workouts.size());

        return stats;
    }

    // Export Workouts to CSV
    public String exportWorkoutsToCsv() {
        String filePath = "workouts_export.csv"; // Output file
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("Workout Name,Duration,Intensity,Date,Category\n");
            for (Workout workout : getAllWorkouts()) {
                writer.write(String.format("%s,%d,%s,%s,%s\n",
                        workout.getName(),
                        workout.getDuration(),
                        workout.getIntensity(),
                        workout.getDate(),
                        workout.getCategory()));
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle exception
        }
        return filePath;
    }

    // Import Workouts from CSV
    public void importWorkoutsFromCSV(MultipartFile file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length == 5) {
                    Workout workout = new Workout(
                            fields[0], // Workout Name
                            Integer.parseInt(fields[1]), // Duration
                            fields[2], // Intensity
                            LocalDate.parse(fields[3]), // Date
                            fields[4]  // Category
                    );
                    saveWorkout(workout);
                } else {
                    throw new IOException("Invalid CSV format at line: " + line);  // Better error handling
                }
            }
        }
    }
}
