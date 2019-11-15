package guru.springframework.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import guru.springframework.command.IngredientCommand;
import guru.springframework.converters.IngredientCommandToIngredient;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.converters.UnitOfMeasureCommandToUnitOfMeasure;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.repository.RecipeRepository;

public class IngredientServiceImplTest {
	
	private final IngredientToIngredientCommand ingredientToIngredientCommand;
	private final IngredientCommandToIngredient ingredientCommandToIngredient;
	
	@Mock
	RecipeRepository recipeRepository;

	IngredientService ingredientService;
		
	public IngredientServiceImplTest() {
		this.ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
		this.ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
	}

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		ingredientService = new IngredientServiceImpl(recipeRepository, ingredientToIngredientCommand, ingredientCommandToIngredient);
	}

	@Test
	public void testFindByRecipeIdAndIngredientId() {
		//given
		Ingredient ingredient1 = new Ingredient();
		Long id1 = 1L;
		ingredient1.setId(id1);

		Ingredient ingredient2 = new Ingredient();
		Long id2 = 2L;
		ingredient2.setId(id2);

		Ingredient ingredient3 = new Ingredient();
		Long id3 = 3L;
		ingredient3.setId(id3);
		
		Recipe recipe = new Recipe();
		Long idRecipe = 1L;
		recipe.setId(idRecipe);
		
		recipe.addIngredient(ingredient1);
		recipe.addIngredient(ingredient2);
		recipe.addIngredient(ingredient3);
		
		Optional<Recipe> recipeOptional = Optional.of(recipe);
		
		//when
		when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

		//then
		IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(idRecipe, id2);
		assertEquals(idRecipe, ingredientCommand.getRecipeId());
		assertEquals(id2, ingredientCommand.getId());
		verify(recipeRepository, times(1)).findById(anyLong());
	}

}
