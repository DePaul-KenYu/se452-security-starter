package edu.depaul.cdm.se452.concept.security;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Integer>{
    Optional<UserRole> findByAuthority(String authority);
}
