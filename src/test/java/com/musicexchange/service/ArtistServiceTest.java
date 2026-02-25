package com.musicexchange.service;

import com.musicexchange.models.Artist;
import com.musicexchange.repository.ArtistRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArtistServiceTest {

    @Mock
    private ArtistRepository artistRepo;

    @InjectMocks
    private ArtistService artistService;

    @Test
    void createArtist_Success() {
        // Data arrange
        String user = "SteveTest";
        String email = "steve@test.com";
        String pass = "rawPassword";

        when(artistRepo.existsByUsername(user)).thenReturn(false);
        when(artistRepo.existsByEmail(email)).thenReturn(false);

        // Act - Calling the void method
        artistService.createArtist(user, email, pass);

        // Assert - Verifying that save was actually called since there is no return value
        verify(artistRepo, times(1)).save(any(Artist.class));
    }

    @Test
    void createArtist_ThrowsException_WhenUsernameExists() {
        // Arrange
        String user = "ExistingUser";
        when(artistRepo.existsByUsername(user)).thenReturn(true);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            artistService.createArtist(user, "test1@test1.com", "pass");
        });

        // Verifying the message matches the "Artist username already exists." from your service
        assertEquals("Artist username already exists.", exception.getMessage());
        verify(artistRepo, never()).save(any(Artist.class));
    }

    @Test
    void createArtist_ThrowsException_WhenEmailExists() {
        // Arrange
        String user = "newuser";
        String emailExists = "existingemail@mail.com";

        when(artistRepo.existsByUsername(user)).thenReturn(false);
        when(artistRepo.existsByEmail(emailExists)).thenReturn(true);

        // Act & Assert
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
            artistService.createArtist(user, emailExists, "pass124");
        });

        // Verifying the message matches the "Artist email already exists." from your service
        assertEquals("Artist email already exists.", runtimeException.getMessage());
        verify(artistRepo, never()).save(any(Artist.class));
    }
}