package guru.springframework.bootstrap;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import guru.springframework.domain.Category;
import guru.springframework.domain.Difficulty;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Notes;
import guru.springframework.domain.Recipe;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repository.CategoryRepository;
import guru.springframework.repository.RecipeRepository;
import guru.springframework.repository.UnitOfMeasureRepository;

@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {

	private CategoryRepository categoryRepository;
	private RecipeRepository recipeRepository;
	private UnitOfMeasureRepository unitOfMeasureRepository;
	
	public RecipeBootstrap(CategoryRepository categoryRepository, RecipeRepository recipeRepository,
			UnitOfMeasureRepository unitOfMeasureRepository) {
		this.categoryRepository = categoryRepository;
		this.recipeRepository = recipeRepository;
		this.unitOfMeasureRepository = unitOfMeasureRepository;
	}
	
	public List<Recipe> getRecipes(){
		List<Recipe> recipes = new ArrayList<Recipe>();
		
		//get UOMs
		Optional<UnitOfMeasure> pieceUOMOptional = unitOfMeasureRepository.findByDescription("Piece");
		
		if(pieceUOMOptional.isEmpty()) {
			throw new RuntimeException("Expected unit_of_measure not found.");
		}

		Optional<UnitOfMeasure> tableUOMOptional = unitOfMeasureRepository.findByDescription("Tablespoon");
		
		if(tableUOMOptional.isEmpty()) {
			throw new RuntimeException("Expected unit_of_measure not found.");
		}
		Optional<UnitOfMeasure> teaUOMOptional = unitOfMeasureRepository.findByDescription("Teaspoon");
		
		if(teaUOMOptional.isEmpty()) {
			throw new RuntimeException("Expected unit_of_measure not found.");
		}
		Optional<UnitOfMeasure> dashUOMOptional = unitOfMeasureRepository.findByDescription("Dash");
		
		if(dashUOMOptional.isEmpty()) {
			throw new RuntimeException("Expected unit_of_measure not found.");
		}
		Optional<UnitOfMeasure> pintUOMOptional = unitOfMeasureRepository.findByDescription("Pinch");
		
		if(pintUOMOptional.isEmpty()) {
			throw new RuntimeException("Expected unit_of_measure not found.");
		}
		Optional<UnitOfMeasure> cupUOMOptional = unitOfMeasureRepository.findByDescription("Cup");
		
		if(cupUOMOptional.isEmpty()) {
			throw new RuntimeException("Expected unit_of_measure not found.");
		}	
		
		UnitOfMeasure pieceUOM = pieceUOMOptional.get();
		UnitOfMeasure tableUOM = tableUOMOptional.get();
		UnitOfMeasure teaUOM = teaUOMOptional.get();
		UnitOfMeasure dashUOM = dashUOMOptional.get();
		UnitOfMeasure pintUOM = pintUOMOptional.get();
		UnitOfMeasure cupUOM = cupUOMOptional.get();
		
		//get Category
		Optional<Category> americanCategoryOptional = categoryRepository.findByDescription("American");
		
		if(americanCategoryOptional.isEmpty()) {
			throw new RuntimeException("Expected category not found.");
		}

		Optional<Category> mexicanCategoryOptional = categoryRepository.findByDescription("Mexican");
		
		if(mexicanCategoryOptional.isEmpty()) {
			throw new RuntimeException("Expected category not found.");
		}
		
		Category americanCategory = americanCategoryOptional.get();
		Category mexicanCategory = mexicanCategoryOptional.get();
		
		//create Recipe object
		Recipe guacamoleRecipe = new Recipe();
		guacamoleRecipe.setDescription("Perfect Guacamole");
		guacamoleRecipe.setPrepTime(10);
		guacamoleRecipe.setCookTime(0);
		guacamoleRecipe.setDifficulty(Difficulty.EASY);
		guacamoleRecipe.setDirections("1 Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. Place in a bowl.\r\n" + 
				"\\n2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)\r\n" + 
				"\\n3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown. Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness. Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\r\n" + 
				"\\n4 Cover with plastic and chill to store: Place plastic wrap on the surface of the guacamole cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve. Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving."
				);
		
		Notes guacamoleNotes = new Notes();
		guacamoleNotes.setRecipe(guacamoleRecipe);
		guacamoleNotes.setRecipeNotes("For a very quick guacamole just take a 1/4 cup of salsa and mix it in with your mashed avocados.\r\n" + 
				"\nFeel free to experiment! One classic Mexican guacamole has pomegranate seeds and chunks of peaches in it (a Diana Kennedy favorite). Try guacamole with added pineapple, mango, or strawberries (see our Strawberry Guacamole).\r\n" + 
				"\nThe simplest version of guacamole is just mashed avocados with salt. Don't let the lack of availability of other ingredients stop you from making guacamole.\r\n" + 
				"\nTo extend a limited supply of avocados, add either sour cream or cottage cheese to your guacamole dip. Purists may be horrified, but so what? It tastes great."
				);
		
		guacamoleRecipe.setNotes(guacamoleNotes);
		
		guacamoleRecipe.getIngredients().add(new Ingredient("Ripe Avocadoes", new BigDecimal(2), pieceUOM, guacamoleRecipe));
		guacamoleRecipe.getIngredients().add(new Ingredient("Kosher salt", new BigDecimal(0.5), teaUOM, guacamoleRecipe));
		guacamoleRecipe.getIngredients().add(new Ingredient("fresh lime juice or lemon juice", new BigDecimal(1), tableUOM, guacamoleRecipe));
		guacamoleRecipe.getIngredients().add(new Ingredient("Red Onion, minced or Green Onion, thinly sliced", new BigDecimal(0.25), cupUOM, guacamoleRecipe));
		guacamoleRecipe.getIngredients().add(new Ingredient("Serrano chiles, stems and seeds removed, minced", new BigDecimal(2), pieceUOM, guacamoleRecipe));
		guacamoleRecipe.getIngredients().add(new Ingredient("Cilantro ", new BigDecimal(2), pieceUOM, guacamoleRecipe));
		guacamoleRecipe.getIngredients().add(new Ingredient("Black Pepper, freshly grated ", new BigDecimal(1), dashUOM, guacamoleRecipe));
		guacamoleRecipe.getIngredients().add(new Ingredient("Ripe Tomato, seeds and pulp removed, chopped", new BigDecimal(1), pieceUOM, guacamoleRecipe));
		
		guacamoleRecipe.getCategories().add(americanCategory);
		guacamoleRecipe.getCategories().add(mexicanCategory);
		
		recipes.add(guacamoleRecipe);
		
		//Yummy tacos
		
		Recipe tacosRecipe = new Recipe();
		tacosRecipe.setDescription("Yummy Tacos");
		tacosRecipe.setCookTime(9);
		tacosRecipe.setPrepTime(20);
		tacosRecipe.setDifficulty(Difficulty.MODERATE);
		tacosRecipe.setDirections("1 Prepare a gas or charcoal grill for medium-high, direct heat.\r\n" + 
				"\n2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over. Set aside to marinate while the grill heats and you prepare the rest of the toppings.\r\n" + 
				"\n3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes.\r\n" + 
				"\n4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side. Wrap warmed tortillas in a tea towel to keep them warm until serving.\r\n" + 
				"\n5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges."
				);
		
		Notes tacosNotes = new Notes();
		tacosNotes.setRecipe(tacosRecipe);
		tacosNotes.setRecipeNotes("\\nWe have a family motto and it is this: Everything goes better in a tortilla.\r\n" + 
				"\\nAny and every kind of leftover can go inside a warm tortilla, usually with a healthy dose of pickled jalapenos. I can always sniff out a late-night snacker when the aroma of tortillas heating in a hot pan on the stove comes wafting through the house.\r\n" + 
				"\r\n" + 
				"\\nToday’s tacos are more purposeful – a deliberate meal instead of a secretive midnight snack!\r\n" + 
				"\r\n" + 
				"\\nFirst, I marinate the chicken briefly in a spicy paste of ancho chile powder, oregano, cumin, and sweet orange juice while the grill is heating. You can also use this time to prepare the taco toppings.\r\n" + 
				"\\nGrill the chicken, then let it rest while you warm the tortillas. Now you are ready to assemble the tacos and dig in. The whole meal comes together in about 30 minutes!\r\n" + 
				"\r\n" + 
				"");
		
		tacosRecipe.setNotes(tacosNotes);
		
		tacosRecipe.getIngredients().add(new Ingredient("Ancho Chili Powder", new BigDecimal(2), tableUOM, tacosRecipe));
		tacosRecipe.getIngredients().add(new Ingredient("Dried Oregano", new BigDecimal(1), teaUOM, tacosRecipe));
		tacosRecipe.getIngredients().add(new Ingredient("Dried Cumin", new BigDecimal(1), teaUOM, tacosRecipe));
		tacosRecipe.getIngredients().add(new Ingredient("Sugar", new BigDecimal(1), teaUOM, tacosRecipe));
		tacosRecipe.getIngredients().add(new Ingredient("Salt", new BigDecimal(0.5), teaUOM, tacosRecipe));
		tacosRecipe.getIngredients().add(new Ingredient("Clove Garlic, finely chopped", new BigDecimal(1), pieceUOM, tacosRecipe));
		tacosRecipe.getIngredients().add(new Ingredient("Orange Zest, finely grated", new BigDecimal(1), tableUOM, tacosRecipe));
		tacosRecipe.getIngredients().add(new Ingredient("Orange Juice, fresh-squeezed", new BigDecimal(3), tableUOM, tacosRecipe));
		tacosRecipe.getIngredients().add(new Ingredient("Olive Oil", new BigDecimal(2), tableUOM, tacosRecipe));
		tacosRecipe.getIngredients().add(new Ingredient("Chicken Thighs, skinless, boneless ", new BigDecimal(6), pieceUOM, tacosRecipe));
		tacosRecipe.getIngredients().add(new Ingredient("Small Corn Tortillas", new BigDecimal(8), cupUOM, tacosRecipe));
		tacosRecipe.getIngredients().add(new Ingredient("Packed Baby Arugula", new BigDecimal(3), pieceUOM, tacosRecipe));
		tacosRecipe.getIngredients().add(new Ingredient("Medium Ripe Avocados, sliced", new BigDecimal(2), pieceUOM, tacosRecipe));
		tacosRecipe.getIngredients().add(new Ingredient("Radishes, thinly sliced", new BigDecimal(4), pieceUOM, tacosRecipe));
		tacosRecipe.getIngredients().add(new Ingredient("Cherry Tomatoes, halved", new BigDecimal(0.5), pintUOM, tacosRecipe));
		tacosRecipe.getIngredients().add(new Ingredient("Red Onion, thinly sliced", new BigDecimal(0.25), pieceUOM, tacosRecipe));
		tacosRecipe.getIngredients().add(new Ingredient("Cilantro, roughly chopped", new BigDecimal(1), pieceUOM, tacosRecipe));
		tacosRecipe.getIngredients().add(new Ingredient("Sour Cream, thinned with 1/4 cup milk", new BigDecimal(0.5), cupUOM, tacosRecipe));
		tacosRecipe.getIngredients().add(new Ingredient("Lime, cut into wedges", new BigDecimal(1), pieceUOM, tacosRecipe));
		
		tacosRecipe.getCategories().add(americanCategory);
		tacosRecipe.getCategories().add(mexicanCategory);
		
		recipes.add(tacosRecipe);
		
		System.out.println("Just returning the favor.");
		
		return recipes;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		recipeRepository.saveAll(getRecipes());
		
	}
}
