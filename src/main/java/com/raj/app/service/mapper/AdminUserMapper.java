package com.raj.app.service.mapper;


import com.raj.app.domain.*;
import com.raj.app.service.dto.AdminUserDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AdminUser} and its DTO {@link AdminUserDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AdminUserMapper extends EntityMapper<AdminUserDTO, AdminUser> {



    default AdminUser fromId(Long id) {
        if (id == null) {
            return null;
        }
        AdminUser adminUser = new AdminUser();
        adminUser.setId(id);
        return adminUser;
    }
}
