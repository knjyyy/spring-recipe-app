package guru.springframework.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import guru.springframework.domain.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, Long>{	
	Optional<Recipe> findByDescription(String description);
}
