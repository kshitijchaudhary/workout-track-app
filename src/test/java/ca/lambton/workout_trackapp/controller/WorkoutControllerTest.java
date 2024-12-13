package ca.lambton.workout_trackapp.controller;

import ca.lambton.workout_trackapp.entity.Workout;
import ca.lambton.workout_trackapp.service.WorkoutService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.time.LocalDate;

public class WorkoutControllerTest {

    private MockMvc mockMvc;

    @Mock
    private WorkoutService workoutService;

    @InjectMocks
    private WorkoutController workoutController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(workoutController).build();
    }

    @Test
    public void testSaveWorkout() throws Exception {
        Workout workout = new Workout("Push-up", 20, "Medium", LocalDate.now(), "Strength");

        mockMvc.perform(post("/save-workout")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", workout.getName())
                        .param("duration", String.valueOf(workout.getDuration()))
                        .param("intensity", workout.getIntensity())
                        .param("date", workout.getDate().toString())
                        .param("category", workout.getCategory()))
                .andExpect(status().is3xxRedirection()) // Expecting a redirect after success
                .andExpect(redirectedUrl("/workouts"));

        verify(workoutService, times(1)).saveWorkout(any(Workout.class)); // Verify if the save method was called
    }

    @Test
    public void testShowWorkouts() throws Exception {
        mockMvc.perform(get("/workouts"))
                .andExpect(status().isOk())
                .andExpect(view().name("list")); // Expect the "list" view to be rendered
    }

    @Test
    public void testEditWorkout() throws Exception {
        Long workoutId = 1L;
        Workout workout = new Workout("Push-up", 20, "Medium", LocalDate.now(), "Strength");

        when(workoutService.getWorkoutById(workoutId)).thenReturn(workout);

        mockMvc.perform(get("/edit-workout/{id}", workoutId))
                .andExpect(status().isOk())
                .andExpect(model().attribute("workout", workout)) // Ensure workout attribute is added to the model
                .andExpect(view().name("editform")); // Expect the "editform" view
    }

    @Test
    public void testDeleteWorkout() throws Exception {
        Long workoutId = 1L;

        mockMvc.perform(get("/delete-workout/{id}", workoutId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/workouts"));

        verify(workoutService, times(1)).deleteWorkoutById(workoutId);
    }
}
