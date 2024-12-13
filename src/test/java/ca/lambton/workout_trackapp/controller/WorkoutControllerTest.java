package ca.lambton.workout_trackapp.controller;

import ca.lambton.workout_trackapp.service.WorkoutService;
import ca.lambton.workout_trackapp.entity.Workout;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class WorkoutControllerTest {

    @MockBean
    private WorkoutService workoutService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testSaveWorkout() throws Exception {
        Workout workout = new Workout("Push-up", 30, "High", LocalDate.now(), "Strength");
        when(workoutService.saveWorkout(any(Workout.class))).thenReturn(workout);

        mockMvc.perform(post("/save-workout")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", "Push-up")
                        .param("duration", "30")
                        .param("intensity", "High")
                        .param("date", LocalDate.now().toString())
                        .param("category", "Strength"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/workouts"));

        verify(workoutService, times(1)).saveWorkout(any(Workout.class));
    }
}
