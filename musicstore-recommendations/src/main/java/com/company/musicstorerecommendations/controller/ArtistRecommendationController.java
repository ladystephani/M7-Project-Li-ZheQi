package com.company.musicstorerecommendations.controller;

import com.company.musicstorerecommendations.model.ArtistRecommendation;
import com.company.musicstorerecommendations.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/artistRecommendation")
public class ArtistRecommendationController {
    @Autowired
    RecommendationService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ArtistRecommendation createArtistRecommendation(@RequestBody @Valid ArtistRecommendation artistRecommendation) {
        artistRecommendation = service.createArtistRecommendation(artistRecommendation);
        return artistRecommendation;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ArtistRecommendation> getAllArtistRecommendations() {
        List<ArtistRecommendation> artistRecommendationList = service.getAllArtistRecommendation();
        return artistRecommendationList;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ArtistRecommendation getArtistRecommendation(@PathVariable("id") Integer id) {
        ArtistRecommendation artistRecommendation = service.getArtistRecommendation(id);
        if (artistRecommendation == null) {
            throw new IllegalArgumentException("Artist recommendation could not be retrieved for id " + id);
        } else {
            return artistRecommendation;
        }
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateArtistRecommendation(@RequestBody @Valid ArtistRecommendation artistRecommendation) {
        if (artistRecommendation ==null || artistRecommendation.getId() <1 ){
            throw new IllegalArgumentException("Check id in model");
        } else if (artistRecommendation.getId() >0) {
            service.updateArtistRecommendation(artistRecommendation);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArtistRecommendation(@PathVariable("id") Integer id) {service.deleteArtistRecommendation(id);}

}
