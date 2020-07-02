package com.raj.app.web.rest;

import com.raj.app.service.AdminUserService;
import com.raj.app.web.rest.errors.BadRequestAlertException;
import com.raj.app.service.dto.AdminUserDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.raj.app.domain.AdminUser}.
 */
@RestController
@RequestMapping("/api")
public class AdminUserResource {

    private final Logger log = LoggerFactory.getLogger(AdminUserResource.class);

    private static final String ENTITY_NAME = "adminUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdminUserService adminUserService;

    public AdminUserResource(AdminUserService adminUserService) {
        this.adminUserService = adminUserService;
    }

    /**
     * {@code POST  /admin-users} : Create a new adminUser.
     *
     * @param adminUserDTO the adminUserDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adminUserDTO, or with status {@code 400 (Bad Request)} if the adminUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/admin-users")
    public ResponseEntity<AdminUserDTO> createAdminUser(@Valid @RequestBody AdminUserDTO adminUserDTO) throws URISyntaxException {
        log.debug("REST request to save AdminUser : {}", adminUserDTO);
        if (adminUserDTO.getId() != null) {
            throw new BadRequestAlertException("A new adminUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdminUserDTO result = adminUserService.save(adminUserDTO);
        return ResponseEntity.created(new URI("/api/admin-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /admin-users} : Updates an existing adminUser.
     *
     * @param adminUserDTO the adminUserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adminUserDTO,
     * or with status {@code 400 (Bad Request)} if the adminUserDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adminUserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/admin-users")
    public ResponseEntity<AdminUserDTO> updateAdminUser(@Valid @RequestBody AdminUserDTO adminUserDTO) throws URISyntaxException {
        log.debug("REST request to update AdminUser : {}", adminUserDTO);
        if (adminUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AdminUserDTO result = adminUserService.save(adminUserDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, adminUserDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /admin-users} : get all the adminUsers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adminUsers in body.
     */
    @GetMapping("/admin-users")
    public List<AdminUserDTO> getAllAdminUsers() {
        log.debug("REST request to get all AdminUsers");
        return adminUserService.findAll();
    }

    /**
     * {@code GET  /admin-users/:id} : get the "id" adminUser.
     *
     * @param id the id of the adminUserDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adminUserDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/admin-users/{id}")
    public ResponseEntity<AdminUserDTO> getAdminUser(@PathVariable Long id) {
        log.debug("REST request to get AdminUser : {}", id);
        Optional<AdminUserDTO> adminUserDTO = adminUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(adminUserDTO);
    }

    /**
     * {@code DELETE  /admin-users/:id} : delete the "id" adminUser.
     *
     * @param id the id of the adminUserDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/admin-users/{id}")
    public ResponseEntity<Void> deleteAdminUser(@PathVariable Long id) {
        log.debug("REST request to delete AdminUser : {}", id);
        adminUserService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
