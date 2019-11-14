package guru.springframework.converters;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import guru.springframework.command.CategoryCommand;
import guru.springframework.domain.Category;

public class CategoryToCategoryCommandTest {

	public static final Long ID_VALUE = 1L;
	public static final String DESCRIPTION = "description";
	CategoryToCategoryCommand converter;
	
	@Before
	public void setUp() throws Exception {
		converter = new CategoryToCategoryCommand();
	}

	@Test
	public void testNullObject() {
		assertNull(converter.convert(null));
	}
	
	@Test
	public void testEmptyObject() throws Exception {
		assertNotNull(converter.convert(new Category()));
	} 

	@Test
	public void convert() throws Exception {
		//given
		Category category = new Category();
		category.setId(ID_VALUE);
		category.setDescription(DESCRIPTION);
		
		//when
		CategoryCommand categoryCommand = converter.convert(category);
		
		//then
		assertEquals(ID_VALUE, categoryCommand.getId());
		assertEquals(DESCRIPTION, categoryCommand.getDescription());
	}
}
