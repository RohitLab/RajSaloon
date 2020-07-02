package com.raj.app.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.raj.app.web.rest.TestUtil;

public class AdminUserDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdminUserDTO.class);
        AdminUserDTO adminUserDTO1 = new AdminUserDTO();
        adminUserDTO1.setId(1L);
        AdminUserDTO adminUserDTO2 = new AdminUserDTO();
        assertThat(adminUserDTO1).isNotEqualTo(adminUserDTO2);
        adminUserDTO2.setId(adminUserDTO1.getId());
        assertThat(adminUserDTO1).isEqualTo(adminUserDTO2);
        adminUserDTO2.setId(2L);
        assertThat(adminUserDTO1).isNotEqualTo(adminUserDTO2);
        adminUserDTO1.setId(null);
        assertThat(adminUserDTO1).isNotEqualTo(adminUserDTO2);
    }
}
