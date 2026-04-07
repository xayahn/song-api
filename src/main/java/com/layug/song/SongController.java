package com.layug.song;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/layug/songs")
public class SongController {

    @Autowired
    private SongRepository songRepository;

    /**
     * GET /layug/songs
     * Retrieve all songs
     */
    @GetMapping
    public List<Song> getAllSongs() {
        return songRepository.findAll();
    }

    /**
     * POST /layug/songs
     * Add a new song
     */
    @PostMapping
    public Song addSong(@RequestBody Song song) {
        return songRepository.save(song);
    }

    /**
     * GET /layug/songs/{id}
     * Retrieve a single song by ID
     */
    @GetMapping("/{id}")
    public Song getSongById(@PathVariable Long id) {
        return songRepository.findById(id).orElse(null);
    }

    /**
     * PUT /layug/songs/{id}
     * Update an existing song by ID
     */
    @PutMapping("/{id}")
    public Song updateSong(@PathVariable Long id, @RequestBody Song songDetails) {
        return songRepository.findById(id).map(song -> {
            song.setTitle(songDetails.getTitle());
            song.setArtist(songDetails.getArtist());
            song.setAlbum(songDetails.getAlbum());
            song.setGenre(songDetails.getGenre());
            song.setUrl(songDetails.getUrl());
            return songRepository.save(song);
        }).orElse(null);
    }

    /**
     * DELETE /layug/songs/{id}
     * Delete a song by ID
     */
    @DeleteMapping("/{id}")
    public String deleteSong(@PathVariable Long id) {
        songRepository.deleteById(id);
        return "Song with ID " + id + " deleted.";
    }

    /**
     * GET /layug/songs/search/{query}
     * Search songs across title, artist, album, and genre fields
     */
    @GetMapping("/search/{query}")
    public List<Song> searchSongs(@PathVariable String query) {
        return songRepository.findByTitleContainingIgnoreCaseOrArtistContainingIgnoreCaseOrAlbumContainingIgnoreCaseOrGenreContainingIgnoreCase(
                query, query, query, query
        );
    }
}