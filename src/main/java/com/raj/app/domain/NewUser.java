package com.raj.app.domain;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * The User entity.
 */
@Entity
@Table(name = "new_user")
public class NewUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Type(type = "uuid-char")
    @Column(name = "user_id", length = 36, nullable = false)
    private UUID userId;

    @Column(name = "name")
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToMany(mappedBy = "newUser")
    private Set<BookingSlot> bookingSlots = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public NewUser userId(UUID userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public NewUser name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public NewUser phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Set<BookingSlot> getBookingSlots() {
        return bookingSlots;
    }

    public NewUser bookingSlots(Set<BookingSlot> bookingSlots) {
        this.bookingSlots = bookingSlots;
        return this;
    }

    public NewUser addBookingSlot(BookingSlot bookingSlot) {
        this.bookingSlots.add(bookingSlot);
        bookingSlot.setNewUser(this);
        return this;
    }

    public NewUser removeBookingSlot(BookingSlot bookingSlot) {
        this.bookingSlots.remove(bookingSlot);
        bookingSlot.setNewUser(null);
        return this;
    }

    public void setBookingSlots(Set<BookingSlot> bookingSlots) {
        this.bookingSlots = bookingSlots;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NewUser)) {
            return false;
        }
        return id != null && id.equals(((NewUser) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NewUser{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", name='" + getName() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            "}";
    }
}
