package com.raj.app.web.rest;

import com.raj.app.service.BookingSlotService;
import com.raj.app.web.rest.errors.BadRequestAlertException;
import com.raj.app.service.dto.BookingSlotDTO;

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
 * REST controller for managing {@link com.raj.app.domain.BookingSlot}.
 */
@RestController
@RequestMapping("/api")
public class BookingSlotResource {

    private final Logger log = LoggerFactory.getLogger(BookingSlotResource.class);

    private static final String ENTITY_NAME = "bookingSlot";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BookingSlotService bookingSlotService;

    public BookingSlotResource(BookingSlotService bookingSlotService) {
        this.bookingSlotService = bookingSlotService;
    }

    /**
     * {@code POST  /booking-slots} : Create a new bookingSlot.
     *
     * @param bookingSlotDTO the bookingSlotDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bookingSlotDTO, or with status {@code 400 (Bad Request)} if the bookingSlot has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/booking-slots")
    public ResponseEntity<BookingSlotDTO> createBookingSlot(@Valid @RequestBody BookingSlotDTO bookingSlotDTO) throws URISyntaxException {
        log.debug("REST request to save BookingSlot : {}", bookingSlotDTO);
        if (bookingSlotDTO.getId() != null) {
            throw new BadRequestAlertException("A new bookingSlot cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BookingSlotDTO result = bookingSlotService.save(bookingSlotDTO);
        return ResponseEntity.created(new URI("/api/booking-slots/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /booking-slots} : Updates an existing bookingSlot.
     *
     * @param bookingSlotDTO the bookingSlotDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bookingSlotDTO,
     * or with status {@code 400 (Bad Request)} if the bookingSlotDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bookingSlotDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/booking-slots")
    public ResponseEntity<BookingSlotDTO> updateBookingSlot(@Valid @RequestBody BookingSlotDTO bookingSlotDTO) throws URISyntaxException {
        log.debug("REST request to update BookingSlot : {}", bookingSlotDTO);
        if (bookingSlotDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BookingSlotDTO result = bookingSlotService.save(bookingSlotDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bookingSlotDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /booking-slots} : get all the bookingSlots.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bookingSlots in body.
     */
    @GetMapping("/booking-slots")
    public List<BookingSlotDTO> getAllBookingSlots() {
        log.debug("REST request to get all BookingSlots");
        return bookingSlotService.findAll();
    }

    /**
     * {@code GET  /booking-slots/:id} : get the "id" bookingSlot.
     *
     * @param id the id of the bookingSlotDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bookingSlotDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/booking-slots/{id}")
    public ResponseEntity<BookingSlotDTO> getBookingSlot(@PathVariable Long id) {
        log.debug("REST request to get BookingSlot : {}", id);
        Optional<BookingSlotDTO> bookingSlotDTO = bookingSlotService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bookingSlotDTO);
    }

    /**
     * {@code DELETE  /booking-slots/:id} : delete the "id" bookingSlot.
     *
     * @param id the id of the bookingSlotDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/booking-slots/{id}")
    public ResponseEntity<Void> deleteBookingSlot(@PathVariable Long id) {
        log.debug("REST request to delete BookingSlot : {}", id);
        bookingSlotService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
