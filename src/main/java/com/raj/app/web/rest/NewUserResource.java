package com.raj.app.web.rest;

import com.raj.app.service.NewUserService;
import com.raj.app.web.rest.errors.BadRequestAlertException;
import com.raj.app.service.dto.NewUserDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.raj.app.domain.NewUser}.
 */
@RestController
@RequestMapping("/api")
public class NewUserResource {

    private final Logger log = LoggerFactory.getLogger(NewUserResource.class);

    private static final String ENTITY_NAME = "newUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NewUserService newUserService;

    public NewUserResource(NewUserService newUserService) {
        this.newUserService = newUserService;
    }

    /**
     * {@code POST  /new-users} : Create a new newUser.
     *
     * @param newUserDTO the newUserDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new newUserDTO, or with status {@code 400 (Bad Request)} if the newUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/new-users")
    public ResponseEntity<NewUserDTO> createNewUser(@Valid @RequestBody NewUserDTO newUserDTO) throws URISyntaxException {
        log.debug("REST request to save NewUser : {}", newUserDTO);
        if (newUserDTO.getId() != null) {
            throw new BadRequestAlertException("A new newUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NewUserDTO result = newUserService.save(newUserDTO);
        return ResponseEntity.created(new URI("/api/new-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /new-users} : Updates an existing newUser.
     *
     * @param newUserDTO the newUserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated newUserDTO,
     * or with status {@code 400 (Bad Request)} if the newUserDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the newUserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/new-users")
    public ResponseEntity<NewUserDTO> updateNewUser(@Valid @RequestBody NewUserDTO newUserDTO) throws URISyntaxException {
        log.debug("REST request to update NewUser : {}", newUserDTO);
        if (newUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NewUserDTO result = newUserService.save(newUserDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, newUserDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /new-users} : get all the newUsers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of newUsers in body.
     */
    @GetMapping("/new-users")
    public ResponseEntity<List<NewUserDTO>> getAllNewUsers(Pageable pageable) {
        log.debug("REST request to get a page of NewUsers");
        Page<NewUserDTO> page = newUserService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /new-users/:id} : get the "id" newUser.
     *
     * @param id the id of the newUserDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the newUserDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/new-users/{id}")
    public ResponseEntity<NewUserDTO> getNewUser(@PathVariable Long id) {
        log.debug("REST request to get NewUser : {}", id);
        Optional<NewUserDTO> newUserDTO = newUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(newUserDTO);
    }

    /**
     * {@code DELETE  /new-users/:id} : delete the "id" newUser.
     *
     * @param id the id of the newUserDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/new-users/{id}")
    public ResponseEntity<Void> deleteNewUser(@PathVariable Long id) {
        log.debug("REST request to delete NewUser : {}", id);
        newUserService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
