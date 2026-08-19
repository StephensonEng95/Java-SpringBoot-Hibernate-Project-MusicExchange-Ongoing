package com.musicexchange.service;

import com.musicexchange.dto.ArtistRequestDto;
import com.musicexchange.models.Artist;
import com.musicexchange.models.UserRole;
import com.musicexchange.repository.ArtistRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArtistServiceTest {

    @Mock
    private ArtistRepository artistRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private ArtistService artistService;
    UserRole role= UserRole.ARTIST;
    @Test
    void createArtist_Success() {
        // Arrange
        String user = "SteveTest";
        String email = "steve@test.com";
        String pass = "rawPassword";
        when(artistRepo.existsByUsername(user)).thenReturn(false);
        when(artistRepo.existsByEmail(email)).thenReturn(false);
        when(passwordEncoder.encode(pass)).thenReturn("hashedPassword123");

        when(artistRepo.save(any(Artist.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ArtistRequestDto artistRequestDto = new ArtistRequestDto(user, email, pass, role);

        // Act
        artistService.createArtist(artistRequestDto);

        // Assert
        verify(artistRepo, times(1)).save(any(Artist.class));
    }

    @Test
    void createArtist_ThrowsException_WhenUsernameExists() {
        // Arrange
        String user = "ExistingUser";

        when(artistRepo.existsByUsername(user)).thenReturn(true);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            artistService.createArtist(new ArtistRequestDto(user, "test1@test1.com", "dummypass123.",role));
        });

        assertEquals("Artist username already exists: " + user, exception.getMessage());
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
            artistService.createArtist(new ArtistRequestDto(user, emailExists, "pass124", role));
        });

        assertEquals("Artist email already exists: " + emailExists, runtimeException.getMessage());
        verify(artistRepo, never()).save(any(Artist.class));
    }
}