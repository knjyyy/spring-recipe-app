package guru.springframework.domain;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class CategoryTest {
	
	Category category;
	
	@Before
	public void setUp() {
		this.category = new Category();
	}

	@Test
	public void testGetId() {
		Long id = 4l;
		category.setId(id);
		assertEquals(id, category.getId());
	}

	@Test
	public void testGetDescription() {
		String desc = "Sample description";
		category.setDescription(desc);
		assertEquals(desc, category.getDescription());
	}

	@Test
	public void testGetRecipes() {
		Set<Recipe> recipes = new HashSet<>();

		Recipe recipe = Recipe.builder().id(20l)
				.description("sample recipe")
				.prepTime(10)
				.cookTime(10)
				.servings(4)
				.source("")
				.url("")
				.direction("Sample direction for the sample recipe.")
				.ingredients(new HashSet<>())
				.image(new byte[2])
				.difficulty(Difficulty.EASY)
				.categories(new HashSet<>())
				.build();
					
		recipes.add(recipe);		
		category.setRecipes(recipes);
		
		assertEquals(recipes, this.category.getRecipes());
	}

	@Test
	public void testEquals() {
		Category cat1 = new Category();
		cat1.setId(1L);
		Category cat2 = new Category();
		cat2.setId(1L);
		
		assertEquals(true, cat1.equals(cat2));
	}
	
	@Test
	public void testNotEquals() {
		Category cat1 = new Category();
		cat1.setId(1L);
		Category cat2 = new Category();
		cat2.setId(2L);
		
		assertEquals(false, cat1.equals(cat2));
	}
	
	@Test
	public void testNotNullEquals() {
		Category cat1 = new Category();
		cat1.setId(1L);
		Category cat2 = new Category();
		cat2.setId(2L);
		
		assertNotNull(cat1.equals(cat2));
	}
}
