package com.authorization.service.crud_service;

import com.authorization.exception.ResourceNotFoundException;
import com.authorization.model.crud_model.FavoriteLink;
import com.authorization.repository.crud_repository.LinksRepository;
import jdk.management.resource.ResourceRequestDeniedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LinksService {
    private LinksRepository linksRepository;

    public LinksService(LinksRepository linksRepository) {
        this.linksRepository = linksRepository;
    }

    public List<FavoriteLink> allLinks() {
        return linksRepository.findAll();
    }

    public FavoriteLink addLink(FavoriteLink favoriteLink) {
        return linksRepository.save(favoriteLink);
    }

    public FavoriteLink getLinkById(long id) {
        return linksRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("can't find id"));
    }

    public FavoriteLink updateLink(long id, FavoriteLink favoriteLink) {
        return linksRepository.findById(id).map(f -> {
            f.setUsername(favoriteLink.getUsername());
            f.setDescribe(favoriteLink.getDescribe());
            f.setLink(favoriteLink.getLink());
            return linksRepository.save(f);
        }).orElseThrow(() -> new ResourceNotFoundException("link by id: " + id + " not found"));
    }

    public ResponseEntity<?> deleteLink(long id) {
        return linksRepository.findById(id).map(f -> {
            linksRepository.deleteById(id);
            return new ResponseEntity<>("link " + id + " was delete", HttpStatus.OK);
        }).orElseThrow(() -> new ResourceNotFoundException("link by id: " + id + " not found"));
    }

}
