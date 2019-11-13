package guru.springframework.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import guru.springframework.command.RecipeCommand;
import guru.springframework.domain.Category;
import guru.springframework.domain.Recipe;
import lombok.Synchronized;

@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand>{

	private final CategoryToCategoryCommand categoryConverter;
	private final IngredientToIngredientCommand ingredientConverter;
	private final NotesToNotesCommand notesConverter;
	
	public RecipeToRecipeCommand(CategoryToCategoryCommand categoryConverter,
			IngredientToIngredientCommand ingredientConverter,
			NotesToNotesCommand notesConverter) {
		this.categoryConverter = categoryConverter;
		this.ingredientConverter = ingredientConverter;
		this.notesConverter = notesConverter;
	}
	
	@Synchronized
	@Nullable
	@Override
	public RecipeCommand convert(Recipe source) {
		if(source == null) {
			return null;			
		}
		
		final RecipeCommand recipe = new RecipeCommand();
		recipe.setId(source.getId());
		recipe.setDescription(source.getDescription());
		//recipe.setCategories(source.getCategories());
		recipe.setCookTime(source.getCookTime());
		recipe.setDifficulty(source.getDifficulty());
		recipe.setDirection(source.getDirection());
		recipe.setImage(source.getImage());
		//recipe.setIngredients(source.getIngredients());
		recipe.setNotes(notesConverter.convert(source.getNotes()));
		recipe.setPrepTime(source.getPrepTime());
		recipe.setServings(source.getServings());
		recipe.setSource(source.getSource());
		recipe.setUrl(source.getUrl());
		
		if(source.getCategories() != null && source.getCategories().size() > 0) {
			source.getCategories()
			.forEach((Category category) -> recipe.getCategories().add(categoryConverter.convert(category)));
		}
		
		if (source.getIngredients() != null && source.getIngredients().size() > 0) {
			source.getIngredients()
			.forEach(ingredient -> recipe.getIngredients().add(ingredientConverter.convert(ingredient)));
		}
		
		return recipe;
	}

}
