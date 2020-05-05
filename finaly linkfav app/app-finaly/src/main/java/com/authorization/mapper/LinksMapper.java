package com.authorization.mapper;


import com.authorization.model.crud_model.FavoriteLink;
import com.authorization.model.crud_model.dto.LinkDto;
import org.springframework.stereotype.Component;

@Component
public class LinksMapper implements Mapper<FavoriteLink, LinkDto> {


    @Override
    public LinkDto map(FavoriteLink from) {
        return new LinkDto
                .Builder()
                .username(from.getUsername())
                .link(from.getLink())
                .describe(from.getDescribe())
                .builder();

    }

    @Override
    public FavoriteLink revers(LinkDto to) {
        return null;
    }
}
