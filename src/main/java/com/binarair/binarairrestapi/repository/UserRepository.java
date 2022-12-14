package com.binarair.binarairrestapi.repository;

import com.binarair.binarairrestapi.model.entity.User;
import com.binarair.binarairrestapi.model.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM users WHERE role_name_id = ?1"
    )
    Optional<User> findByRole(String role);
}
