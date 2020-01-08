package guru.springframework.controllers;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

import guru.springframework.command.RecipeCommand;
import guru.springframework.service.ImageService;
import guru.springframework.service.RecipeService;

public class ImageControllerTest {

	@Mock
	ImageService imageService;

	@Mock
	RecipeService recipeService;

	ImageController controller;

	MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		controller = new ImageController(imageService, recipeService);
		mockMvc = MockMvcBuilders.standaloneSetup(controller)
				.setControllerAdvice(new ControlExceptionHandler())
				.build();
	}

	@Test
	public void getImageForm() throws Exception {
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(2L);

		when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);

		mockMvc.perform(get("/recipes/1/image"))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("recipe"));

		verify(recipeService, times(1)).findCommandById(anyLong());
	}

	@Test
	public void handleImagePost() throws Exception {
		MockMultipartFile multipartFile = new MockMultipartFile("imagefile", "testing.txt", "text/plain", "Spring Framework Guru".getBytes());

		mockMvc.perform(multipart("/recipes/1/image").file(multipartFile))
		.andExpect(status().is3xxRedirection())
		.andExpect(header().string("location", "/recipes/1/show"));

		verify(imageService, times(1)).saveImageFile(anyLong(), any());

	}                      
	
	@Test
	public void readerImageFromDB() throws Exception {
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(1L);
		
		String s = "sample image text";
		Byte[] byteBoxed = new Byte[s.getBytes().length];
		int i = 0;
		
		for (byte b: s.getBytes()) {
			byteBoxed[i++] = b;
		}
		
		recipeCommand.setImage(byteBoxed);
		
		when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);
		
		MockHttpServletResponse response = mockMvc.perform(get("/recipes/1/recipeimage"))
				.andExpect(status().isOk())
				.andReturn().getResponse();
		
		byte[] responseBytes = response.getContentAsByteArray();
		
		assertEquals(s.getBytes().length, responseBytes.length);
	}
	
	@Test
	public void testGetImageNumberFormat() throws Exception {
		mockMvc.perform(get("/recipes/asdf/recipeimage"))
			.andExpect(status().isBadRequest())
			.andExpect(view().name("400error"));
	}
}
