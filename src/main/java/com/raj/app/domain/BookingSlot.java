package com.raj.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A BookingSlot.
 */
@Entity
@Table(name = "booking_slot")
public class BookingSlot implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "booking_id")
    private Long bookingId;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "booking_date", nullable = false)
    private LocalDate bookingDate;

    @Column(name = "slot_starting")
    private LocalDate slotStarting;

    @Column(name = "slot_ending")
    private LocalDate slotEnding;

    @ManyToOne
    @JsonIgnoreProperties(value = "bookingSlots", allowSetters = true)
    private NewUser newUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public BookingSlot bookingId(Long bookingId) {
        this.bookingId = bookingId;
        return this;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public String getDescription() {
        return description;
    }

    public BookingSlot description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public BookingSlot bookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
        return this;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public LocalDate getSlotStarting() {
        return slotStarting;
    }

    public BookingSlot slotStarting(LocalDate slotStarting) {
        this.slotStarting = slotStarting;
        return this;
    }

    public void setSlotStarting(LocalDate slotStarting) {
        this.slotStarting = slotStarting;
    }

    public LocalDate getSlotEnding() {
        return slotEnding;
    }

    public BookingSlot slotEnding(LocalDate slotEnding) {
        this.slotEnding = slotEnding;
        return this;
    }

    public void setSlotEnding(LocalDate slotEnding) {
        this.slotEnding = slotEnding;
    }

    public NewUser getNewUser() {
        return newUser;
    }

    public BookingSlot newUser(NewUser newUser) {
        this.newUser = newUser;
        return this;
    }

    public void setNewUser(NewUser newUser) {
        this.newUser = newUser;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BookingSlot)) {
            return false;
        }
        return id != null && id.equals(((BookingSlot) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BookingSlot{" +
            "id=" + getId() +
            ", bookingId=" + getBookingId() +
            ", description='" + getDescription() + "'" +
            ", bookingDate='" + getBookingDate() + "'" +
            ", slotStarting='" + getSlotStarting() + "'" +
            ", slotEnding='" + getSlotEnding() + "'" +
            "}";
    }
}
