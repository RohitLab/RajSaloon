package com.raj.app.repository;

import com.raj.app.domain.AdminUser;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AdminUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {
}
