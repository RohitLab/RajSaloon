package com.raj.app.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.raj.app.domain.BookingSlot} entity.
 */
public class BookingSlotDTO implements Serializable {
    
    private Long id;

    private Long bookingId;

    private String description;

    @NotNull
    private LocalDate bookingDate;

    private LocalDate slotStarting;

    private LocalDate slotEnding;


    private Long newUserId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public LocalDate getSlotStarting() {
        return slotStarting;
    }

    public void setSlotStarting(LocalDate slotStarting) {
        this.slotStarting = slotStarting;
    }

    public LocalDate getSlotEnding() {
        return slotEnding;
    }

    public void setSlotEnding(LocalDate slotEnding) {
        this.slotEnding = slotEnding;
    }

    public Long getNewUserId() {
        return newUserId;
    }

    public void setNewUserId(Long newUserId) {
        this.newUserId = newUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BookingSlotDTO)) {
            return false;
        }

        return id != null && id.equals(((BookingSlotDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BookingSlotDTO{" +
            "id=" + getId() +
            ", bookingId=" + getBookingId() +
            ", description='" + getDescription() + "'" +
            ", bookingDate='" + getBookingDate() + "'" +
            ", slotStarting='" + getSlotStarting() + "'" +
            ", slotEnding='" + getSlotEnding() + "'" +
            ", newUserId=" + getNewUserId() +
            "}";
    }
}
