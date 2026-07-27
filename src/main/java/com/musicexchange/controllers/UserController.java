package com.musicexchange.controllers;

import com.musicexchange.dto.ArtistRequestDto;
import com.musicexchange.dto.ArtistResponseDto;
import com.musicexchange.dto.FanResponseDto;
import com.musicexchange.dto.SuggestedArtistsResponseDto;
import com.musicexchange.models.UserRole;
import com.musicexchange.service.ArtistService;
import com.musicexchange.service.FanService;
import com.musicexchange.service.SuggestedArtistsService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final ArtistService artistService;
    private final FanService fanService;
    private final SuggestedArtistsService suggestedArtistsService;
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/signup")
    public String signUpPage(Model model) {
        model.addAttribute("requestDto", new ArtistRequestDto());
        return "signup";
    }

    @PostMapping("/signup")
    public String processSignup(@Valid @ModelAttribute("requestDto") ArtistRequestDto requestDto,
                                BindingResult bindingResult,
                                @RequestParam UserRole role,
                                RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "signup";
        }

        boolean isArtist = (role == UserRole.ARTIST);

        if (isArtist) {
            artistService.createArtist(requestDto);
        } else {
            fanService.createFan(requestDto);
        }

        redirectAttributes.addFlashAttribute("message", "Account created successfully! Please log in.");
        return "redirect:/login";
    }

    @GetMapping("/artist/dashboard")
    @PreAuthorize("hasRole('ARTIST')")
    public String artistDashboard(HttpSession session, Model model) {
        Object user = session.getAttribute("user");

        if (user instanceof ArtistResponseDto artist) {
            ArtistResponseDto currentArtist = artistService.getArtistById(artist.artistId());
            model.addAttribute("artist", currentArtist);
            return "artist-dashboard";
        }

        return "redirect:/login";
    }

    @GetMapping("/fan/dashboard")
    @PreAuthorize("hasRole('FAN')")
    public String fanDashboard(HttpSession session, Model model) {
        Object user = session.getAttribute("user");

        if (user instanceof FanResponseDto fan) {
            FanResponseDto currentFan = fanService.getFanById(fan.id());
            model.addAttribute("fan", currentFan);

            List<SuggestedArtistsResponseDto> suggestedArtists = suggestedArtistsService.getAllSuggestedArtists()
                    .stream()
                    .map(artist -> new SuggestedArtistsResponseDto(
                            artist.id(),
                            artist.username(),
                            artist.profilePic(),
                            artist.genre()
                    ))
                    .toList();
            model.addAttribute("artists", suggestedArtists);

            return "fan-dashboard";
        }

        return "redirect:/login";
    }

    @PostMapping("/user/update-email")
    @PreAuthorize("isAuthenticated()")
    public String updateEmail(HttpSession session,
                              @RequestParam String email,
                              RedirectAttributes redirectAttributes) {
        Object sessionUser = session.getAttribute("user");

        if (sessionUser instanceof ArtistResponseDto artist) {
            ArtistResponseDto updatedArtist = artistService.updateArtistByEmail(artist.artistId(), email);
            session.setAttribute("user", updatedArtist);
            redirectAttributes.addFlashAttribute("message", "Email updated successfully!");
            return "redirect:/artist/dashboard";
        }

        if (sessionUser instanceof FanResponseDto fan) {
            FanResponseDto updatedFan = fanService.updateFanByEmail(fan.id(), email);
            session.setAttribute("user", updatedFan);
            redirectAttributes.addFlashAttribute("message", "Email updated successfully!");
            return "redirect:/fan/dashboard";
        }

        return "redirect:/login";
    }

    @PostMapping("/user/update-password")
    @PreAuthorize("isAuthenticated()")
    public String updatePassword(HttpSession session,
                                 @RequestParam String password,
                                 RedirectAttributes redirectAttributes) {
        Object sessionUser = session.getAttribute("user");

        if (sessionUser instanceof ArtistResponseDto artist) {
            artistService.updateArtistByPassword(artist.artistId(), password);
            redirectAttributes.addFlashAttribute("message", "Password updated successfully!");
            return "redirect:/artist/dashboard";
        }

        if (sessionUser instanceof FanResponseDto fan) {
            fanService.updateFanByPassword(fan.id(), password);
            redirectAttributes.addFlashAttribute("message", "Password updated successfully!");
            return "redirect:/fan/dashboard";
        }

        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login?logout=true";
    }
}