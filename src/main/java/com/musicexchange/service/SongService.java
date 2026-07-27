package com.musicexchange.service;

import com.musicexchange.dto.SongRequestDto;
import com.musicexchange.dto.SongResponseDto;
import com.musicexchange.exceptions.DuplicateResourceException;
import com.musicexchange.exceptions.ResourceNotFoundException;
import com.musicexchange.kafka.ArtistAddedSongEvent;
import com.musicexchange.kafka.ArtistAddedSongProducer;
import com.musicexchange.models.Artist;
import com.musicexchange.models.Song;
import com.musicexchange.repository.ArtistRepository;
import com.musicexchange.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SongService {

    private final SongRepository songRepo;
    private final ArtistRepository artistRepo;
    private final ArtistAddedSongProducer artistAddedSongProducer;


    @Transactional
    public SongResponseDto addSong(SongRequestDto request) {
        Artist artist = artistRepo.findById(request.getArtistId())
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found with ID: " + request.getArtistId()));

        if (songRepo.existsBySongTitleAndArtist(request.getTitle(), artist)) {
            throw new DuplicateResourceException("Song with title '" + request.getTitle() + "' already exists for this artist");
        }

        Song song = new Song();
        song.setSongTitle(request.getTitle());
        song.setGenre(request.getGenre());
        song.setArtist(artist);

        Song savedSong = songRepo.save(song);

        ArtistAddedSongEvent event = new ArtistAddedSongEvent(
                null,
                artist.getArtistId(),
                savedSong.getSongId()
        );
        artistAddedSongProducer.publish(event);

        return mapToResponseDto(savedSong);
    }

    @Transactional(readOnly = true)
    public List<SongResponseDto> getAllSongs() {
        return songRepo.findAll()
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public SongResponseDto getSongById(Long songId) {
        Song song = songRepo.findById(songId)
                .orElseThrow(() -> new ResourceNotFoundException("Song not found with ID: " + songId));
        return mapToResponseDto(song);
    }

    public SongResponseDto updateSongByIdAndTitle(Long songId, SongRequestDto request) {
        Song song = songRepo.findById(songId)
                .orElseThrow(() -> new ResourceNotFoundException("Song not found with ID: " + songId));

        if ( songRepo.existsBySongTitleAndArtist(request.getTitle(), song.getArtist())) {
            throw new DuplicateResourceException("Song with title '" + request.getTitle() + "' already exists for this artist" + song.getArtist().getArtistId());
        }

        if(!song.getSongTitle().equalsIgnoreCase(request.getTitle())){
            throw new ResourceNotFoundException("Invalid song entered" + request.getTitle());
        }

        song.setSongTitle(request.getTitle());
        song.setGenre(request.getGenre());

        return mapToResponseDto(songRepo.save(song));
    }

    @Transactional(readOnly = true)
    public List<SongResponseDto> getSongsByArtist(Long artistId) {
        Artist artist = artistRepo.findById(artistId)
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found with ID: " + artistId));

        return songRepo.findByArtist(artist)
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    public void deleteSong(Long songId) {
        if (!songRepo.existsById(songId)) {
            throw new ResourceNotFoundException("Delete failed: Song with ID " + songId + " does not exist.");
        }
        songRepo.deleteById(songId);
    }

    private SongResponseDto mapToResponseDto(Song song) {
        Long artistId = (song.getArtist()!=null)? song.getArtist().getArtistId():null;

        return new SongResponseDto(
                song.getSongId(),
                song.getSongTitle(),
                song.getGenre(),
                song.getCoverArt(),
                song.getDuration(),
                song.getReleasedAt(),
                artistId
        );
    }
}