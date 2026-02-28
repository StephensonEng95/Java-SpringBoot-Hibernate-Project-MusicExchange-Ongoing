package com.musicexchange.controllers;

import com.musicexchange.models.Artist;
import com.musicexchange.models.UserRole;
import com.musicexchange.service.ArtistService;
import com.musicexchange.service.FanService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
public class UserController {

    private final ArtistService artistService;
    private final FanService fanService;

    public UserController(ArtistService artistService, FanService fanService) {
        this.artistService = artistService;
        this.fanService = fanService;
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String username,
                               @RequestParam String password,
                               HttpSession session,
                               Model model) {

        // Use the service to check if the user exists and the password matches
        return artistService.authenticateArtist(username, password)
                .map(artist -> {
                    // If login is successful, save the artist object in the session
                    // This lets other pages know who is currently logged in
                    session.setAttribute("user", artist);
                    session.setAttribute("userType", "ARTIST");

                    log.info("Artist {} logged in", username);
                    return "redirect:/artist/dashboard";
                })
                .orElseGet(() -> {
                    // If login fails, show an error message on the login screen
                    log.warn("Login failed for: {}", username);
                    model.addAttribute("error", "Invalid username or password.");
                    return "login";
                });
    }

    @GetMapping("/artist/dashboard")
    public String artistDashboard(HttpSession session, Model model) {
        // Pull the artist out of the session
        Artist artist = (Artist) session.getAttribute("user");

        // If the session is empty, it means they aren't logged in
        if (artist == null) {
            log.debug("Unauthorized dashboard access attempt");
            return "redirect:/login";
        }

        // Add the artist to the model so we can show their name/email on the HTML page
        model.addAttribute("artist", artist);
        return "artist-dashboard";
    }

    @PostMapping("/signup")
    public String processSignup(@RequestParam String username,
                                @RequestParam String email,
                                @RequestParam String password,
                                @RequestParam UserRole role,
                                Model model) {
        try {
            // Check if the user is an Artist or a Fan and save them to the database
            if (role == UserRole.ARTIST) {
                artistService.createArtist(username, email, password);
            } else if (role == UserRole.FAN) {
                fanService.createFan(username, email, password);
            }

            log.info("New user registered: {}", username);
            return "redirect:/login?success=true";

        } catch (Exception e) {
            // Log the error if the database save fails (like a duplicate username)
            log.error("Signup failed: {}", e.getMessage());
            model.addAttribute("error", "Could not create account. Please try again.");
            return "signup";
        }
    }

    @PostMapping("/updateartistemail")
    public String updateArtistEmail(HttpSession session,
                                    @RequestParam String email,
                                    RedirectAttributes redirectAttributes) {
        // Get the artist from the session, not a hidden input field
        Artist artist = (Artist) session.getAttribute("user");

        if (artist == null) {
            return "redirect:/login";
        }

        // Use the ID from the session to update the correct user in the database
        artistService.updateArtistEmail(artist.getArtistId(), email);

        // Sync the session object so the dashboard shows the new email immediately
        artist.setEmail(email);
        session.setAttribute("user", artist);

        // Send a little success message to the dashboard
        redirectAttributes.addFlashAttribute("message", "Email updated successfully!");
        log.info("Artist {} updated their email", artist.getUsername());

        return "redirect:/artist/dashboard";
    }

    @PostMapping("/updateartistpassword")
    public String updateArtistPassword(HttpSession session,
                                       @RequestParam String password,
                                       RedirectAttributes redirectAttributes) {
        // Securely get the logged-in user's info
        Artist artist = (Artist) session.getAttribute("user");

        if (artist == null) {
            return "redirect:/login";
        }

        // Pass the ID and the new password to the service for encoding and saving
        artistService.updateArtistPassword(artist.getArtistId(), password);

        redirectAttributes.addFlashAttribute("message", "Password updated successfully!");
        log.info("Artist {} changed their password", artist.getUsername());

        return "redirect:/artist/dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // Delete all session data to log the user out safely
        session.invalidate();
        log.info("User logged out");
        return "redirect:/login?logout=true";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/signup")
    public String signUpPage() {
        return "signup";
    }
}