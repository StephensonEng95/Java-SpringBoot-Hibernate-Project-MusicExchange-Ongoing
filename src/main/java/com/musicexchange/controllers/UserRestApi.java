package com.musicexchange.controllers;

import com.musicexchange.dto.*;
import com.musicexchange.models.UserRole;
import com.musicexchange.service.ArtistService;
import com.musicexchange.service.FanService;
import com.musicexchange.service.SuggestedArtistsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/user/v1")
@RequiredArgsConstructor
public class UserRestApi {

    private final ArtistService artistService;
    private final FanService fanService;
    private final SuggestedArtistsService suggestedArtistsService;

    @GetMapping("/artist/{artistId}")
    public ResponseEntity<ArtistResponseDto> getArtist(@PathVariable("artistId") Long id) {
        ArtistResponseDto artist = artistService.getArtistById(id);
        return ResponseEntity.ok(artist);
    }


    @GetMapping("/fan/{fanId}")
    public ResponseEntity<FanResponseDto> getFan(@PathVariable("fanId") Long id) {
        FanResponseDto fan = fanService.getFanById(id);
        return ResponseEntity.ok(fan);
    }

    @GetMapping("fan/current-artists")
    public ResponseEntity<List<ArtistResponseDto>> getAllArtists() {
        List<ArtistResponseDto> artists = artistService.getAllArtists();
        return ResponseEntity.ok(artists);
    }

    //this class is mainly for testing artists populated on fan dashboard
    @GetMapping("fan/suggested-artists")
    public ResponseEntity<List<SuggestedArtistsResponseDto>> getAllSuggestedArtists() {
        List<SuggestedArtistsResponseDto> artists = suggestedArtistsService.getAllSuggestedArtists();
        return ResponseEntity.ok(artists);
    }

    @PostMapping("/artist-signup")
    public ResponseEntity<ArtistResponseDto> artistSignup(@Valid @RequestBody ArtistRequestDto requestDto) {
        ArtistResponseDto artistResponseDto;
        if(requestDto.getRole() == UserRole.ARTIST) {
            artistResponseDto = artistService.createArtist(requestDto);
            return ResponseEntity.ok(artistResponseDto);

        }
        return ResponseEntity.badRequest().build();
    }


    @PostMapping("/fan-signup")
    public ResponseEntity<FanResponseDto> fanSignup(@Valid @RequestBody FanRequestDto fanRequestDto){
        if (fanRequestDto.getRole() == UserRole.FAN) {
            FanResponseDto fanResponseDto = fanService.createFan(fanRequestDto);
            //if (object2 instanceof FanResponseDto fan) {
            return ResponseEntity.ok(fanResponseDto);
            //}
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }
}

    /*@PostMapping("/login")
    public ResponseEntity<ArtistResponseDto> userLogin(@Valid @RequestBody ArtistRequestDto requestDto){
            ArtistResponseDto artistResponseDto;


            }*/
