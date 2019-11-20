package guru.springframework.service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import guru.springframework.command.UnitOfMeasureCommand;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.repository.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UomServiceImpl implements UomService{

	private final UnitOfMeasureRepository uomRepository;
	private final UnitOfMeasureToUnitOfMeasureCommand uomConverter;
	
	public UomServiceImpl(UnitOfMeasureRepository uomRepository, UnitOfMeasureToUnitOfMeasureCommand uomConverter) {
		this.uomRepository = uomRepository;
		this.uomConverter = uomConverter;
	}

	@Override
	public Set<UnitOfMeasureCommand> listAllUom() {
		return StreamSupport.stream(uomRepository.findAll()
				.spliterator(), false)
				.map(uomConverter::convert)
				.collect(Collectors.toSet());
	}

}
