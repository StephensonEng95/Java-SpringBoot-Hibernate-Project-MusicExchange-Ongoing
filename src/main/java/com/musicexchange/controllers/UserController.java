package com.musicexchange.controllers;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.musicexchange.models.Artist;
import com.musicexchange.models.Fan;
import com.musicexchange.models.UserRole;
import com.musicexchange.service.ArtistService;
import com.musicexchange.service.FanService;

import jakarta.servlet.http.HttpSession;

/**
 * User controller handling artist and fan authentication
 */
@Controller
@RequestMapping("/user")
public class UserController {
	private final ArtistService artistService;
	private final FanService fanService; 

	public UserController(ArtistService artistService, FanService fanService) {
		this.artistService = artistService;
		this.fanService = fanService;
	}
 
	@GetMapping("/home")
	public String home(Model model) {
		model.addAttribute("message", "welcome to MusicExchange");
		return("home");
	}

	@PostMapping("/signup")
	public String processSignup(@RequestParam String username, @RequestParam String email,
			                   @RequestParam String password, @RequestParam UserRole role, Model model) 
	{

		try {
			// Validate username
			if (username == null || username.isEmpty()) {
				model.addAttribute("error", "Username is required");
				return "signup";
			}

			// Validate email
			if (email == null || !email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
				model.addAttribute("error", "Please enter a valid email address");
				return "signup";
			}

			// Validate password
			String passwordRegex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
			if (password == null || !password.matches(passwordRegex)) {
				model.addAttribute("error",
						"Password must be at least 8 characters long, contain at least one uppercase letter, one number, and one special character (@$!%*?&)");
				return "signup";
			}

			// Artist creation post succesful validation
			if (role == UserRole.ARTIST) {
				artistService.createArtist(username, email, password);
				return "redirect:/artist/dashboard";
			}
			// Fan creation post successful validation
			else if (role == UserRole.FAN) {
				fanService.createFan(username, email, password);
				return "redirect:/fan/dashboard";
			} else {
				model.addAttribute("error", "Please select a valid role");
				return "signup";
			}

		} catch (Exception e) {
			model.addAttribute("error", "Error during signup: " + e.getMessage());
			model.addAttribute("role", role); // Preserve selected role
			return "signup";
		}
	}
    
	@GetMapping("/signup")
	public String signUpPage() {
		return "signup";
	}

	@PostMapping("/login")
	public String toLogin(@RequestParam String username, 
			              @RequestParam String password, 
			              HttpSession session,
			              Model model) {

		try {
			// Use of artistService's authenticate method
			Optional<Artist> artist = artistService.authenticateArtist(username, password);
			if (artist.isPresent()) {
				session.setAttribute("user", artist.get());
				session.setAttribute("userType", "ARTIST");
				
				return "redirect:/artist/dashboard";
			}

			// Use of fanService's authenticate method
			Optional<Fan> fan = fanService.authenticateFan(username, password);
			if (fan.isPresent()) {
				session.setAttribute("user", fan.get());
				session.setAttribute("userType", "FAN");
				return "redirect:/fan/dashboard";
			}

			// Login failed
			model.addAttribute("error", "Invalid username or password");
			return "login";

		} catch (Exception e) {
			model.addAttribute("error", "Login failed: " + e.getMessage());
			return "login";
		}
	}

	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}

	@PutMapping("/updateartistemail")
	public String updateArtistEmail(Long id, String email, Model model) {
		artistService.updateArtistEmail(id, email);
		model.addAttribute("Artist email updated successfully '" + email);
        return "redirect:/artist/dashboard";
    }

	@DeleteMapping("/updateartistpassword")
	public String updateArtistPassword(Long id, String password, Model model) {
		artistService.updateArtistPassword(id, password);
		model.addAttribute( "Artist email updated successfully '" + password);
        return "redirect:/artist/dashboard";

	}
}
