package ca.lambton.workout_trackapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Min;  // For setting minimum value
import jakarta.validation.constraints.PastOrPresent;
import java.time.LocalDate;


@Entity
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Name is required")
    private String name;

    @Positive(message = "Duration must be positive")
    @Min(value = 1, message = "Duration must be at least 1 minute")  // Min 1 to prevent 0 and negative values
    private int duration;

    @NotEmpty(message = "Intensity is required")
    private String intensity;

    @Column(nullable = false)
    @PastOrPresent(message = "Date cannot be in the future")
    private LocalDate date;

    @NotEmpty(message = "Category is required")
    private String category;

    public Workout() {}

    public Workout(String name, int duration, String intensity, LocalDate date, String category) {
        this.name = name;
        this.duration = duration;
        this.intensity = intensity;
        this.date = date;
        this.category = category;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getIntensity() {
        return intensity;
    }

    public void setIntensity(String intensity) {
        this.intensity = intensity;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
