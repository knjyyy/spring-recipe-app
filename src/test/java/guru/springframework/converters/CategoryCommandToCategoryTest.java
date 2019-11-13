package guru.springframework.converters;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import guru.springframework.command.CategoryCommand;
import guru.springframework.domain.Category;

class CategoryCommandToCategoryTest {

	public static final Long ID_VALUE = 1L;
	public static final String DESCRIPTION = "description";
	CategoryCommandToCategory converter;
	
	@BeforeEach
	void setUp() throws Exception {
		converter = new CategoryCommandToCategory();
	}

	@Test
	void testNullObject() {
		assertNull(converter.convert(null));
	}

	@Test
	void testEmptyObject() {
		assertNotNull(converter.convert(new CategoryCommand()));
	}
	
	@Test
	public void convert() throws Exception {
		//given
		CategoryCommand categoryCommand = new CategoryCommand();
		categoryCommand.setId(ID_VALUE);
		categoryCommand.setDescription(DESCRIPTION);
		
		//when
		Category category = converter.convert(categoryCommand);
		
		//then
		assertEquals(ID_VALUE, category.getId());
		assertEquals(DESCRIPTION, category.getDescription());
	}
}
