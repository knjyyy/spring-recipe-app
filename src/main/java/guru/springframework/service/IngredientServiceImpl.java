package guru.springframework.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import guru.springframework.command.IngredientCommand;
import guru.springframework.converters.IngredientCommandToIngredient;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

	private final RecipeRepository recipeRepository;
	private final IngredientToIngredientCommand ingredientToIngredientCommand;
	private final IngredientCommandToIngredient ingredientCommandToIngredient;
	
	public IngredientServiceImpl(RecipeRepository recipeRepository,
			IngredientToIngredientCommand ingredientToIngredientCommand,
			IngredientCommandToIngredient ingredientCommandToIngredient) {
		this.recipeRepository = recipeRepository;
		this.ingredientToIngredientCommand = ingredientToIngredientCommand;
		this.ingredientCommandToIngredient = ingredientCommandToIngredient;
	}

	@Override
	public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
		Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
		
		if(!recipeOptional.isPresent()) {
			log.error("Recipe not found for id: " + recipeId);
		}
		
		Recipe recipe = recipeOptional.get();
		
		Optional<IngredientCommand> ingredientCommand = recipe.getIngredients()
			.stream()
			.filter(ingredient -> ingredient.getId().equals(ingredientId))
			.map(ingredient -> ingredientToIngredientCommand.convert(ingredient))
			.findFirst();
		
		if(!ingredientCommand.isPresent()) {
			log.error("Ingredient not found for id:" + ingredientId);
		}
		
		return ingredientCommand.get();
	}

}
