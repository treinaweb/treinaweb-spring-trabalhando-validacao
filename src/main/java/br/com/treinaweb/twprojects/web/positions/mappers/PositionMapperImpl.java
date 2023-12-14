package br.com.treinaweb.twprojects.web.positions.mappers;

import org.springframework.stereotype.Component;

import br.com.treinaweb.twprojects.core.models.Position;
import br.com.treinaweb.twprojects.web.positions.dtos.PositionForm;
import br.com.treinaweb.twprojects.web.positions.dtos.PositionListItem;

@Component
public class PositionMapperImpl implements PositionMapper {

    @Override
    public PositionForm toPositionForm(Position position) {
        return PositionForm.builder()
            .name(position.getName())
            .build();
    }

    @Override
    public Position toPosition(PositionForm positionForm) {
        return Position.builder()
            .name(positionForm.getName())
            .build();
    }

    @Override
    public PositionListItem toPositionListItem(Position position) {
        return PositionListItem.builder()
            .id(position.getId())
            .name(position.getName())
            .build();
    }
    
}
