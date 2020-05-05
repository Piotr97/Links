package com.authorization.repository.crud_repository;

import com.authorization.model.crud_model.FavoriteLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LinksRepository extends JpaRepository<FavoriteLink, Long> {
    @Query("select f from FavoriteLink f where f.id=?1")
    FavoriteLink findFavoriteLinkById(long id);
}
