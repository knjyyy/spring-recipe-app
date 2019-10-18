package guru.springframework.controllers;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashSet;
import java.util.Set;

import guru.springframework.domain.Recipe;
import guru.springframework.repository.RecipeRepository;
import guru.springframework.service.RecipeService;
import guru.springframework.service.RecipeServiceImpl;

public class IndexControllerTest {

	@Mock
	RecipeService recipeService;
	
	@Mock
	Model model;
	
	IndexController indexController;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		indexController = new IndexController(recipeService);
	}
	
	@Test
	public void testMockMVC() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
		mockMvc.perform(get("/")).andExpect(status().isOk())
			.andExpect(view().name("index"));
	}

	@Test
	public void testGetIndexPage() {

		Set<Recipe> recipes = new HashSet<>();
		Recipe r1 = new Recipe();
		r1.setId(1l);
		recipes.add(r1);
		Recipe r2 = new Recipe();
		r2.setId(2l);
		recipes.add(r2);
		
		long size = recipes.size();
		
		ArgumentCaptor<Set<Recipe>> argCatch = ArgumentCaptor.forClass(Set.class);
		
		when(recipeService.getRecipes()).thenReturn(recipes);
		String indexPage = "index";
		assertEquals(indexController.getIndexPage(model), indexPage);
		verify(recipeService, times(1)).getRecipes();		
		verify(model, times(1)).addAttribute(eq("recipes"), argCatch.capture());
		
		Set<Recipe> recipeCapture = argCatch.getValue();
		assertEquals(recipeCapture.size(), size);
	}
}
