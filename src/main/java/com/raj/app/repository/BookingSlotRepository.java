package com.raj.app.repository;

import com.raj.app.domain.BookingSlot;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the BookingSlot entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BookingSlotRepository extends JpaRepository<BookingSlot, Long> {
}
