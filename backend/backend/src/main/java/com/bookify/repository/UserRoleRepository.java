package com.bookify.repository;

import com.bookify.entity.AppRole;
import com.bookify.entity.Profile;
import com.bookify.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UUID> {
    List<UserRole> findByUser(Profile user);
    Optional<UserRole> findByUserAndRole(Profile user, AppRole role);
    boolean existsByUserAndRole(Profile user, AppRole role);
}
