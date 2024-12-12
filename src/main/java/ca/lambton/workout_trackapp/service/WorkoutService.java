package ca.lambton.workout_trackapp.service;


import ca.lambton.workout_trackapp.entity.Workout;
import ca.lambton.workout_trackapp.repository.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class WorkoutService {

    @Autowired
    private WorkoutRepository workoutRepository;

    public Workout saveWorkout(Workout workout) {
        return workoutRepository.save(workout);
    }

    public List<Workout> getAllWorkouts() {
        return workoutRepository.findAll();
    }
}