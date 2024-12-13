package ca.lambton.workout_trackapp.service;

import ca.lambton.workout_trackapp.entity.Workout;
import ca.lambton.workout_trackapp.repository.WorkoutRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDate;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class WorkoutServiceTest {

    @Mock
    private WorkoutRepository workoutRepository;

    @InjectMocks
    private WorkoutService workoutService;

    private Workout workout;

    @BeforeEach
    public void setUp() {
        workout = new Workout();
        workout.setId(1L);
        workout.setName("Push-up");
        workout.setDuration(30);
        workout.setIntensity("Medium");
        workout.setDate(LocalDate.now());
        workout.setCategory("Strength");
    }

    @Test
    public void testSaveWorkout() {
        when(workoutRepository.save(workout)).thenReturn(workout);

        // Test saveWorkout
        Workout savedWorkout = workoutService.saveWorkout(workout);

        assertThat(savedWorkout).isNotNull();
        assertThat(savedWorkout.getName()).isEqualTo("Push-up");

        verify(workoutRepository, times(1)).save(workout);
    }

    @Test
    public void testGetAllWorkouts() {
        List<Workout> workouts = Collections.singletonList(workout);
        when(workoutRepository.findAll()).thenReturn(workouts);

        // Test getAllWorkouts
        List<Workout> result = workoutService.getAllWorkouts();

        assertThat(result).contains(workout);
        verify(workoutRepository, times(1)).findAll();
    }

    @Test
    public void testGetWorkoutById() {
        when(workoutRepository.findById(workout.getId())).thenReturn(Optional.of(workout));

        // Test getWorkoutById
        Optional<Workout> foundWorkout = workoutService.getWorkoutById(workout.getId());

        assertThat(foundWorkout).isPresent();
        assertThat(foundWorkout.get().getName()).isEqualTo("Push-up");

        verify(workoutRepository, times(1)).findById(workout.getId());
    }

    @Test
    public void testDeleteWorkoutById() {
        workoutService.deleteWorkoutById(workout.getId());

        // Verify deleteById interaction
        verify(workoutRepository, times(1)).deleteById(workout.getId());
    }

    @Test
    public void testCalculateProgress() {
        List<Workout> workouts = Arrays.asList(workout, new Workout("Squat", 20, "High", LocalDate.now(), "Strength"));
        when(workoutRepository.findAll()).thenReturn(workouts);

        // Test calculateProgress
        Map<String, Object> progress = workoutService.calculateProgress();

        assertThat(progress).containsKey("totalDuration");
        assertThat(progress).containsKey("mostFrequentType");
        assertThat(progress).containsKey("totalWorkouts");

        verify(workoutRepository, times(1)).findAll();
    }
}
