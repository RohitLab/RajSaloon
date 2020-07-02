package com.raj.app.service.mapper;


import com.raj.app.domain.*;
import com.raj.app.service.dto.NewUserDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link NewUser} and its DTO {@link NewUserDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface NewUserMapper extends EntityMapper<NewUserDTO, NewUser> {


    @Mapping(target = "bookingSlots", ignore = true)
    @Mapping(target = "removeBookingSlot", ignore = true)
    NewUser toEntity(NewUserDTO newUserDTO);

    default NewUser fromId(Long id) {
        if (id == null) {
            return null;
        }
        NewUser newUser = new NewUser();
        newUser.setId(id);
        return newUser;
    }
}
