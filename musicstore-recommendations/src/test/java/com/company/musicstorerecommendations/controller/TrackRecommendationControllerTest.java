package com.company.musicstorerecommendations.controller;

import com.company.musicstorerecommendations.model.AlbumRecommendation;
import com.company.musicstorerecommendations.model.TrackRecommendation;
import com.company.musicstorerecommendations.service.RecommendationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TrackRecommendationController.class)
public class TrackRecommendationControllerTest {
    @MockBean
    private RecommendationService service;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUp() throws Exception {
        setupCatalogServiceMock();
    }

    private void setupCatalogServiceMock() {
        TrackRecommendation trackRecommendation = new TrackRecommendation(1,1,1,true);
        TrackRecommendation trackRecommendationWithoutId = new TrackRecommendation(1,1,true);
        List<TrackRecommendation> trackRecommendations = Arrays.asList(trackRecommendation);

        doReturn(trackRecommendations).when(service).getAllTrackRecommendation();
        doReturn(trackRecommendation).when(service).getTrackRecommendation(1);
        doReturn(trackRecommendation).when(service).createTrackRecommendation(trackRecommendationWithoutId);
        doNothing().when(service).updateTrackRecommendation(trackRecommendation);
        doNothing().when(service).deleteTrackRecommendation(1);
    }

    @Test
    public void getAllTrackRecommendationShouldReturnAListAnd200() throws Exception {
        TrackRecommendation trackRecommendation = new TrackRecommendation(1,1,1,true);
        List<TrackRecommendation> trackRecommendations = Arrays.asList(trackRecommendation);
        String expectedJsonValue = mapper.writeValueAsString(trackRecommendations);

        mockMvc.perform(get("/trackRecommendation"))
                .andDo(print())
                .andExpect(status().isOk()) //assert
                .andExpect(content().json(expectedJsonValue));
    }

    @Test
    public void getTrackRecommendationByIdShouldReturnRecommendationAnd200() throws Exception {
        TrackRecommendation savedTrackRecommendation = new TrackRecommendation(1,1,1,true);
        String savedRecommendationJson = mapper.writeValueAsString(savedTrackRecommendation);

        mockMvc.perform(get("/trackRecommendation/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(savedRecommendationJson));
    }

    @Test
    public void createTrackRecommendationShouldReturnNewRecommendationAndStatus201() throws Exception {
        TrackRecommendation inputTrackRecommendation = new TrackRecommendation(1,1,true);
        TrackRecommendation outputTrackRecommendation = new TrackRecommendation(1,1,1,true);
        String inputRecommendationJson = mapper.writeValueAsString(inputTrackRecommendation);
        String outputRecommendationJson  = mapper.writeValueAsString(outputTrackRecommendation);

        mockMvc.perform(
                        post("/trackRecommendation")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(inputRecommendationJson)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputRecommendationJson));
    }

    @Test
    public void updateTrackRecommendationShouldReturnUpdatedRecommendationAndStatus204() throws Exception {
        TrackRecommendation inputTrackRecommendation = new TrackRecommendation(1,1,1,true);
        TrackRecommendation outputTrackRecommendation = new TrackRecommendation(1,1,1,true);
        String inputRecommendationJson = mapper.writeValueAsString(inputTrackRecommendation);

        mockMvc.perform(
                        put("/trackRecommendation")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(inputRecommendationJson)
                )
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteTrackShouldDeleteAndReturnStatus204() throws Exception {
        TrackRecommendation inputTrackRecommendation = new TrackRecommendation(1,1,1,true);

        mockMvc.perform(delete("/trackRecommendation/{id}", 1))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

}