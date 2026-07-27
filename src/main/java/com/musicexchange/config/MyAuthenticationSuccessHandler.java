package com.musicexchange.config;

import com.musicexchange.dto.ArtistResponseDto;
import com.musicexchange.dto.FanResponseDto;
import com.musicexchange.repository.ArtistRepository;
import com.musicexchange.repository.FanRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final ArtistRepository artistRepository;
    private final FanRepository fanRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String username = authentication.getName();
        HttpSession session = request.getSession();

        var artistOpt = artistRepository.findByUsername(username);
        if (artistOpt.isPresent()) {
            var artist = artistOpt.get();
            var artistDto = new ArtistResponseDto(
                    artist.getArtistId(),
                    artist.getUsername(),
                    artist.getEmail(),
                    artist.getProfilePicture(),
                    artist.getCreatedAt(),
                    artist.getUpdatedAt()

            );
            session.setAttribute("user", artistDto);
            response.sendRedirect("/artist/dashboard");
            return;
        }

        var fanOpt = fanRepository.findByUsername(username);
        if (fanOpt.isPresent()) {
            var fan = fanOpt.get();
            var fanDto = new FanResponseDto(
                    fan.getFanId(),
                    fan.getUsername(),
                    fan.getEmail(),
                    fan.getProfilePicture(),
                    fan.getCreatedAt(),
                    fan.getUpdatedAt()
            );
            session.setAttribute("user", fanDto);
            response.sendRedirect("/fan/dashboard");
            return;
        }

        response.sendRedirect("/login?error");
    }
}