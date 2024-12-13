package ca.lambton.workout_trackapp.service;

import ca.lambton.workout_trackapp.entity.Workout;
import ca.lambton.workout_trackapp.repository.WorkoutRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Optional;

public class WorkoutServiceTest {

    @Mock
    private WorkoutRepository workoutRepository;

    @InjectMocks
    private WorkoutService workoutService;

    private Workout workout;

    @BeforeEach
    public void setup() {
        workout = new Workout("Push-up", 20, "Medium", LocalDate.now(), "Strength");
    }

    @Test
    public void testSaveWorkout() {
        // Mock the save method to return the same workout object
        when(workoutRepository.save(workout)).thenReturn(workout);

        Workout savedWorkout = workoutService.saveWorkout(workout);

        assertThat(savedWorkout).isNotNull();
        assertThat(savedWorkout.getName()).isEqualTo("Push-up");
        verify(workoutRepository, times(1)).save(workout);
    }

    @Test
    public void testGetWorkoutById() {
        // Mock the repository to return the workout when findById is called
        when(workoutRepository.findById(workout.getId())).thenReturn(Optional.of(workout));

        Optional<Workout> foundWorkout = workoutService.getWorkoutById(workout.getId());

        assertThat(foundWorkout).isPresent();
        assertThat(foundWorkout.get().getName()).isEqualTo("Push-up");
        verify(workoutRepository, times(1)).findById(workout.getId());
    }

    @Test
    public void testDeleteWorkoutById() {
        // Mocking the delete method does not need to return anything, but we want to verify if it's called
        doNothing().when(workoutRepository).deleteById(workout.getId());

        workoutService.deleteWorkoutById(workout.getId());

        verify(workoutRepository, times(1)).deleteById(workout.getId());
    }

    @Test
    public void testGetAllWorkouts() {
        // Test to check if all workouts are fetched correctly
        when(workoutRepository.findAll()).thenReturn(List.of(workout));

        List<Workout> workouts = workoutService.getAllWorkouts();

        assertThat(workouts).hasSize(1);
        assertThat(workouts.get(0).getName()).isEqualTo("Push-up");
        verify(workoutRepository, times(1)).findAll();
    }

    @Test
    public void testUpdateWorkout() {
        // Assume that workout exists and is updated
        workout.setDuration(30); // Modify the workout to simulate update
        when(workoutRepository.save(workout)).thenReturn(workout);

        Workout updatedWorkout = workoutService.saveWorkout(workout);

        assertThat(updatedWorkout.getDuration()).isEqualTo(30);
        verify(workoutRepository, times(1)).save(workout);
    }
}
