package guru.springframework.command;

import java.math.BigDecimal;

import guru.springframework.domain.Recipe;
import guru.springframework.domain.UnitOfMeasure;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class IngredientCommand {

	private Long id;
	private Long recipeId;
	private String description;
	private BigDecimal amount;
	private Recipe recipe;
	private UnitOfMeasureCommand uom;
	
}
