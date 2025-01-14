package com.smart.home;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		
		String formattedDate = sdf.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		return "home";
	}
	
}
