package fpt.captonse.dfn.service.mapper;


import fpt.captonse.dfn.domain.*;
import fpt.captonse.dfn.service.dto.ArticleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Article} and its DTO {@link ArticleDTO}.
 */
@Mapper(componentModel = "spring", uses = {LabelMapper.class})
public interface ArticleMapper extends EntityMapper<ArticleDTO, Article> {

    @Mapping(source = "labelId.id", target = "labelIdId")
    ArticleDTO toDto(Article article);

    @Mapping(source = "labelIdId", target = "labelId")
    Article toEntity(ArticleDTO articleDTO);

    default Article fromId(Long id) {
        if (id == null) {
            return null;
        }
        Article article = new Article();
        article.setId(id);
        return article;
    }
}
