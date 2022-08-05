package com.company.musicstorerecommendations.controller;

import com.company.musicstorerecommendations.model.AlbumRecommendation;
import com.company.musicstorerecommendations.model.LabelRecommendation;
import com.company.musicstorerecommendations.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/albumRecommendation")
public class AlbumRecommendationController {
    @Autowired
    RecommendationService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AlbumRecommendation createAlbumRecommendation(@RequestBody @Valid AlbumRecommendation albumRecommendation) {
        albumRecommendation = service.createAlbumRecommendation(albumRecommendation);
        return albumRecommendation;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AlbumRecommendation> getAllAlbumRecommendations() {
        List<AlbumRecommendation> albumRecommendationList = service.getAllAlbumRecommendation();
        return albumRecommendationList;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AlbumRecommendation getAlbumRecommendation(@PathVariable("id") Integer id) {
        AlbumRecommendation albumRecommendation = service.getAlbumRecommendation(id);
        if(albumRecommendation==null) {
            throw new IllegalArgumentException("Album recommendation could not be retrieved for id " + id);
        } else {
            return albumRecommendation;
        }
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAlbumRecommendation(@RequestBody @Valid AlbumRecommendation albumRecommendation) {
        if (albumRecommendation==null || albumRecommendation.getId() <1) {
            throw new IllegalArgumentException("Check id in model");
        } else if (albumRecommendation.getId() >0) {
            service.updateAlbumRecommendation(albumRecommendation);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAlbumRecommendation(@PathVariable("id") Integer id) {service.deleteAlbumRecommendation(id);}

}
