package guru.springframework.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import guru.springframework.command.RecipeCommand;
import guru.springframework.exceptions.NotFoundException;
import guru.springframework.service.RecipeService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class RecipeController {

	private  RecipeService recipeService;
	
	public RecipeController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}
	
	@GetMapping("/recipes/{id}/show")
	public String showById(@PathVariable String id, Model model) {
		model.addAttribute("recipe", recipeService.findById(Long.valueOf(id)));
		return "recipes/show";
	}
	
	@GetMapping("/recipes/new")
	public String newRecipe(Model model) {
		model.addAttribute("recipe", new RecipeCommand());
		return "recipes/recipeform";
	}
	
	@GetMapping("/recipes/{id}/update")
	public String updateRecipe(@PathVariable String id, Model model) {
		model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
		return "recipes/recipeform";
	}

	@PostMapping("/recipes")
	public String saveOrUpdate (@ModelAttribute RecipeCommand command) {
		RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);
		return "redirect:/recipes/" + savedCommand.getId() + "/show";
	}
	
	@GetMapping("/recipes/{id}/delete")
	public String deleteRecipe (@PathVariable String id) {
		recipeService.deleteById(Long.valueOf(id));
		return "redirect:/";
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	public ModelAndView handleNotFound(Exception exception) {
		log.error("Handling Not Found Exception");
		log.error(exception.getMessage());
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("404error");
		modelAndView.addObject("exception", exception);

		return modelAndView;
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(NumberFormatException.class)
	public ModelAndView handleNumberFormat(Exception exception) {
		log.error("Handling Number Format Exception.");
		log.error(exception.getMessage());
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("400error");
		modelAndView.addObject("exception", exception);
		
		return modelAndView;
	}
}
