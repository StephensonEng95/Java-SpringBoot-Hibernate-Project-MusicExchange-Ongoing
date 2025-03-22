package com.musicexchange.musicexchange.controllers;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.musicexchange.musicexchange.models.Artist;
import com.musicexchange.musicexchange.service.ArtistService;


@Controller
//@RequestMapping("/user-api")
public class ArtistController {
	 private final ArtistService artistService;
	 private static final Logger logger=LoggerFactory.getLogger(ArtistController.class);
	 
	@Autowired
    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }
	/*@GetMapping("/artist")
	public String test() {
		return "{\"message\": \"This is a JSON response\"}";
	}*/
	
	@GetMapping("/home")
	public String home(Model model) {
		model.addAttribute("message", "Welcome to home page");
		return "home";
	}
	@GetMapping("/signup")
	public String toSignUp(Model model) {
		model.addAttribute("message", "Welcome to MusicExchange");
		return "signup";
	}
	@PostMapping("/signup")
    public String createArtist(@RequestParam String username,@RequestParam String password,@RequestParam String email,Model model) {
		Artist artist=new Artist();
		try {
			logger.debug("attempting user creation: {}",username);
        if (username== null || username.trim().isEmpty()) {
            model.addAttribute("message", "required username");
            logger.warn("username required");
            return "signup";
        }
        
        if(email==null || !email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
        	model.addAttribute("error", "enter a valid email");
        	return "signup";
        }
        String passwordRegex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        if (password == null || !password.matches(passwordRegex)) {
            model.addAttribute("error", "Password must be at least 8 characters long, contain at least one uppercase letter, one number, and one special character.");
            return "signup";
        }
        artistService.createArtist(username,email,password);
        logger.info("User created successfully: {}" + artist.getUsername());
        return "redirect:/home";
		}
		catch(Exception e) {
			//e.printStackTrace();
			logger.error("error during signup",e);
			model.addAttribute("message","Error:" + e.getMessage());
		return "signup";
		}
    }
	
   @GetMapping("/login")
   public String toLogin(Model model) {
	   return "login";
	   
   }
	

}
