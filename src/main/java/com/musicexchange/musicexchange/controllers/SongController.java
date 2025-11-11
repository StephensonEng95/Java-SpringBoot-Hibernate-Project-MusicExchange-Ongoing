package com.musicexchange.musicexchange.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.musicexchange.musicexchange.service.SongsService;

@Controller
public class SongController {
	
	@Autowired
	private  SongsService sService;
	private final Logger logger=LoggerFactory.getLogger(SongController.class);
	
	@GetMapping("/home")
	public String home(Model model) {
		model.addAttribute("message, welcome to MusicExchange");
		return("home");
	}
	
	
	
	
	
}
