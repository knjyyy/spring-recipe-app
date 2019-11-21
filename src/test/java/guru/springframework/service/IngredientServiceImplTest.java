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
import guru.springframework.repository.UnitOfMeasureRepository;

public class IngredientServiceImplTest {
	
	private final IngredientToIngredientCommand ingredientToIngredientCommand;
	private final IngredientCommandToIngredient ingredientCommandToIngredient;
	
	@Mock
	RecipeRepository recipeRepository;
	
	@Mock
	UnitOfMeasureRepository uomRepository;

	IngredientService ingredientService;
		
	public IngredientServiceImplTest() {
		this.ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
		this.ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
	}

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		ingredientService = new IngredientServiceImpl(recipeRepository, ingredientToIngredientCommand, ingredientCommandToIngredient, uomRepository);
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

	@Test
	public void testSaveRecipe() throws Exception {
		//given
		IngredientCommand command = new IngredientCommand();
		command.setId(3L);
		command.setRecipeId(2L);

		Optional<Recipe> recipeOptional = Optional.of(new Recipe());
		Recipe savedRecipe = new Recipe();
		savedRecipe.addIngredient(new Ingredient());
		savedRecipe.getIngredients().iterator().next().setId(3L);

		when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
		when(recipeRepository.save(any())).thenReturn(savedRecipe);

		//when
		IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

		//then
		assertEquals(Long.valueOf(3L), savedCommand.getId());
		verify(recipeRepository, times(1)).findById(anyLong());
		verify(recipeRepository, times(1)).save(any(Recipe.class));
	}
	
	@Test
	public void testDeleteIngredientById() throws Exception {
		//given
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		Ingredient ingredient = new Ingredient();
		ingredient.setId(1L);
		recipe.addIngredient(ingredient);
		ingredient.setRecipe(recipe);
		Optional<Recipe> recipeOptional = Optional.of(recipe);

		when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
		
		//when
		ingredientService.deleteIngredientById(1L, 1L);
		
		//then
		verify(recipeRepository, times(1)).findById(anyLong());
		verify(recipeRepository, times(1)).save(any(Recipe.class));
	}
}
