package com.raj.app.service;

import com.raj.app.service.dto.NewUserDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.raj.app.domain.NewUser}.
 */
public interface NewUserService {

    /**
     * Save a newUser.
     *
     * @param newUserDTO the entity to save.
     * @return the persisted entity.
     */
    NewUserDTO save(NewUserDTO newUserDTO);

    /**
     * Get all the newUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<NewUserDTO> findAll(Pageable pageable);


    /**
     * Get the "id" newUser.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NewUserDTO> findOne(Long id);

    /**
     * Delete the "id" newUser.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
