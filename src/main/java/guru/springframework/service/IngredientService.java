package guru.springframework.service;

import java.util.Set;

import guru.springframework.command.IngredientCommand;
import guru.springframework.domain.Ingredient;

public interface IngredientService {
	IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);
}
