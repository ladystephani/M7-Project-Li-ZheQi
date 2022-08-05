package com.company.musicstorerecommendations.controller;

import com.company.musicstorerecommendations.model.LabelRecommendation;
import com.company.musicstorerecommendations.model.TrackRecommendation;
import com.company.musicstorerecommendations.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/trackRecommendation")
public class TrackRecommendationController {
    @Autowired
    RecommendationService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TrackRecommendation createTrackRecommendation(@RequestBody @Valid TrackRecommendation trackRecommendation) {
        trackRecommendation = service.createTrackRecommendation(trackRecommendation);
        return trackRecommendation;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TrackRecommendation> getAllTrackRecommendations() {
        List<TrackRecommendation> trackRecommendationList = service.getAllTrackRecommendation();
        return trackRecommendationList;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TrackRecommendation getTrackRecommendation(@PathVariable("id") Integer id) {
        TrackRecommendation trackRecommendation = service.getTrackRecommendation(id);
        if (trackRecommendation == null) {
            throw new IllegalArgumentException("Track recommendation could not be retrieved for id " + id);
        } else {
            return trackRecommendation;
        }
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTrackRecommendation(@RequestBody @Valid TrackRecommendation trackRecommendation) {
        if (trackRecommendation ==null || trackRecommendation.getId() <1) {
            throw new IllegalArgumentException("Check id in model");
        } else if (trackRecommendation.getId() > 0) {
            service.updateTrackRecommendation(trackRecommendation);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTrackRecommendation(@PathVariable("id") Integer id) {service.deleteTrackRecommendation(id);}

}
