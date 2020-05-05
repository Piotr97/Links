package com.authorization.service.crud_service;

import com.authorization.exception.ResourceNotFoundException;
import com.authorization.mapper.LinksMapper;
import com.authorization.model.crud_model.FavoriteLink;
import com.authorization.model.crud_model.dto.LinkDto;
import com.authorization.repository.crud_repository.LinksRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LinksDtoService {

    private static final Logger logger = LoggerFactory.getLogger(LinksDtoService.class);

    private LinksRepository linksRepository;
    private LinksMapper linksMapper;

    public LinksDtoService(LinksRepository linksRepository, LinksMapper linksMapper) {
        this.linksRepository = linksRepository;
        this.linksMapper = linksMapper;
    }

    public List<LinkDto> getLinksDto() {
        List<LinkDto> linkDtos = new ArrayList<>();
        linksRepository.findAll().forEach(l -> {
            linkDtos.add(linksMapper.map(l));
        });
        return linkDtos;
    }

    public LinkDto createDto(LinkDto linkDto) {
        FavoriteLink favoriteLink = new FavoriteLink();
        favoriteLink.setUsername(linkDto.getUsername());
        favoriteLink.setLink(linkDto.getLink());
        favoriteLink.setDescribe(linkDto.getDescribe());


        logger.info("Try to save FavLink to DB {}", favoriteLink);
        FavoriteLink result = linksRepository.save(favoriteLink);
        if (result == null) {
            logger.error("Error while save FavLink to data base!");
            return null;
        }
        return linksMapper.map(favoriteLink);
    }
}

