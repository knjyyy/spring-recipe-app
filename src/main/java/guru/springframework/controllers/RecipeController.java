package guru.springframework.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import guru.springframework.command.RecipeCommand;
import guru.springframework.service.RecipeService;

@Controller
public class RecipeController {

	private  RecipeService recipeService;
	
	public RecipeController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}
	
	@RequestMapping("/recipes/{id}/show")
	public String showById(@PathVariable String id, Model model) {
		model.addAttribute("recipe", recipeService.findById(Long.valueOf(id)));
		return "recipes/show";
	}
	
	@RequestMapping("/recipes/new")
	public String newRecipe(Model model) {
		model.addAttribute("recipe", new RecipeCommand());
		return "recipes/recipeform";
	}
	
	@RequestMapping("/recipes/{id}/update")
	public String updateRecipe(@PathVariable String id, Model model) {
		model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
		return "recipes/recipeform";
	}

	@PostMapping
	@RequestMapping("/recipes")
	public String saveOrUpdate (@ModelAttribute RecipeCommand command) {
		RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);
		return "redirect:/recipes/" + savedCommand.getId() + "/show";
	}
	
	@RequestMapping("/recipes/{id}/delete")
	public String deleteRecipe (@PathVariable String id) {
		recipeService.deleteById(Long.valueOf(id));
		return "redirect:/";
	}
}
