package com.raj.app.service.mapper;


import com.raj.app.domain.*;
import com.raj.app.service.dto.BookingSlotDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link BookingSlot} and its DTO {@link BookingSlotDTO}.
 */
@Mapper(componentModel = "spring", uses = {NewUserMapper.class})
public interface BookingSlotMapper extends EntityMapper<BookingSlotDTO, BookingSlot> {

    @Mapping(source = "newUser.id", target = "newUserId")
    BookingSlotDTO toDto(BookingSlot bookingSlot);

    @Mapping(source = "newUserId", target = "newUser")
    BookingSlot toEntity(BookingSlotDTO bookingSlotDTO);

    default BookingSlot fromId(Long id) {
        if (id == null) {
            return null;
        }
        BookingSlot bookingSlot = new BookingSlot();
        bookingSlot.setId(id);
        return bookingSlot;
    }
}
