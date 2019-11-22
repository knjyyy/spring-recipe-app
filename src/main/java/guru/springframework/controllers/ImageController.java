package guru.springframework.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import guru.springframework.command.RecipeCommand;
import guru.springframework.service.ImageService;
import guru.springframework.service.RecipeService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ImageController {
	private final ImageService imageService;
	private final RecipeService recipeService;

	public ImageController(ImageService imageService, RecipeService recipeService) {
		this.imageService = imageService;
		this.recipeService = recipeService;
	}

	@GetMapping("/recipes/{recipeId}/image")
	public String showUploadForm(@PathVariable String recipeId, Model model) {
		model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(recipeId)));
		return "recipes/imageuploadform";
	}

	@PostMapping("/recipes/{recipeId}/image")
	public String handleImagePost(@PathVariable String recipeId, @RequestParam("imagefile") MultipartFile file) {
		imageService.saveImageFile(Long.valueOf(recipeId), file);
		return "redirect:/recipes/" + recipeId + "/show";
	}
	
	@GetMapping("/recipes/{recipeId}/recipeimage")
	public void renderImageFromDB(@PathVariable String recipeId, HttpServletResponse response) throws IOException {
		RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));
		
		if(recipeCommand.getImage() != null) {
			byte[] byteUnboxed = new byte[recipeCommand.getImage().length];
			int i = 0;
			
			for(byte b : recipeCommand.getImage()) {
				byteUnboxed[i++] = b;
			}
			
			response.setContentType("image/jpeg");
			InputStream is = new ByteArrayInputStream(byteUnboxed);
			IOUtils.copy(is, response.getOutputStream());
		}
	}
}
