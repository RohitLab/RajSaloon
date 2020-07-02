package com.raj.app.service;

import com.raj.app.service.dto.BookingSlotDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.raj.app.domain.BookingSlot}.
 */
public interface BookingSlotService {

    /**
     * Save a bookingSlot.
     *
     * @param bookingSlotDTO the entity to save.
     * @return the persisted entity.
     */
    BookingSlotDTO save(BookingSlotDTO bookingSlotDTO);

    /**
     * Get all the bookingSlots.
     *
     * @return the list of entities.
     */
    List<BookingSlotDTO> findAll();


    /**
     * Get the "id" bookingSlot.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BookingSlotDTO> findOne(Long id);

    /**
     * Delete the "id" bookingSlot.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
