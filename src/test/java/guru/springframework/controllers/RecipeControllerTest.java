package guru.springframework.controllers;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import guru.springframework.command.RecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.exceptions.NotFoundException;
import guru.springframework.service.RecipeService;

public class RecipeControllerTest {

	@Mock
	RecipeService recipeService;

	RecipeController controller;

	MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		controller = new RecipeController(recipeService);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void testGetRecipe() throws Exception {
		Recipe recipe = new Recipe();
		recipe.setId(1l);

		when(recipeService.findById(anyLong())).thenReturn(recipe);

		mockMvc.perform(get("/recipes/1/show/"))
			.andExpect(status().isOk())
			.andExpect(view().name("recipes/show"))
			.andExpect(model().attributeExists("recipe"));
	}

	@Test
	public void testGetRecipeNotFound() throws Exception {
		when(recipeService.findById(anyLong())).thenThrow(NotFoundException.class);
		
		mockMvc.perform(get("/recipes/23/show"))
			.andExpect(status().isNotFound())
			.andExpect(view().name("404error"));
	}
	
	@Test
	public void testGetNewRecipeForm() throws Exception {
		mockMvc.perform(get("/recipes/new"))
			.andExpect(status().isOk())
			.andExpect(view().name("recipes/recipeform"))
			.andExpect(model().attributeExists("recipe"));
	}

	@Test
	public void testPostNewRecipeForm() throws Exception {
		RecipeCommand command = new RecipeCommand();
		command.setId(2L);

		when(recipeService.saveRecipeCommand(any())).thenReturn(command);

		mockMvc.perform(post("/recipes")
			.contentType(MediaType.APPLICATION_FORM_URLENCODED).param("id", "")
			.param("description", "some string"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/recipes/2/show"));
	}

	@Test
	public void testGetUpdateView() throws Exception {
		RecipeCommand command = new RecipeCommand();
		command.setId(2L);

		when(recipeService.findCommandById(anyLong())).thenReturn(command);

		mockMvc.perform(get("/recipes/1/update"))
			.andExpect(status().isOk())
			.andExpect(view().name("recipes/recipeform"))
			.andExpect(model().attributeExists("recipe"));
	}

	@Test
	public void testDeleteRecipe() throws Exception {
		mockMvc.perform(get("/recipes/1/delete"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/"));
		
		verify(recipeService, times(1)).deleteById(anyLong());
	}

}