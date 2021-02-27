package com.nbp.bear.components.repository;

import com.nbp.bear.components.model.NbpUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NbpUserRepository extends JpaRepository<NbpUser, Integer> {
    Optional<NbpUser> findByUserName(String userName);
    Optional<NbpUser> findByEmail(String email);
}
