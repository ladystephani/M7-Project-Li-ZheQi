package com.company.musicstorerecommendations.controller;

import com.company.musicstorerecommendations.model.LabelRecommendation;
import com.company.musicstorerecommendations.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/labelRecommendation")
public class LabelRecommendationController {
    @Autowired
    RecommendationService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LabelRecommendation createLabelRecommendation(@RequestBody @Valid LabelRecommendation labelRecommendation) {
        labelRecommendation = service.createLabelRecommendation(labelRecommendation);
        return labelRecommendation;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<LabelRecommendation> getAllLabelRecommendations() {
        List<LabelRecommendation> labelRecommendationList = service.getAllLabelRecommendation();
        return labelRecommendationList;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LabelRecommendation getLabelRecommendation(@PathVariable("id") Integer id) {
        LabelRecommendation labelRecommendation = service.getLabelRecommendation(id);
        if(labelRecommendation==null) {
            throw new IllegalArgumentException("Label recommendation could not be retrieved for id " + id);
        } else {
            return labelRecommendation;
        }
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateLabelRecommendation(@RequestBody @Valid LabelRecommendation labelRecommendation) {
        if (labelRecommendation ==null || labelRecommendation.getId() <1) {
            throw new IllegalArgumentException("Check id in model");
        } else if (labelRecommendation.getId() >0) {
            service.updateLabelRecommendation(labelRecommendation);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLabelRecommendation(@PathVariable("id") Integer id) {service.deleteLabelRecommendation(id);}

}
