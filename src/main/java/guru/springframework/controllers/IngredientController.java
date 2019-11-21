package guru.springframework.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import guru.springframework.command.IngredientCommand;
import guru.springframework.command.RecipeCommand;
import guru.springframework.command.UnitOfMeasureCommand;
import guru.springframework.service.IngredientService;
import guru.springframework.service.RecipeService;
import guru.springframework.service.UomService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class IngredientController {
	
	private final RecipeService recipeService;
	private final IngredientService ingredientService;
	private final UomService uomService;
	
	public IngredientController(RecipeService recipeService, 
			IngredientService ingredientService,
			UomService uomService) {
		this.recipeService = recipeService;
		this.ingredientService = ingredientService;
		this.uomService = uomService;
	}

	@GetMapping("/recipes/{id}/ingredients")
	public String listIngredients(@PathVariable String id, Model model) {
        log.debug("Getting ingredient list for recipe id: " + id);
		model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
		return "recipes/ingredients/list";
	}
	
	@GetMapping("/recipes/{recipeId}/ingredients/{ingredientId}/show")
	public String showIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model) {
		model.addAttribute("ingredient", 
			ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredientId)));
		return "recipes/ingredients/show";
	}
	
	@GetMapping("/recipes/{recipeId}/ingredients/new")
	public String newIngredient(@PathVariable String recipeId, Model model) {
		//raise exception when null
		RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));
		
		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setRecipeId(recipeCommand.getId());
		ingredientCommand.setUom(new UnitOfMeasureCommand());
		
		model.addAttribute("ingredient", ingredientCommand);
		model.addAttribute("uomList", uomService.listAllUom()
				);
		return "recipes/ingredients/ingredientform";
	}
	
	@GetMapping("/recipes/{recipeId}/ingredients/{ingredientId}/update")
	public String updateIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model) {
		model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredientId)));
		model.addAttribute("uomList", uomService.listAllUom());
		return "recipes/ingredients/ingredientform";
	}
	
	@GetMapping("/recipes/{recipeId}/ingredients/{ingredientId}/delete")
	public String deleteIngredient(@PathVariable String recipeId, @PathVariable String ingredientId) {
		ingredientService.deleteIngredientById(Long.valueOf(recipeId), Long.valueOf(ingredientId));

		return "redirect:/recipes/" + recipeId + "/ingredients";
	}
	
	@PostMapping("/recipes/{recipeId}/ingredients")
	public String saveOrUpdate(@ModelAttribute IngredientCommand command) {
		IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);
		
		log.debug("Saved Recipe ID: " + savedCommand.getRecipeId());
		log.debug("Saved Ingrdient ID: " + savedCommand.getId());
		return "redirect:/recipes/" + savedCommand.getRecipeId() + "/ingredients/" + savedCommand.getId() + "/show";
	}
}
