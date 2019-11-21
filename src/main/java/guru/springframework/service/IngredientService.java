package guru.springframework.service;

import guru.springframework.command.IngredientCommand;

public interface IngredientService {
	IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);
	IngredientCommand saveIngredientCommand(IngredientCommand ingredient);
	void deleteIngredientById(Long recipeId, Long ingredientId);
}
