package guru.springframework.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import guru.springframework.service.RecipeService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class IndexController {
	
	private final RecipeService recipeService;
	
	public IndexController(RecipeService recipeService) {
		this.recipeService= recipeService;
	}
	
	@GetMapping({"", "/", "/index"})
	public String getIndexPage(Model model) { 
		log.debug("getIndexPage()");
		model.addAttribute("recipes", recipeService.getRecipes());
		
		return "index"; 
	}
}
