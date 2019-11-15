package guru.springframework.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import guru.springframework.service.IngredientService;
import guru.springframework.service.RecipeService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class IngredientController {
	
	private RecipeService recipeService;
	private IngredientService ingredientService;
	
	public IngredientController(RecipeService recipeService, IngredientService ingredientService) {
		this.recipeService = recipeService;
		this.ingredientService = ingredientService;
	}

	@GetMapping
	@RequestMapping("/recipes/{id}/ingredients")
	public String listIngredients(@PathVariable String id, Model model) {
        log.debug("Getting ingredient list for recipe id: " + id);
		model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
		return "recipes/ingredients/list";
	}
	
	@GetMapping
	@RequestMapping("/recipes/{recipeId}/ingredients/{ingredientId}/show")
	public String showIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model) {
		model.addAttribute("ingredient", 
			ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredientId)));
		return "recipes/ingredients/show";
	}
}
