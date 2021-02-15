package com.nbp.bear.components.repository;

import com.nbp.bear.components.model.NbpUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NbpUserRepository extends JpaRepository<NbpUser, Integer> {
    NbpUser findByUserName(String userName);
}
