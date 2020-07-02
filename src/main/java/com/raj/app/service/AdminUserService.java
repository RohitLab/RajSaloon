package com.raj.app.service;

import com.raj.app.service.dto.AdminUserDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.raj.app.domain.AdminUser}.
 */
public interface AdminUserService {

    /**
     * Save a adminUser.
     *
     * @param adminUserDTO the entity to save.
     * @return the persisted entity.
     */
    AdminUserDTO save(AdminUserDTO adminUserDTO);

    /**
     * Get all the adminUsers.
     *
     * @return the list of entities.
     */
    List<AdminUserDTO> findAll();


    /**
     * Get the "id" adminUser.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AdminUserDTO> findOne(Long id);

    /**
     * Delete the "id" adminUser.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
