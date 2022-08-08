package com.company.musicstorerecommendations.controller;

import com.company.musicstorerecommendations.model.AlbumRecommendation;
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
@WebMvcTest(AlbumRecommendationController.class)
public class AlbumRecommendationControllerTest {
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
        AlbumRecommendation albumRecommendation = new AlbumRecommendation(1,1,1,true);
        AlbumRecommendation albumRecommendationWithoutId = new AlbumRecommendation(1,1,true);
        List<AlbumRecommendation> albumRecommendations = Arrays.asList(albumRecommendation);

        doReturn(albumRecommendations).when(service).getAllAlbumRecommendation();
        doReturn(albumRecommendation).when(service).getAlbumRecommendation(1);
        doReturn(albumRecommendation).when(service).createAlbumRecommendation(albumRecommendationWithoutId);
        doNothing().when(service).updateAlbumRecommendation(albumRecommendation);
        doNothing().when(service).deleteAlbumRecommendation(1);
    }

    @Test
    public void getAllAlbumRecommendationShouldReturnAListAnd200() throws Exception {
        AlbumRecommendation albumRecommendation = new AlbumRecommendation(1,1,1,true);
        List<AlbumRecommendation> albumRecommendations = Arrays.asList(albumRecommendation);
        String expectedJsonValue = mapper.writeValueAsString(albumRecommendations);
        mockMvc.perform(get("/albumRecommendation"))
                .andDo(print())
                .andExpect(status().isOk()) //assert
                .andExpect(content().json(expectedJsonValue));
    }

    @Test
    public void getAlbumRecommendationByIdShouldReturnRecommendationAnd200() throws Exception {
        AlbumRecommendation savedAlbumRecommendation = new AlbumRecommendation(1,1,1,true);
        String savedRecommendationJson = mapper.writeValueAsString(savedAlbumRecommendation);

        mockMvc.perform(get("/albumRecommendation/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(savedRecommendationJson));
    }

    @Test
    public void createAlbumRecommendationShouldReturnNewRecommendationAndStatus201() throws Exception {
        AlbumRecommendation inputAlbumRecommendation = new AlbumRecommendation(1,1,true);
        AlbumRecommendation outputAlbumRecommendation = new AlbumRecommendation(1,1,1,true);
        String inputRecommendationJson = mapper.writeValueAsString(inputAlbumRecommendation);
        String outputRecommendationJson  = mapper.writeValueAsString(outputAlbumRecommendation);

        mockMvc.perform(
                        post("/albumRecommendation")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(inputRecommendationJson)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputRecommendationJson));
    }

    @Test
    public void updateAlbumRecommendationShouldReturnUpdatedRecommendationAndStatus204() throws Exception {
        AlbumRecommendation inputAlbumRecommendation = new AlbumRecommendation(1,1,1,true);
        AlbumRecommendation outputAlbumRecommendation = new AlbumRecommendation(1,1,1,true);
        String inputRecommendationJson = mapper.writeValueAsString(inputAlbumRecommendation);

        mockMvc.perform(
                        put("/albumRecommendation")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(inputRecommendationJson)
                )
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteAlbumShouldDeleteAndReturnStatus204() throws Exception {
        AlbumRecommendation inputAlbumRecommendation = new AlbumRecommendation(1,1,1,true);
        mockMvc.perform(delete("/albumRecommendation/{id}", 1))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

}