package guru.springframework.service;

import java.util.Set;

import guru.springframework.command.RecipeCommand;
import guru.springframework.domain.Recipe;

public interface RecipeService {
	Set<Recipe> getRecipes();
	
	Recipe findById(Long id);
	
	RecipeCommand saveRecipeCommand(RecipeCommand command);
	
	RecipeCommand findCommandById(Long id);
}
