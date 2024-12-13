package ca.lambton.workout_trackapp.repository;

import ca.lambton.workout_trackapp.entity.Workout;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDate;

@DataJpaTest
public class WorkoutRepositoryTest {

    @Autowired
    private WorkoutRepository workoutRepository;

    private Workout workout;

    @BeforeEach
    public void setup() {
        workout = new Workout("Push-up", 20, "Medium", LocalDate.now(), "Strength");
    }

    @Test
    public void testSaveWorkout() {
        Workout savedWorkout = workoutRepository.save(workout);
        assertThat(savedWorkout).isNotNull();
        assertThat(savedWorkout.getId()).isGreaterThan(0);
    }

    @Test
    public void testFindWorkoutById() {
        workoutRepository.save(workout);
        Workout foundWorkout = workoutRepository.findById(workout.getId()).orElse(null);
        assertThat(foundWorkout).isNotNull();
        assertThat(foundWorkout.getName()).isEqualTo(workout.getName());
    }

    @Test
    public void testDeleteWorkout() {
        workoutRepository.save(workout);
        Long workoutId = workout.getId();
        workoutRepository.deleteById(workoutId);

        Workout deletedWorkout = workoutRepository.findById(workoutId).orElse(null);
        assertThat(deletedWorkout).isNull();
    }
}
