package com.musicexchange.service;

import com.musicexchange.dto.ArtistRequestDto;
import com.musicexchange.dto.ArtistResponseDto;
import com.musicexchange.exceptions.DuplicateResourceException;
import com.musicexchange.exceptions.ResourceNotFoundException;
import com.musicexchange.models.Artist;
import com.musicexchange.repository.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArtistService {

    private final ArtistRepository artistRepo;
    private final PasswordEncoder passwordEncoder;


    @Transactional(readOnly = true)
    public List<ArtistResponseDto> getAllArtists() {
        return artistRepo.findAll()
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public ArtistResponseDto getArtistById(Long artistId) {
        Artist artist = artistRepo.findById(artistId)
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found with ID: " + artistId));
        return mapToResponseDto(artist);
    }

    public ArtistResponseDto createArtist(ArtistRequestDto requestDto) {
        if (artistRepo.existsByUsername(requestDto.getUsername())) {
            throw new DuplicateResourceException("Artist username already exists: " + requestDto.getUsername());
        }
        if (artistRepo.existsByEmail(requestDto.getEmail())) {
            throw new DuplicateResourceException("Artist email already exists: " + requestDto   .getEmail());
        }

        Artist artist = new Artist();
        artist.setUsername(requestDto.getUsername());
        artist.setEmail(requestDto.getEmail());
        artist.setPassword(passwordEncoder.encode(requestDto.getPassword()));

        Artist savedArtist = artistRepo.save(artist);
        return mapToResponseDto(savedArtist);
    }

    public ArtistResponseDto updateArtistByEmail(Long artistId, String email) {
        Artist artist = artistRepo.findById(artistId)
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found with ID: " + artistId));

        artist.setEmail(email);
        Artist updatedArtist = artistRepo.save(artist);
        return mapToResponseDto(updatedArtist);
    }

    public void updateArtistByPassword(Long artistId, String rawPassword) {
        Artist artist = artistRepo.findById(artistId)
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found with ID: " + artistId));

        artist.setPassword(passwordEncoder.encode(rawPassword));
        artistRepo.save(artist);
    }

    public void deleteArtistById(Long artistId) {
        if (!artistRepo.existsById(artistId)) {
            throw new ResourceNotFoundException("Artist not found with ID: " + artistId);
        }
        artistRepo.deleteById(artistId);
    }

    private ArtistResponseDto mapToResponseDto(Artist artist) {
        return new ArtistResponseDto(
                artist.getArtistId(),
                artist.getUsername(),
                artist.getEmail(),
                artist.getProfilePicture(),
                artist.getCreatedAt(),
                artist.getUpdatedAt()
        );
    }
}