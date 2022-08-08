package com.company.musicstorerecommendations.controller;

import com.company.musicstorerecommendations.model.LabelRecommendation;
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
@WebMvcTest(LabelRecommendationController.class)
public class LabelRecommendationControllerTest {
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
        LabelRecommendation labelRecommendation = new LabelRecommendation(1,1,1,true);
        LabelRecommendation labelRecommendationWithoutId = new LabelRecommendation(1,1,true);
        List<LabelRecommendation> labelRecommendations = Arrays.asList(labelRecommendation);

        doReturn(labelRecommendations).when(service).getAllLabelRecommendation();
        doReturn(labelRecommendation).when(service).getLabelRecommendation(1);
        doReturn(labelRecommendation).when(service).createLabelRecommendation(labelRecommendationWithoutId);
        doNothing().when(service).updateLabelRecommendation(labelRecommendation);
        doNothing().when(service).deleteLabelRecommendation(1);
    }

    @Test
    public void getAllLabelRecommendationShouldReturnAListAnd200() throws Exception {
        LabelRecommendation labelRecommendation = new LabelRecommendation(1,1,1,true);
        List<LabelRecommendation> labelRecommendations = Arrays.asList(labelRecommendation);
        String expectedJsonValue = mapper.writeValueAsString(labelRecommendations);

        mockMvc.perform(get("/labelRecommendation"))
                .andDo(print())
                .andExpect(status().isOk()) //assert
                .andExpect(content().json(expectedJsonValue));
    }

    @Test
    public void getLabelRecommendationByIdShouldReturnRecommendationAnd200() throws Exception {
        LabelRecommendation savedLabelRecommendation = new LabelRecommendation(1,1,1,true);
        String savedRecommendationJson = mapper.writeValueAsString(savedLabelRecommendation);

        mockMvc.perform(get("/labelRecommendation/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(savedRecommendationJson));
    }

    @Test
    public void createLabelRecommendationShouldReturnNewRecommendationAndStatus201() throws Exception {
        LabelRecommendation inputLabelRecommendation = new LabelRecommendation(1,1,true);
        LabelRecommendation outputLabelRecommendation = new LabelRecommendation(1,1,1,true);
        String inputLabelRecommendationJson = mapper.writeValueAsString(inputLabelRecommendation);
        String outputLabelRecommendationJson  = mapper.writeValueAsString(outputLabelRecommendation);

        mockMvc.perform(
                        post("/labelRecommendation")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(inputLabelRecommendationJson)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputLabelRecommendationJson));
    }

    @Test
    public void updateLabelRecommendationShouldReturnUpdatedRecommendationAndStatus204() throws Exception {
        LabelRecommendation inputLabelRecommendation = new LabelRecommendation(1,1,1,true);
        LabelRecommendation outputLabelRecommendation = new LabelRecommendation(1,1,1,false);
        String inputLabelRecommendationJson = mapper.writeValueAsString(inputLabelRecommendation);

        mockMvc.perform(
                        put("/labelRecommendation")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(inputLabelRecommendationJson)
                )
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteLabelShouldDeleteAndReturnStatus204() throws Exception {
        LabelRecommendation inputLabelRecommendation = new LabelRecommendation(1,1,1,true);
        mockMvc.perform(delete("/labelRecommendation/{id}", 1))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

}