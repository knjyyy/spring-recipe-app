package guru.springframework.controllers;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import guru.springframework.command.IngredientCommand;
import guru.springframework.command.RecipeCommand;
import guru.springframework.service.IngredientService;
import guru.springframework.service.RecipeService;
import guru.springframework.service.UomService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;



public class IngredientControllerTest {

	@Mock
	RecipeService recipeService;
	
	@Mock
	IngredientService ingredientService;
	
	@Mock
	UomService uomService;
	
	IngredientController ingredientController;
	
	MockMvc mockMvc;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		ingredientController = new IngredientController(recipeService, ingredientService, uomService);
		mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
	}

	@Test
	public void testListIngredients() throws Exception {
		//given
		RecipeCommand recipeCommand = new RecipeCommand();
		when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);
		
		//when
		mockMvc.perform(get("/recipes/1/ingredients"))
			.andExpect(status().isOk())
			.andExpect(view().name("recipes/ingredients/list"))
			.andExpect(model().attributeExists("recipe"));
		
		//then
		verify(recipeService, times(1)).findCommandById(anyLong());
	}

	@Test
	public void testShowIngredient() throws Exception {
		//given
		IngredientCommand ingredientCommand = new IngredientCommand();
		
		//when
		when(ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong()))
			.thenReturn(ingredientCommand);
		
		//then
		mockMvc.perform(get("/recipes/1/ingredients/1/show"))
			.andExpect(status().isOk())
			.andExpect(view().name("recipes/ingredients/show"))
			.andExpect(model().attributeExists("ingredient"));
	}
	
	@Test
	public void testNewIngredientForm() throws Exception {
		//given
		RecipeCommand command = new RecipeCommand();
		command.setId(1L);
		
		//when
		when(recipeService.findCommandById(anyLong())).thenReturn(command);
		when(uomService.listAllUom()).thenReturn(new HashSet<>());
		
		//then
		mockMvc.perform(get("/recipes/1/ingredients/new"))
			.andExpect(status().isOk())
			.andExpect(view().name("recipes/ingredients/ingredientform"))
			.andExpect(model().attributeExists("ingredient"));
	}
	
	@Test
	public void testUpdateIngredient() throws Exception {
		//given
		IngredientCommand ingredientCommand = new IngredientCommand();

		//when
		when(ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(ingredientCommand);
		when(uomService.listAllUom()).thenReturn(new HashSet<>());

		//then
		mockMvc.perform(get("/recipes/1/ingredients/2/update"))
			.andExpect(status().isOk())
			.andExpect(view().name("recipes/ingredients/ingredientform"))
			.andExpect(model().attributeExists("ingredient"))
			.andExpect(model().attributeExists("uomList"));
	}
	
	@Test
	public void testDeleteIngredient() throws Exception {
		//then
		mockMvc.perform(get("/recipes/2/ingredients/3/delete")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("id","")
				.param("description", "some string")
		).andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/recipes/2/ingredients"));
	}
	
	@Test
	public void testSaveOrUpdate() throws Exception {
		//given 
		IngredientCommand command = new IngredientCommand();
        command.setId(3L);
        command.setRecipeId(2L);

		//when
		when(ingredientService.saveIngredientCommand(any())).thenReturn(command);

		//then
		mockMvc.perform(post("/recipes/2/ingredients")
		).andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/recipes/2/ingredients/3/show"));

	}
}
