package guru.springframework.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import guru.springframework.command.NotesCommand;
import guru.springframework.domain.Notes;
import lombok.Synchronized;

@Component
public class NotesToNotesCommand implements Converter<Notes, NotesCommand>{

	@Synchronized
	@Nullable
	@Override
	public NotesCommand convert(Notes source) {
		if(source == null) {
			return null;			
		}
		
		final NotesCommand notes = new NotesCommand();
		notes.setId(source.getId());
		notes.setRecipe(source.getRecipe());
		notes.setRecipeNotes(source.getRecipeNotes());
		
		return notes;
	}

}
