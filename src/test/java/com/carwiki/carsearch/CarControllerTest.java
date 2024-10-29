package com.carwiki.carsearch;

import com.carwiki.carsearch.controller.CarController;
import com.carwiki.carsearch.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CarControllerTest {

    @Mock
    private CarService carService;

    @InjectMocks
    private CarController carController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(carController).build();
    }

    @Test
    void testAddCar_Success() throws Exception {
        when(carService.addCars(any())).thenReturn("Successfully added the cars.");

        mockMvc.perform(post("/api/cars/addCars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[{\"id\": 1, \"brand\": \"Honda\", \"model\": \"Civic\", \"url\": \"sampleUrl\"}]"))
                .andExpect(status().isCreated())
                .andExpect(content().string("Successfully added the cars."));
    }

    @Test
    void testAddCar_Failure() throws Exception {
        // Simulate a failure in the service layer
        when(carService.addCars(any())).thenThrow(new RuntimeException("Failed to add cars"));

        mockMvc.perform(post("/api/cars/addCars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[{\"id\": 1, \"brand\": \"Honda\", \"model\": \"Civic\", \"url\": \"sampleUrl\"}]"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Failed to add cars"));
    }
}