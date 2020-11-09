package fpt.captonse.dfn.service.mapper;


import fpt.captonse.dfn.domain.*;
import fpt.captonse.dfn.service.dto.LabelDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Label} and its DTO {@link LabelDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LabelMapper extends EntityMapper<LabelDTO, Label> {


    @Mapping(target = "ids", ignore = true)
    @Mapping(target = "removeId", ignore = true)
    Label toEntity(LabelDTO labelDTO);

    default Label fromId(Long id) {
        if (id == null) {
            return null;
        }
        Label label = new Label();
        label.setId(id);
        return label;
    }
}
