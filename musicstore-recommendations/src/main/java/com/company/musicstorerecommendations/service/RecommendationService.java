package com.company.musicstorerecommendations.service;

import com.company.musicstorerecommendations.controller.AlbumRecommendationController;
import com.company.musicstorerecommendations.controller.TrackRecommendationController;
import com.company.musicstorerecommendations.model.AlbumRecommendation;
import com.company.musicstorerecommendations.model.ArtistRecommendation;
import com.company.musicstorerecommendations.model.LabelRecommendation;
import com.company.musicstorerecommendations.model.TrackRecommendation;
import com.company.musicstorerecommendations.repository.AlbumRepository;
import com.company.musicstorerecommendations.repository.ArtistRepository;
import com.company.musicstorerecommendations.repository.LabelRepository;
import com.company.musicstorerecommendations.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sound.midi.Track;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class RecommendationService {
    ArtistRepository artistRepo;
    LabelRepository labelRepo;
    AlbumRepository albumRepo;
    TrackRepository trackRepo;
    @Autowired
    public RecommendationService(
            ArtistRepository artistRepository,
            LabelRepository labelRepository,
            AlbumRepository albumRepository,
            TrackRepository trackRepository
    ) {
        this.artistRepo = artistRepository;
        this.labelRepo = labelRepository;
        this.albumRepo = albumRepository;
        this.trackRepo = trackRepository;
    }

    //Artist
    public ArtistRecommendation createArtistRecommendation(ArtistRecommendation artistRecommendation) {
        // Validate incoming Data in the view model.
        // All validations were done using JSR303
        if (artistRecommendation==null) throw new IllegalArgumentException("No artist recommendation is passed! ");

        artistRecommendation.setArtist_id(artistRecommendation.getArtist_id());
        artistRecommendation.setUser_id(artistRecommendation.getUser_id());
        artistRecommendation.setLiked(artistRecommendation.isLiked());

        artistRecommendation.setId(artistRepo.save(artistRecommendation).getId());
        return artistRecommendation;
    }

    public List<ArtistRecommendation> getAllArtistRecommendation() {
        List<ArtistRecommendation> artistRecommendationList = artistRepo.findAll();
        List<ArtistRecommendation> artistRecommendationArrayList = new ArrayList<>();

        if (artistRecommendationList==null){
            return null;
        } else {
            artistRecommendationList.stream().forEach(r -> artistRecommendationArrayList.add(buildArtistRecommendation(r)));
        }
        return artistRecommendationList;
    }

    public ArtistRecommendation getArtistRecommendation(Integer id) {
        Optional<ArtistRecommendation> artistRecommendation = artistRepo.findById(id);
        if (artistRecommendation == null) {
            return null;
        } else {
            return buildArtistRecommendation(artistRecommendation.get());
        }
    }

    public void updateArtistRecommendation(ArtistRecommendation artistRecommendation) {
        if (artistRecommendation==null) throw new IllegalArgumentException("No artist recommendation is passed! ");

        if (this.getArtistRecommendation(artistRecommendation.getId()) == null) throw new IllegalArgumentException("No such artist recommendation to update");

        artistRecommendation.setId(artistRecommendation.getId());
        artistRecommendation.setArtist_id(artistRecommendation.getArtist_id());
        artistRecommendation.setUser_id(artistRecommendation.getUser_id());
        artistRecommendation.setLiked(artistRecommendation.isLiked());

        artistRepo.save(artistRecommendation);
    }

    public void deleteArtistRecommendation(Integer id) {artistRepo.deleteById(id);}

    //label
    public LabelRecommendation createLabelRecommendation(LabelRecommendation labelRecommendation) {
        if (labelRecommendation==null) throw new IllegalArgumentException("No label recommendation is passed! ");

        labelRecommendation.setLabel_id(labelRecommendation.getLabel_id());
        labelRecommendation.setUser_id(labelRecommendation.getUser_id());
        labelRecommendation.setLiked(labelRecommendation.isLiked());

        labelRecommendation.setId(labelRepo.save(labelRecommendation).getId());
        return labelRecommendation;
    }

    public List<LabelRecommendation> getAllLabelRecommendation() {
        List<LabelRecommendation> labelRecommendations = labelRepo.findAll();
        List<LabelRecommendation> labelRecommendationArrList = new ArrayList<>();

        if (labelRecommendations == null) {
            return null;
        } else {
            labelRecommendations.stream().forEach(r -> labelRecommendationArrList.add(buildLabelRecommendation(r)));
        }
        return labelRecommendations;
    }

    public LabelRecommendation getLabelRecommendation(Integer id) {
        Optional<LabelRecommendation> labelRecommendation = labelRepo.findById(id);
        if (labelRecommendation == null) {
            return null;
        } else {
            return buildLabelRecommendation(labelRecommendation.get());
        }
    }

    public void updateLabelRecommendation(LabelRecommendation labelRecommendation) {
        if (labelRecommendation==null) throw new IllegalArgumentException("No label recommendation is passed");

        if (this.getLabelRecommendation(labelRecommendation.getId()) == null) throw new IllegalArgumentException("No such label recommendation to update");

        labelRecommendation.setId(labelRecommendation.getId());
        labelRecommendation.setLabel_id(labelRecommendation.getLabel_id());
        labelRecommendation.setUser_id(labelRecommendation.getUser_id());
        labelRecommendation.setLiked(labelRecommendation.isLiked());

        labelRepo.save(labelRecommendation);
    }

    public void deleteLabelRecommendation(Integer id) {labelRepo.deleteById(id);}

    //album
    public AlbumRecommendation createAlbumRecommendation(AlbumRecommendation albumRecommendation) {
        if (albumRecommendation==null) throw new IllegalArgumentException("No album recommendation is passed! ");

        albumRecommendation.setAlbum_id(albumRecommendation.getAlbum_id());
        albumRecommendation.setUser_id(albumRecommendation.getUser_id());
        albumRecommendation.setLiked(albumRecommendation.isLiked());

        albumRecommendation.setId(albumRepo.save(albumRecommendation).getId());
        return albumRecommendation;
    }

    public List<AlbumRecommendation> getAllAlbumRecommendation() {
        List<AlbumRecommendation> albumRecommendations = albumRepo.findAll();
        List<AlbumRecommendation> albumRecommendationArrList = new ArrayList<>();

        if (albumRecommendations ==null) {
            return null;
        } else {
            albumRecommendations.stream().forEach(r -> albumRecommendationArrList.add(buildAlbumRecommendation(r)));
        }
        return albumRecommendations;
    }


    public AlbumRecommendation getAlbumRecommendation(Integer id) {
        Optional<AlbumRecommendation> albumRecommendation = albumRepo.findById(id);
        if (albumRecommendation ==null) {
            return null;
        } else {
            return buildAlbumRecommendation(albumRecommendation.get());
        }
    }

    public void updateAlbumRecommendation(AlbumRecommendation albumRecommendation) {
        if (albumRecommendation==null) throw new IllegalArgumentException("No album recommendation is passed");

        if (this.getAlbumRecommendation(albumRecommendation.getId()) == null) throw new IllegalArgumentException("No such album recommendation to update");

        albumRecommendation.setId(albumRecommendation.getId());
        albumRecommendation.setAlbum_id(albumRecommendation.getAlbum_id());
        albumRecommendation.setUser_id(albumRecommendation.getUser_id());
        albumRecommendation.setLiked(albumRecommendation.isLiked());

        albumRepo.save(albumRecommendation);
    }

    public void deleteAlbumRecommendation(Integer id) {albumRepo.deleteById(id);}

    //track
    public TrackRecommendation createTrackRecommendation(TrackRecommendation trackRecommendation) {
        if (trackRecommendation == null) throw new IllegalArgumentException("No album recommendation is passed");

        trackRecommendation.setTrack_id(trackRecommendation.getTrack_id());
        trackRecommendation.setUser_id(trackRecommendation.getUser_id());
        trackRecommendation.setLiked(trackRecommendation.isLiked());

        trackRecommendation.setId(trackRepo.save(trackRecommendation).getId());
        return trackRecommendation;
    }

    public List<TrackRecommendation> getAllTrackRecommendation() {
        List<TrackRecommendation> trackRecommendations = trackRepo.findAll();
        List<TrackRecommendation> trackRecommendationArrList = new ArrayList<>();

        if (trackRecommendations == null) {
            return null;
        } else {
            trackRecommendations.stream().forEach(r -> trackRecommendationArrList.add(buildTrackRecommendation(r)));
        }
        return trackRecommendations;
    }

    public TrackRecommendation getTrackRecommendation(Integer id) {
        Optional<TrackRecommendation> trackRecommendation = trackRepo.findById(id);
        if (trackRecommendation == null) {
            return null;
        } else {
            return buildTrackRecommendation(trackRecommendation.get());
        }
    }

    public void updateTrackRecommendation(TrackRecommendation trackRecommendation) {
        if (trackRecommendation == null) throw new IllegalArgumentException("No track recommendation is passed");

        if (this.getTrackRecommendation(trackRecommendation.getId())==null) throw new IllegalArgumentException("No such track recommendation to update");

        trackRecommendation.setId(trackRecommendation.getId());
        trackRecommendation.setTrack_id(trackRecommendation.getTrack_id());
        trackRecommendation.setUser_id(trackRecommendation.getUser_id());
        trackRecommendation.setLiked(trackRecommendation.isLiked());

        trackRepo.save(trackRecommendation);
    }

    public void deleteTrackRecommendation(Integer id) {trackRepo.deleteById(id);}

    //helper
    public ArtistRecommendation buildArtistRecommendation(ArtistRecommendation artistRecommendation) {
        artistRecommendation.setId(artistRecommendation.getId());
        artistRecommendation.setArtist_id(artistRecommendation.getArtist_id());
        artistRecommendation.setUser_id(artistRecommendation.getUser_id());
        artistRecommendation.setLiked(artistRecommendation.isLiked());
        return artistRecommendation;
    }

    public LabelRecommendation buildLabelRecommendation(LabelRecommendation labelRecommendation) {
        labelRecommendation.setId(labelRecommendation.getId());
        labelRecommendation.setLabel_id(labelRecommendation.getLabel_id());
        labelRecommendation.setUser_id(labelRecommendation.getUser_id());
        labelRecommendation.setLiked(labelRecommendation.isLiked());
        return labelRecommendation;
    }

    public AlbumRecommendation buildAlbumRecommendation(AlbumRecommendation albumRecommendation) {
        albumRecommendation.setId(albumRecommendation.getId());
        albumRecommendation.setAlbum_id(albumRecommendation.getAlbum_id());
        albumRecommendation.setUser_id(albumRecommendation.getUser_id());
        albumRecommendation.setLiked(albumRecommendation.isLiked());
        return albumRecommendation;
    }

    public TrackRecommendation buildTrackRecommendation(TrackRecommendation trackRecommendation) {
        trackRecommendation.setId(trackRecommendation.getId());
        trackRecommendation.setTrack_id(trackRecommendation.getTrack_id());
        trackRecommendation.setUser_id(trackRecommendation.getUser_id());
        trackRecommendation.setLiked(trackRecommendation.isLiked());
        return trackRecommendation;
    }
}
