package com.company.musicstorerecommendations.controller;

import com.company.musicstorerecommendations.model.ArtistRecommendation;
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

import java.util.ArrayList;
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
@WebMvcTest(ArtistRecommendationController.class)
public class ArtistRecommendationControllerTest {
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
        ArtistRecommendation artistRecommendation = new ArtistRecommendation(1, 1, 1, true);
        ArtistRecommendation artistRecommendationWithoutId = new ArtistRecommendation(1, 1, true);
        List<ArtistRecommendation> artistRecommendationList = Arrays.asList(artistRecommendation);

        doReturn(artistRecommendationList).when(service).getAllArtistRecommendation();
        doReturn(artistRecommendation).when(service).getArtistRecommendation(1);
        doReturn(artistRecommendation).when(service).createArtistRecommendation(artistRecommendationWithoutId);
        doNothing().when(service).updateArtistRecommendation(artistRecommendation);
        doNothing().when(service).deleteTrackRecommendation(1);
    }

    @Test
    public void getAllArtistRecommendationShouldReturnAListAnd200() throws Exception {
        ArtistRecommendation artistRecommendation = new ArtistRecommendation(1, 1, 1, true);
        List<ArtistRecommendation> artistRecommendationList = Arrays.asList(artistRecommendation);
        String expectedJsonValue = mapper.writeValueAsString(artistRecommendationList);

        mockMvc.perform(get("/artistRecommendation"))
                .andDo(print())
                .andExpect(status().isOk()) //assert
                .andExpect(content().json(expectedJsonValue));
    }

    @Test
    public void getArtistRecommendationByIdShouldReturnArtistRecommendationAnd200() throws Exception {
        ArtistRecommendation savedArtistRecommendation = new ArtistRecommendation(1, 1, 1, true);
        String savedArtistRecommendationJson = mapper.writeValueAsString(savedArtistRecommendation);

        mockMvc.perform(get("/artistRecommendation/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(savedArtistRecommendationJson));
    }

    @Test
    public void createArtistRecommendationShouldReturnNewRecommendationAndStatus201() throws Exception {
        ArtistRecommendation inputArtistRecommendation = new ArtistRecommendation(1, 1, true);
        ArtistRecommendation outputArtistRecommendation = new ArtistRecommendation(1, 1, 1, true);
        String inputArtistRecommendationJson = mapper.writeValueAsString(inputArtistRecommendation);
        String outputArtistRecommendationJson  = mapper.writeValueAsString(outputArtistRecommendation);

        mockMvc.perform(
                        post("/artistRecommendation")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(inputArtistRecommendationJson)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputArtistRecommendationJson));
    }

    @Test
    public void updateArtistRecommendationShouldReturnUpdatedAlbumAndStatus204() throws Exception {
        ArtistRecommendation inputArtistRecommendation = new ArtistRecommendation(1,1, 1, true);
        ArtistRecommendation outputArtistRecommendation = new ArtistRecommendation(1, 1, 1, false);
        String inputArtistRecommendationJson = mapper.writeValueAsString(inputArtistRecommendation);
        mockMvc.perform(
                        put("/artistRecommendation")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(inputArtistRecommendationJson)
                )
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteArtistShouldDeleteAndReturnStatus204() throws Exception {
        ArtistRecommendation inputArtistRecommendation = new ArtistRecommendation(1,1, 1, true);
        mockMvc.perform(delete("/artistRecommendation/{id}", 1))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}