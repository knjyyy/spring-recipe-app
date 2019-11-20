package guru.springframework.service;

import java.util.Set;

import guru.springframework.command.UnitOfMeasureCommand;

public interface UomService {
	Set<UnitOfMeasureCommand> listAllUom();
}
