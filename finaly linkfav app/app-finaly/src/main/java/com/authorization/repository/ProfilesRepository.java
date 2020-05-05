package com.authorization.repository;

import com.authorization.model.Profiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfilesRepository extends JpaRepository<Profiles, Long> {
    @Query("select p from  Profiles p where p.login=?1")
    Optional<Profiles> getProfilesByLogin(String login);
}
